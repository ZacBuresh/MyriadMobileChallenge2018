package com.example.sam01.final_project.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam01.final_project.R;
import com.example.sam01.final_project.activity.EventDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.sam01.final_project.activity.EventDetails.parseDate;

//This takes the EventList and applies it to a recycler view.
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder>{

    private Context mContext;
    static int eventPos = 0;
    private ArrayList<Event> eventList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            image = view.findViewById(R.id.image);
            date = view.findViewById(R.id.bio);
            mContext = itemView.getContext();
        }
    }

    public EventListAdapter(ArrayList<Event> eventList){
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Event event = eventList.get(position);
        holder.name.setText(event.getTitle());
        Picasso.get().load(event.getImage_url()).into(holder.image);
        holder.date.setText(parseDate(event.getStart_date_time(), event.getEnd_date_time()));

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                eventPos = position;
                Event event = eventList.get(eventPos);
                Toast.makeText(mContext, event.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, EventDetails.class);
                intent.putExtra("Id", event.getId());
                intent.putExtra("Image", event.getImage_url());
                intent.putExtra("Event", event.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
