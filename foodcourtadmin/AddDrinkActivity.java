package my.edu.utar.foodcourtadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddDrinkActivity extends AppCompatActivity {
    EditText bestdrink,categoryid,description,imagepath,locationid,price,priceid,
    star,timeid,timevalue,title;
    Button btnAdd,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        bestdrink=(EditText)findViewById(R.id.txtBestDrink);
        categoryid=(EditText)findViewById(R.id.txtCategoryId);
        description=(EditText)findViewById(R.id.txtDescription);
        imagepath=(EditText)findViewById(R.id.txtImagePath);
        locationid=(EditText)findViewById(R.id.txtLocationId);
        price=(EditText)findViewById(R.id.txtPrice);
        priceid=(EditText)findViewById(R.id.txtPriceId);
        star=(EditText)findViewById(R.id.txtStar);
        timeid=(EditText)findViewById(R.id.txtTimeId);
        timevalue=(EditText)findViewById(R.id.txtTimeValue);
        title=(EditText)findViewById(R.id.txtTitle);


        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnBack=(Button) findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDate();
                clearAll();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void insertDate(){
        Map<String,Object> map=new HashMap<>();
        map.put("BestDrink",bestdrink.getText().toString());
        map.put("CategoryId",categoryid.getText().toString());
        map.put("Description",description.getText().toString());
        map.put("ImagePath",imagepath.getText().toString());
        map.put("LocationId",locationid.getText().toString());
        map.put("Price",price.getText().toString());
        map.put("PriceId",priceid.getText().toString());
        map.put("Star",star.getText().toString());
        map.put("TimeId",timeid.getText().toString());
        map.put("TimeValue",timevalue.getText().toString());
        map.put("Title",title.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Drinks").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddDrinkActivity.this,"Data Inserted Successfully.",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddDrinkActivity.this,"Error While Insertion.",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearAll(){
        bestdrink.setText("");
        categoryid.setText("");
        description.setText("");
        imagepath.setText("");
        locationid.setText("");
        price.setText("");
        priceid.setText("");
        star.setText("");
        timeid.setText("");
        timevalue.setText("");
        title.setText("");

    }
}
