package com.example.ecounity.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;
import com.example.ecounity.activity.model.Practice;

import java.util.ArrayList;
import java.util.List;

public class PracticeAdapter extends RecyclerView.Adapter<PracticeViewHolder>{
        private List<Practice> originalPractices;
    private List<Practice> filteredPractices;

    public PracticeAdapter(List<Practice> practices) {
        this.originalPractices = practices;
        this.filteredPractices = new ArrayList<>(practices);
    }

    @NonNull
    @Override
    public PracticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new PracticeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PracticeViewHolder holder, int position) {
        Practice practice = filteredPractices.get(position);
        holder.titleTextView.setText(practice.getTitulo());
        holder.descriptionTextView.setText(practice.getDescricao());
        holder.categoryTextView.setText(practice.getCategoria());
    }

    @Override
    public int getItemCount() {
        return filteredPractices.size();
    }

    public List<Practice> getOriginalPractices() {
        return originalPractices;
    }

    public List<Practice> getFilteredPractices() {
        return filteredPractices;
    }

    public void filterList(List<Practice> filteredPractices) {
        this.filteredPractices = filteredPractices;
        notifyDataSetChanged();
    }
}
