package com.example.finalproject.Yahya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

public class ScheduleAdapter
        extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    private Context context;
    private List<ScheduleItem> items;


    public ScheduleAdapter(Context context, List<ScheduleItem> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_card,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ScheduleItem item = items.get(position);
        CardView cardView = holder.cardView;

        // Access your views inside schedule_card layout
        TextView subject = cardView.findViewById(R.id.textViewSubject);
        TextView time = cardView.findViewById(R.id.textViewTime);
        TextView location = cardView.findViewById(R.id.textViewRoom);

        subject.setText(item.getSubjectName());
        time.setText(item.getStartTime());
        location.setText(item.getLocation());
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}