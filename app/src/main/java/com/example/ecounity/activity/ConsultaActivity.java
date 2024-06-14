package com.example.ecounity.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;
import com.example.ecounity.activity.adapter.PracticeAdapter;
import com.example.ecounity.activity.model.Practice;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ConsultaActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private PracticeAdapter practiceAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.praticelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data from Firebase
        db.collection("Práticas Sustentáveis")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Practice> practices = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            Practice practice = document.toObject(Practice.class);
                            practices.add(practice);
                        }
                        practiceAdapter = new PracticeAdapter(practices);

                        recyclerView.setAdapter(practiceAdapter);
                    } else {
                        Log.d("ConsultaActivity", "Error getting documents: ", task.getException());
                    }
                });

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {

                    practiceAdapter.filterList(practiceAdapter.getOriginalPractices());
                } else {
                    filterList(newText);
                }
                return true;
            }
        });
    }

    private void filterList(String query) {
        List<Practice> filteredList = new ArrayList<>();
        for (Practice practice : practiceAdapter.getOriginalPractices()) {
            if (practice.getTitulo().toLowerCase().contains(query.toLowerCase())
                    || practice.getDescricao().toLowerCase().contains(query.toLowerCase())
                    || practice.getCategoria().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(practice);
            }
        }

        practiceAdapter.filterList(filteredList);
        practiceAdapter.notifyDataSetChanged();
    }

}
