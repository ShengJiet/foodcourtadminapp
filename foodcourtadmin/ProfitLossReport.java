package my.edu.utar.foodcourtadmin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfitLossReport {
    private DatabaseReference databaseReference;
    private Context context;

    public ProfitLossReport(Context context) {
        this.context = context;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Bill");
    }

    public void calculateProfitLoss(final String period) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double totalProfitLoss = 0;

                for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot itemSnapshot : billSnapshot.getChildren()) {
                        Double subtotal = itemSnapshot.child("subtotal").getValue(Double.class);
                        if (subtotal != null) {
                            // Calculate profit or loss
                            totalProfitLoss += subtotal; // Update this line with the actual profit/loss calculation
                        }
                    }
                }

                Intent intent = new Intent(context, SalesReportActivity.class);
                intent.putExtra("REPORT_TYPE", period + " Profit/Loss");
                intent.putExtra("TOTAL_SALES", totalProfitLoss);
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ProfitLossReport", "Failed to read profit/loss data: " + databaseError.toException());
            }
        });
    }
}
