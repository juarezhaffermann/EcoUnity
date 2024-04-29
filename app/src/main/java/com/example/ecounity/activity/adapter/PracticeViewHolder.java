package com.example.ecounity.activity.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecounity.R;


public class PracticeViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView descriptionTextView;
    public TextView categoryTextView;

    public PracticeViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.tvpatrica);
        descriptionTextView = itemView.findViewById(R.id.tvdescricao);
        categoryTextView = itemView.findViewById(R.id.tvcategoria);
    }
}
