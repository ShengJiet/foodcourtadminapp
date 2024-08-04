package my.edu.utar.foodcourtadmin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SalesReport {
    private DatabaseReference databaseReference;
    private Context context;
    private SimpleDateFormat dateFormat;

    public SalesReport(Context context) {
        this.context = context;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Bill");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    public void generateSalesReport() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Double> dailySales = new HashMap<>();
                Map<String, Double> weeklySales = new HashMap<>();
                Map<String, Double> monthlySales = new HashMap<>();

                for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                    try {
                        String dateTimeStr = billSnapshot.child("dateTime").getValue(String.class);
                        Date billDate = dateFormat.parse(dateTimeStr);

                        if (billDate != null) {
                            String dayKey = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(billDate);
                            String weekKey = new SimpleDateFormat("yyyy-'W'ww", Locale.getDefault()).format(billDate);
                            String monthKey = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(billDate);

                            double totalPrice = Double.parseDouble(billSnapshot.child("totalPrice").getValue(String.class).replace("$", ""));

                            dailySales.put(dayKey, dailySales.getOrDefault(dayKey, 0.0) + totalPrice);
                            weeklySales.put(weekKey, weeklySales.getOrDefault(weekKey, 0.0) + totalPrice);
                            monthlySales.put(monthKey, monthlySales.getOrDefault(monthKey, 0.0) + totalPrice);
                        }

                    } catch (ParseException e) {
                        Log.e("SalesReport", "Failed to parse date: " + e.getMessage());
                    }
                }

                Intent intent = new Intent(context, SalesReportActivity.class);
                intent.putExtra("DAILY_SALES", (HashMap<String, Double>) dailySales);
                intent.putExtra("WEEKLY_SALES", (HashMap<String, Double>) weeklySales);
                intent.putExtra("MONTHLY_SALES", (HashMap<String, Double>) monthlySales);
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("SalesReport", "Failed to read sales data: " + databaseError.toException());
            }
        });
    }
}
