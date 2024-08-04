package my.edu.utar.foodcourtadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityProfile extends AppCompatActivity {

    private TextView adminName, adminRole, adminEmail, adminContact, adminAge, adminGender, adminNationality, adminRaces, adminAddress;
    private Button editButton;
    private DatabaseReference adminRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        adminName = findViewById(R.id.AdminText);
        adminRole = findViewById(R.id.RoleText);
        adminEmail = findViewById(R.id.AdminEmailText);
        adminContact = findViewById(R.id.ProjectTitleText);
        adminAge = findViewById(R.id.AgeText);
        adminGender = findViewById(R.id.GenderText);
        adminNationality = findViewById(R.id.NationalityText);
        adminRaces = findViewById(R.id.RacesText);
        adminAddress = findViewById(R.id.AddressText);
        editButton = findViewById(R.id.btnEdit);

        adminRef = FirebaseDatabase.getInstance().getReference().child("Admin");

        // Add OnClickListener to the edit button
        editButton.setOnClickListener(v -> {
            // Start MainActivityProfileUpdate with intent extras containing existing data
            Intent intent = new Intent(MainActivityProfile.this, MainActivityProfileUpdate.class);
            intent.putExtra("adminName", adminName.getText().toString());
            intent.putExtra("adminRole", adminRole.getText().toString());
            intent.putExtra("adminEmail", adminEmail.getText().toString());
            intent.putExtra("adminContact", adminContact.getText().toString());
            intent.putExtra("adminAge", adminAge.getText().toString());
            intent.putExtra("adminGender", adminGender.getText().toString());
            intent.putExtra("adminNationality", adminNationality.getText().toString());
            intent.putExtra("adminRaces", adminRaces.getText().toString());
            intent.putExtra("adminAddress", adminAddress.getText().toString());
            startActivity(intent);
        });

        adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    MainModelProfile mainModelProfile = dataSnapshot.getValue(MainModelProfile.class);

                    // Set retrieved values to TextViews
                    adminName.setText(mainModelProfile.getName());
                    adminRole.setText(mainModelProfile.getRole());
                    adminEmail.setText(mainModelProfile.getEmail());
                    adminContact.setText(mainModelProfile.getContactNo());
                    adminAge.setText(mainModelProfile.getAge());
                    adminGender.setText(mainModelProfile.getGender());
                    adminNationality.setText(mainModelProfile.getNationality());
                    adminRaces.setText(mainModelProfile.getRaces());
                    adminAddress.setText(mainModelProfile.getAddress());
                } else {
                    Log.d("MainActivityProfile", "No admin data found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainActivityProfile", "Failed to read admin data.", databaseError.toException());
            }
        });

    }
}