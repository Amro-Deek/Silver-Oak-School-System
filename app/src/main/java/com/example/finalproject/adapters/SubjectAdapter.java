package com.example.finalproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.activities.SendAssignmentActivity;
import com.example.finalproject.activities.ViewAssignmentsActivity;
import com.example.finalproject.models.*;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private final List<Subject> subjectList;
    private final Context context;

    public SubjectAdapter(Context context, List<Subject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.textViewSubjectName.setText(subject.getName());

        holder.buttonSubmitAssignmen.setOnClickListener(v -> {
            Intent intent = new Intent(context, SendAssignmentActivity.class);
            intent.putExtra("subject_id", subject.getId()); // Pass the subject ID
            intent.putExtra("subject_name", subject.getName()); // Optional: pass name too
            context.startActivity(intent);
            // Intent or logic to handle viewing grades
            Toast.makeText(context, "Submit Assignment Grades for " + subject.getName(), Toast.LENGTH_SHORT).show();
        });

        holder.buttonViewAssignments.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewAssignmentsActivity.class);
            intent.putExtra("subject_id", subject.getId()); // Pass the subject ID
            intent.putExtra("subject_name", subject.getName()); // Optional: pass name too
            context.startActivity(intent);
            // Intent or logic to handle viewing assignments
            Toast.makeText(context, "View Assignments for " + subject.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSubjectName;
        Button buttonSubmitAssignmen, buttonViewAssignments;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSubjectName = itemView.findViewById(R.id.textViewSubjectName);
            buttonSubmitAssignmen = itemView.findViewById(R.id.buttonSubmitAssignmen);
            buttonViewAssignments = itemView.findViewById(R.id.buttonViewAssignments);
        }
    }
}
