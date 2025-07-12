package com.example.finalproject.deema;


import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private String[] headlines;
    private String[] newsBodies;
    private int[] imageIds;


    public ImageAdapter(String[] headlines, String[] newsBodies, int[] imageIds) {
        this.headlines = headlines;
        this.newsBodies = newsBodies;
        this.imageIds = imageIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cardpart, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        ImageView imageView = cardView.findViewById(R.id.img);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);

        TextView headlineView = cardView.findViewById(R.id.headline);
        headlineView.setText(headlines[position]);

        TextView bodyView = cardView.findViewById(R.id.newsBody);
        bodyView.setText(newsBodies[position]);

        // Optional click listener
        cardView.setOnClickListener(v -> {
            // Handle item click
        });
    }

    @Override
    public int getItemCount() {
        return headlines.length; // Assuming all arrays are same length
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}

