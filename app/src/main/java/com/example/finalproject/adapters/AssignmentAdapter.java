package com.example.finalproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.activities.ViewRepliesActivity;
import com.example.finalproject.models.Assignment;

import java.util.ArrayList;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private final Context context;
    private final ArrayList<Assignment> list;

    public AssignmentAdapter(Context context, ArrayList<Assignment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assignment_item, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {
        Assignment a = list.get(position);
        holder.title.setText(a.getTitle());
        holder.subject.setText("Subject: " + a.getSubjectName());
        holder.date.setText("Due Date: " + a.getDueDate());
        holder.description.setText(a.getDescription());
        holder.file.setText("File: " + a.getAttachmentUrl());

        holder.buttonViewReplies.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewRepliesActivity.class);
            intent.putExtra("assignment_id", a.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView title, subject, date, description, file;
        Button buttonViewReplies;

        public AssignmentViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewAssignmentTitle);
            subject = itemView.findViewById(R.id.textViewAssignmentSubject);
            date = itemView.findViewById(R.id.textViewAssignmentDueDate);
            description = itemView.findViewById(R.id.textViewAssignmentDescription);
            file = itemView.findViewById(R.id.textViewAssignmentFile);
            buttonViewReplies = itemView.findViewById(R.id.buttonViewReplies);
        }
    }
}
