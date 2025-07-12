package com.example.finalproject.deema;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.List;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.EventViewHolder> {

    private final List<Event> eventList;

    public eventAdapter(List<Event> list) {
        this.eventList = list;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_singleevent, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.day.setText(event.getDay());
        holder.dayOfWeek.setText(event.getDayOfWeek());
        holder.time.setText(event.getTime());
        holder.title.setText(event.getTitle());
        holder.eventImage.setImageResource(event.getImageResource());


    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfWeek, day, time, title;
        ImageView eventImage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfWeek = itemView.findViewById(R.id.dayOfWeek);
            day = itemView.findViewById(R.id.dayNumber);
            time = itemView.findViewById(R.id.eventTime);
            title = itemView.findViewById(R.id.eventTitle);
            eventImage = itemView.findViewById(R.id.eventImage);
        }
    }
}
