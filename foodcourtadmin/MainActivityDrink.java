package my.edu.utar.foodcourtadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityDrink extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapterDrink mainAdapterDrink;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drink);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModelDrink> options =
                new FirebaseRecyclerOptions.Builder<MainModelDrink>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Drinks"), MainModelDrink.class)
                        .build();

        mainAdapterDrink = new MainAdapterDrink(options);
        recyclerView.setAdapter(mainAdapterDrink);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddDrinkActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapterDrink.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapterDrink.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str) {
        FirebaseRecyclerOptions<MainModelDrink> options =
                new FirebaseRecyclerOptions.Builder<MainModelDrink>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Drinks").orderByChild("Title").startAt(str).endAt(str + "~"), MainModelDrink.class)
                        .build();

        mainAdapterDrink.updateOptions(options); // Update adapter options
    }
}
