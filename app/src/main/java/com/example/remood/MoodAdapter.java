package com.example.remood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodViewHolder> {

    private Context context;
    private final List<Mood> moodList;

    private final OnMoodActionListener actionListener;

    public MoodAdapter(Context context, ArrayList<Mood> moodList, OnMoodActionListener actionListener) {
        this.context = context;
        this.moodList = moodList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowviewmood, parent, false);
        return new MoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder holder, int position) {
        Mood mood = moodList.get(position);
        holder.moodText.setText(mood.getName());
        holder.moodImage.setImageResource(mood.getImageResId());

        // Event untuk edit
        holder.editButton.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onEdit(position);
            }
        });
        // Event untuk delete
        holder.deleteButton.setOnClickListener(v -> {
            if(actionListener != null) {
                actionListener.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moodList.size();
    }

    public static class MoodViewHolder extends RecyclerView.ViewHolder {
        TextView moodText;
        ImageView moodImage, editButton, deleteButton;

        public MoodViewHolder(@NonNull View itemView) {
            super(itemView);
            moodText = itemView.findViewById(R.id.tvNamaMood);
            moodImage = itemView.findViewById(R.id.imageView);
            editButton = itemView.findViewById(R.id.imageView2);
            deleteButton = itemView.findViewById(R.id.imageView3);
        }
    }

    public interface OnMoodActionListener {
        void onEdit(int position);
        void onDelete(int position);
    }

}
