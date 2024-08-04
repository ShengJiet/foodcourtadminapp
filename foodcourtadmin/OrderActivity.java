package my.edu.utar.foodcourtadmin;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private LinearLayout orderLayout;
    private Handler handler = new Handler();
    private int kitchenStaffCount = 0;
    private List<Order> orders = new ArrayList<>();
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderLayout = findViewById(R.id.orderLayout);
        ordersRef = FirebaseDatabase.getInstance().getReference("Order");

        promptForKitchenStaffCount();
    }

    private void promptForKitchenStaffCount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter number of kitchen staff");

        final EditText input = new EditText(this);
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            kitchenStaffCount = Integer.parseInt(input.getText().toString());
            startOrderRefresh();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void startOrderRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchOrders();
                handler.postDelayed(this, 5000); // Refresh every 5 seconds
            }
        }, 5000);
    }

    private void fetchOrders() {
        ordersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                orders.clear();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Order order = new Order();
                    order.setId(snapshot.getKey());
                    order.setDateTime(snapshot.child("dateTime").getValue(String.class));
                    order.setTableNumber(snapshot.child("tableNumber").getValue(String.class));

                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        // Ensure we only parse item nodes
                        if (itemSnapshot.hasChild("itemName")) {
                            Order.Item item = itemSnapshot.getValue(Order.Item.class);
                            if (item != null) {
                                order.addItem(item);
                            }
                        }
                    }
                    orders.add(order);
                }
                Log.d("OrderActivity", "Fetched " + orders.size() + " orders");
                assignOrdersToKitchenStaff();
            } else {
                Log.e("OrderActivity", "Failed to fetch orders");
            }
        });
    }

    private void assignOrdersToKitchenStaff() {
        orderLayout.removeAllViews();

        List<Order>[] staffOrders = new List[kitchenStaffCount];
        for (int i = 0; i < kitchenStaffCount; i++) {
            staffOrders[i] = new LinkedList<>();
        }

        // Sort orders by timestamp for FIFO
        Collections.sort(orders);

        int staffIndex = 0;
        for (Order order : orders) {
            staffOrders[staffIndex].add(order);
            staffIndex = (staffIndex + 1) % kitchenStaffCount;
        }

        for (int i = 0; i < kitchenStaffCount; i++) {
            addStaffColumn(i, staffOrders[i]);
        }
    }

    private void addStaffColumn(int staffIndex, List<Order> orders) {
        LinearLayout staffColumn = new LinearLayout(this);
        staffColumn.setOrientation(LinearLayout.VERTICAL);
        staffColumn.setPadding(16, 16, 16, 16);

        TextView staffTitle = new TextView(this);
        staffTitle.setText("Kitchen Staff " + (staffIndex + 1));
        staffColumn.addView(staffTitle);

        for (Order order : orders) {
            LinearLayout orderDetailsLayout = new LinearLayout(this);
            orderDetailsLayout.setOrientation(LinearLayout.VERTICAL);

            for (Order.Item item : order.getItems()) {
                TextView itemTextView = new TextView(this);
                itemTextView.setText(item.getItemName() + " x " + item.getQuantity());
                orderDetailsLayout.addView(itemTextView);
            }

            Button orderButton = new Button(this);
            orderButton.setText("Mark as Finished");
            orderButton.setOnClickListener(v -> showConfirmDialog(order, staffColumn, orderDetailsLayout));
            orderDetailsLayout.addView(orderButton);

            staffColumn.addView(orderDetailsLayout);
        }

        orderLayout.addView(staffColumn);
    }

    private void showConfirmDialog(Order order, ViewGroup parent, View orderView) {
        new AlertDialog.Builder(this)
                .setMessage("Confirm the order has finished?")
                .setPositiveButton("Yes", (dialog, which) -> markOrderAsFinished(order, parent, orderView))
                .setNegativeButton("No", null)
                .show();
    }

    private void markOrderAsFinished(Order order, ViewGroup parent, View orderView) {
        ordersRef.child(order.getId()).removeValue()
                .addOnSuccessListener(aVoid -> parent.removeView(orderView))
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }

    public static class Order implements Comparable<Order> {
        private String id;
        private String dateTime;
        private String tableNumber;
        private List<Item> items = new ArrayList<>();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getTableNumber() {
            return tableNumber;
        }

        public void setTableNumber(String tableNumber) {
            this.tableNumber = tableNumber;
        }

        public List<Item> getItems() {
            return items;
        }

        public void addItem(Item item) {
            items.add(item);
        }

        @Override
        public int compareTo(Order o) {
            return this.dateTime.compareTo(o.dateTime);
        }

        public static class Item {
            private String itemName;
            private int quantity;
            private String iceAmount;
            private String sweetnessLevel;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getIceAmount() {
                return iceAmount;
            }

            public void setIceAmount(String iceAmount) {
                this.iceAmount = iceAmount;
            }

            public String getSweetnessLevel() {
                return sweetnessLevel;
            }

            public void setSweetnessLevel(String sweetnessLevel) {
                this.sweetnessLevel = sweetnessLevel;
            }
        }
    }
}
