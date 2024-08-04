package my.edu.utar.foodcourtadmin;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class BillingDetailActivity extends AppCompatActivity {

    private TextView billingDetailsTextView;
    private JSONObject billData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_detail);

        billingDetailsTextView = findViewById(R.id.billingDetailsTextView);

        String billId = getIntent().getStringExtra("BILL_ID");
        if (billId != null) {
            fetchBillData(billId);
        } else {
            Log.e("BillingDetailActivity", "No bill ID received");
        }
    }

    private void fetchBillData(String billId) {
        FirebaseDatabase.getInstance().getReference("Bill").child(billId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            JSONObject bill = new JSONObject();
                            for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                                if (itemSnapshot.getValue() instanceof Map) {
                                    // Convert the Map to JSONObject
                                    JSONObject itemJson = new JSONObject((Map<?, ?>) itemSnapshot.getValue());
                                    bill.put(itemSnapshot.getKey(), itemJson);
                                } else {
                                    bill.put(itemSnapshot.getKey(), itemSnapshot.getValue());
                                }
                            }
                            billData = bill;
                            displayBillDetails();
                        } catch (JSONException e) {
                            Log.e("BillingDetailActivity", "Failed to parse bill data: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("BillingDetailActivity", "Failed to fetch bill data: " + databaseError.getMessage());
                    }
                });
    }

    private void displayBillDetails() {
        StringBuilder details = new StringBuilder();
        try {
            details.append("Date and Time: ").append(billData.getString("dateTime")).append("\n")
                    .append("Table Number: ").append(billData.getString("tableNumber")).append("\n")
                    .append("Items:\n");

            // Iterate over the keys in billData
            Iterator<String> keys = billData.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (!key.equals("dateTime") && !key.equals("tableNumber") && !key.equals("tax") && !key.equals("totalPrice")) {
                    JSONObject item = billData.getJSONObject(key);
                    details.append("- ").append(item.getString("itemName")).append(", ")
                            .append("Price: $").append(item.getDouble("price")).append(", ")
                            .append("Quantity: ").append(item.getInt("quantity")).append(", ")
                            .append("Subtotal: $").append(item.getDouble("subtotal")).append("\n");
                }
            }
            details.append("Tax: $").append(billData.getDouble("tax")).append("\n")
                    .append("Total Price: ").append(billData.getString("totalPrice"));

            billingDetailsTextView.setText(details.toString());
        } catch (JSONException e) {
            Log.e("BillingDetailActivity", "Failed to display bill details: " + e.getMessage());
        }
    }
}
