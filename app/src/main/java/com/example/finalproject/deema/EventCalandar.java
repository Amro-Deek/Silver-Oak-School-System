package com.example.finalproject.deema;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.List;

public class EventCalandar extends AppCompatActivity {

    private RecyclerView eventRecyclerView;
    private eventAdapter adapter;
    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calandar);

        // Initialize RecyclerView
        eventRecyclerView = findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get events from the database
        eventsdb db = new eventsdb();  // Assuming you made this class return a list of events
        events = db.getEvents();       // You must implement this method in eventsdb

        // Set adapter
        adapter = new eventAdapter(events);
        eventRecyclerView.setAdapter(adapter);
    }
}
