package my.edu.utar.foodcourtadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button updateDrinksButton;
    private ImageView profileButton;
    private Button salesReportButton;
    private Button billingButton;
    private Button orderButton; // New Order Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateDrinksButton = findViewById(R.id.updateDrinksButton);
        profileButton = findViewById(R.id.profileBtn);
        salesReportButton = findViewById(R.id.salesReportButton);
        billingButton = findViewById(R.id.billingButton);
        orderButton = findViewById(R.id.orderButton); // New Order Button

        updateDrinksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivityDrink.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivityProfile.class);
                startActivity(intent);
            }
        });

        salesReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalesReport salesReport = new SalesReport(MainActivity.this);
                salesReport.generateSalesReport();
            }
        });

        billingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BillingListActivity.class);
                startActivity(intent);
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() { // New Order Button
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
