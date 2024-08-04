package my.edu.utar.foodcourtadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityProfileUpdate extends AppCompatActivity {

    private EditText adminNameEditText, roleEditText, emailEditText, contactNoEditText, ageEditText, genderEditText, nationalityEditText, racesEditText, addressEditText;
    private Button updateButton;
    private MainAdapterProfileUpdate adapterProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileupdate);

        // Initialize views
        adminNameEditText = findViewById(R.id.txtAdminName);
        roleEditText = findViewById(R.id.txtRole);
        emailEditText = findViewById(R.id.txtEmail);
        contactNoEditText = findViewById(R.id.txtContactNo);
        ageEditText = findViewById(R.id.txtAge);
        genderEditText = findViewById(R.id.txtGender);
        nationalityEditText = findViewById(R.id.txtNationality);
        racesEditText = findViewById(R.id.txtRaces);
        addressEditText = findViewById(R.id.txtAddress);
        updateButton = findViewById(R.id.btnUpdate);

        // Initialize the adapter for updating profile
        adapterProfile = new MainAdapterProfileUpdate();

        // Retrieve existing data from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            adminNameEditText.setText(extras.getString("adminName", ""));
            roleEditText.setText(extras.getString("adminRole", ""));
            emailEditText.setText(extras.getString("adminEmail", ""));
            contactNoEditText.setText(extras.getString("adminContact", ""));
            ageEditText.setText(extras.getString("adminAge", ""));
            genderEditText.setText(extras.getString("adminGender", ""));
            nationalityEditText.setText(extras.getString("adminNationality", ""));
            racesEditText.setText(extras.getString("adminRaces", ""));
            addressEditText.setText(extras.getString("adminAddress", ""));
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAdminProfile();
            }
        });
    }

    private void updateAdminProfile() {
        String adminName = adminNameEditText.getText().toString().trim();
        String role = roleEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String contactNo = contactNoEditText.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();
        String nationality = nationalityEditText.getText().toString().trim();
        String races = racesEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Call the method from MainAdapterProfile to update the Firebase Admin table
        adapterProfile.updateAdminProfile( adminName, role, email, contactNo, age, gender, nationality, races, address);
    }
}
