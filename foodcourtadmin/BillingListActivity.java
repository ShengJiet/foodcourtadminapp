package my.edu.utar.foodcourtadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BillingListActivity extends AppCompatActivity {

    private ListView billingListView;
    private List<String> billIds;
    private List<String> billDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_list);

        billingListView = findViewById(R.id.billingListView);
        billIds = new ArrayList<>();
        billDetails = new ArrayList<>();

        fetchBillingData();

        billingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedBillId = billIds.get(position);
                Intent intent = new Intent(BillingListActivity.this, BillingDetailActivity.class);
                intent.putExtra("BILL_ID", selectedBillId);
                startActivity(intent);
            }
        });
    }

    private void fetchBillingData() {
        FirebaseDatabase.getInstance().getReference("Bill")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<BillItem> billItemList = new ArrayList<>();

                        for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                            MainModelBill bill = billSnapshot.getValue(MainModelBill.class);
                            if (bill != null) {
                                String billId = billSnapshot.getKey();
                                billItemList.add(new BillItem(billId, bill.getDateTime(), bill.getTableNumber(), bill.getTotalPrice()));
                            }
                        }

                        // Sort billItemList by dateTime in descending order
                        Collections.sort(billItemList, new Comparator<BillItem>() {
                            @Override
                            public int compare(BillItem o1, BillItem o2) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                try {
                                    Date date1 = sdf.parse(o1.dateTime);
                                    Date date2 = sdf.parse(o2.dateTime);
                                    return date2.compareTo(date1);
                                } catch (ParseException e) {
                                    Log.e("BillingListActivity", "Date parsing error: " + e.getMessage());
                                    return 0;
                                }
                            }
                        });

                        for (BillItem billItem : billItemList) {
                            billIds.add(billItem.billId);
                            StringBuilder billDetail = new StringBuilder();
                            billDetail.append("Date and Time: ").append(billItem.dateTime).append("\n")
                                    .append("Table Number: ").append(billItem.tableNumber).append("\n")
                                    .append("Total Price: ").append(billItem.totalPrice);
                            billDetails.add(billDetail.toString());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(BillingListActivity.this, android.R.layout.simple_list_item_1, billDetails);
                        billingListView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("BillingListActivity", "Failed to fetch billing data: " + databaseError.getMessage());
                    }
                });
    }

    private static class BillItem {
        String billId;
        String dateTime;
        String tableNumber;
        String totalPrice;

        BillItem(String billId, String dateTime, String tableNumber, String totalPrice) {
            this.billId = billId;
            this.dateTime = dateTime;
            this.tableNumber = tableNumber;
            this.totalPrice = totalPrice;
        }
    }
}
