package com.example.finalproject.Yahya;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    private Context context;
    private List<Assigment> assignments;

    private int studentId;

    public AssignmentAdapter(Context context, List<Assigment> assignments, int studentId) {
        this.context = context;
        this.assignments = assignments;
        this.studentId = studentId;
    }

    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assignment_record, parent, false);
        return new AssignmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.finalproject.Yahya.AssignmentAdapter.ViewHolder holder, int position) {
        Assigment assignment = assignments.get(position);

        holder.assignmentTitle.setText(assignment.getTitle());
        holder.subject.setText("Subject: " + assignment.getSubject());
        holder.dueDate.setText("Due: " + assignment.getDuoDate());

        holder.submitButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, com.example.finalproject.Yahya.AssignmentSubmitionActivity.class);
            intent.putExtra("id", assignment.getId());
            intent.putExtra("title", assignment.getTitle());
            intent.putExtra("subject", assignment.getSubject());
            intent.putExtra("dueDate", assignment.getDuoDate());
            intent.putExtra("status", assignment.getStatus());
            intent.putExtra("student_id", studentId);
            intent.putExtra("description", assignment.getDescription());
            intent.putExtra("attachment_url", assignment.getAttachmentUrl());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentTitle, subject, dueDate, status;
        Button submitButton;


        public ViewHolder(View itemView) {
            super(itemView);
            assignmentTitle = itemView.findViewById(R.id.assignmentTitle);
            subject = itemView.findViewById(R.id.subject);
            dueDate = itemView.findViewById(R.id.dueDate);
            submitButton = itemView.findViewById(R.id.submitButton);

        }
    }
}