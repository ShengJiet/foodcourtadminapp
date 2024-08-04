package my.edu.utar.foodcourtadmin;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class SalesReportActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);

        tableLayout = findViewById(R.id.tableLayout);
        decimalFormat = new DecimalFormat("#.00");

        HashMap<String, Double> dailySales = (HashMap<String, Double>) getIntent().getSerializableExtra("DAILY_SALES");
        HashMap<String, Double> weeklySales = (HashMap<String, Double>) getIntent().getSerializableExtra("WEEKLY_SALES");
        HashMap<String, Double> monthlySales = (HashMap<String, Double>) getIntent().getSerializableExtra("MONTHLY_SALES");

        if (dailySales != null) {
            addTableHeader("Daily Sales");
            addSalesDataToTable(dailySales);
        }

        if (weeklySales != null) {
            addTableHeader("Weekly Sales");
            addSalesDataToTable(weeklySales);
        }

        if (monthlySales != null) {
            addTableHeader("Monthly Sales");
            addSalesDataToTable(monthlySales);
        }
    }

    private void addTableHeader(String title) {
        TableRow headerRow = new TableRow(this);
        TextView headerTextView = new TextView(this);
        headerTextView.setText(title);
        headerTextView.setTextSize(20);
        headerTextView.setPadding(5, 5, 5, 5);
        headerRow.addView(headerTextView);
        tableLayout.addView(headerRow);
    }

    private void addSalesDataToTable(Map<String, Double> salesData) {
        for (Map.Entry<String, Double> entry : salesData.entrySet()) {
            TableRow row = new TableRow(this);
            TextView dateTextView = new TextView(this);
            TextView amountTextView = new TextView(this);

            dateTextView.setText(entry.getKey());
            amountTextView.setText("RM" + decimalFormat.format(entry.getValue()));

            dateTextView.setPadding(5, 5, 5, 5);
            amountTextView.setPadding(5, 5, 5, 5);

            row.addView(dateTextView);
            row.addView(amountTextView);

            tableLayout.addView(row);
        }
    }
}
