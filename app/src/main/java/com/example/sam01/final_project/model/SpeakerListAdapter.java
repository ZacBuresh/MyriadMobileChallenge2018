package com.example.sam01.final_project.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam01.final_project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//This takes the Speaker objects and applies it to a recycler view.
public class SpeakerListAdapter extends RecyclerView.Adapter<SpeakerListAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<Speaker> speakerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, bio;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            image = view.findViewById(R.id.image);
            bio = view.findViewById(R.id.bio);
            mContext = itemView.getContext();
        }
    }

    public SpeakerListAdapter(ArrayList<Speaker> speakerList){
        this.speakerList = speakerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Speaker speaker = speakerList.get(position);
        holder.name.setText(speaker.getFirst_name() + " " + speaker.getLast_name());
        Picasso.get().load(speaker.getImage_url()).into(holder.image);
        holder.bio.setText(speaker.getBio());
    }

    @Override
    public int getItemCount() {
        return speakerList.size();
    }
}
