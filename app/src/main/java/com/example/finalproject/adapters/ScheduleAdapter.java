package com.example.finalproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.ScheduleEntry;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<ScheduleEntry> scheduleList;

    public ScheduleAdapter(List<ScheduleEntry> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleEntry entry = scheduleList.get(position);
        holder.textViewTime.setText(entry.getStartTime() + " - " + entry.getEndTime());
        holder.textViewSubject.setText(entry.getSubjectName());
        holder.textViewRoom.setText("Room: " + entry.getRoom());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTime, textViewSubject, textViewRoom;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewSubject = itemView.findViewById(R.id.textViewSubject);
            textViewRoom = itemView.findViewById(R.id.textViewRoom);
        }
    }
}
