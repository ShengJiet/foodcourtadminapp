package my.edu.utar.foodcourtadmin;

import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainAdapterProfileUpdate {
    private DatabaseReference adminRef;

    public MainAdapterProfileUpdate() {
        adminRef = FirebaseDatabase.getInstance().getReference().child("Admin");
    }

    public void updateAdminProfile(String adminName, String role, String email, String contactNo, String age, String gender, String nationality, String races, String address) {
        MainModelProfile mainModelProfile = new MainModelProfile(adminName, role, email, contactNo, age, gender, nationality, races, address);

        adminRef.setValue(mainModelProfile)
                .addOnSuccessListener(aVoid -> Log.d("MainAdapterProfile", "Admin profile updated successfully"))
                .addOnFailureListener(e -> Log.e("MainAdapterProfile", "Failed to update admin profile", e));
    }
}