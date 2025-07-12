package com.example.finalproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.AssignmentResponse;

import java.util.List;

public class AssignmentResponseAdapter extends RecyclerView.Adapter<AssignmentResponseAdapter.ViewHolder> {

    private final Context context;
    private final List<AssignmentResponse> replies;

    public AssignmentResponseAdapter(Context context, List<AssignmentResponse> replies) {
        this.context = context;
        this.replies = replies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reply_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AssignmentResponse reply = replies.get(position);
        holder.studentName.setText("👤 " + reply.getStudentName());
        holder.submissionDate.setText("📅 Submitted: " + reply.getSubmissionDate());
        holder.attachmentUrl.setText("📎 File: " + reply.getAttachmentUrl());
        holder.message.setText("✉️ " + reply.getMessage());
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, submissionDate, attachmentUrl,message;

        public ViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.textViewStudentName);
            submissionDate = itemView.findViewById(R.id.textViewSubmissionDate);
            attachmentUrl = itemView.findViewById(R.id.textViewAttachmentUrl);
            message = itemView.findViewById(R.id.textViewMessage);
        }
    }
}