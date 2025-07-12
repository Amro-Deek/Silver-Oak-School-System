package com.example.finalproject.deema;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.List;

public class NewsLetter extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_news_letter);

            // Initialize data
            NewsDB db = new NewsDB();
            List<News> newsList = db.getNews();

            // Convert to arrays for adapter
            String[] headlines = new String[newsList.size()];
            String[] bodies = new String[newsList.size()];
            int[] images = new int[newsList.size()];

            for (int i = 0; i < newsList.size(); i++) {
                headlines[i] = newsList.get(i).getTitle();
                bodies[i] = newsList.get(i).getNewsinfo();
                images[i] = newsList.get(i).getImageid();
            }

            // Setup RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ImageAdapter adapter = new ImageAdapter(headlines, bodies, images);
            recyclerView.setAdapter(adapter);
        }
    }

