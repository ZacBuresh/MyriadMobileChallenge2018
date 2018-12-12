package com.example.sam01.final_project.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam01.final_project.R;
import com.example.sam01.final_project.model.Event;
import com.example.sam01.final_project.model.EventListAdapter;
import com.example.sam01.final_project.model.Speaker;
import com.example.sam01.final_project.model.SpeakerListAdapter;
import com.example.sam01.final_project.rest.EventService;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.sam01.final_project.activity.EventList.username;
import static com.example.sam01.final_project.activity.LoginActivity.strToken;

public class EventDetails extends AppCompatActivity {

    ArrayList<Event> eventDetailList = new ArrayList<Event>();
    ArrayList<Speaker> speakerArrayList = new ArrayList<Speaker>();
    ArrayList<Integer> speakerIdList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        final TextView nameView = findViewById(R.id.nameView);
        final ImageView imageDetail = findViewById(R.id.imageDetail);
        final TextView dateView = findViewById(R.id.time);
        final TextView description = findViewById(R.id.description);
        final TextView location = findViewById(R.id.location);
        Integer eventId = getIntent().getIntExtra("Id", 1);

        //Sets up the toolbar allowing the "Back Button" to bring the user back to the Events List Activity.
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventList.class);
                intent.putExtra("User", username);
                startActivity(intent);
            }
        });

        //Uses retrofit to use the EventService's method, getEventDetails, to get the selected event details.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://challenge.myriadapps.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final EventService eventService = retrofit.create(EventService.class);

        eventService.getEventDetails(strToken, eventId).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Event event = response.body();
                setTitle(event.getTitle());
                Picasso.get().load(event.getImage_url()).into(imageDetail);
                nameView.setText(event.getTitle());
                String date = parseDate(event.getStart_date_time(), event.getEnd_date_time());
                dateView.setText(date);
                description.setText(event.getEvent_description());
                location.setText(event.getLocation());

                //Edits the Speakers attribute in the event object to get all the speaker ID's.
                ArrayList speakerList = event.getSpeakers();
                for(int ct = 0; ct<speakerList.size(); ct++){
                    String speakerItem = speakerList.get(ct).toString();
                    String[] newSpeakerItem = speakerItem.split("=");
                    String speakerId = newSpeakerItem[1];
                    String[] parsedSpeakerId = speakerId.split("\\.");
                    String finalSpeakerId = parsedSpeakerId[0];
                    speakerIdList.add(Integer.parseInt(finalSpeakerId));
                }

                //Uses retrofit to use the EventService's method, getSpeakerDetails, to use the speaker ID
                //list to display the speaker's data within another recycler view.
                for(int ct = 0; ct<speakerIdList.size(); ct++){
                    eventService.getSpeakerDetails(strToken, speakerIdList.get(ct)).enqueue(new Callback<Speaker>(){

                        @Override
                        public void onResponse(Call<Speaker> call, Response<Speaker> response) {
                            Speaker speaker = response.body();
                            speakerArrayList.add(speaker);
                            RecyclerView speakerView = (RecyclerView)findViewById(R.id.speakerView);
                            speakerView.setHasFixedSize(true);
                            DividerItemDecoration itemDecor = new DividerItemDecoration(speakerView.getContext(), 1);
                            speakerView.addItemDecoration(itemDecor);
                            LinearLayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                            speakerView.setLayoutManager(eLayoutManager);
                            speakerView.setItemAnimator(new DefaultItemAnimator());

                            SpeakerListAdapter sAdapter = new SpeakerListAdapter(speakerArrayList);
                            speakerView.setAdapter(sAdapter);
                        }

                        @Override
                        public void onFailure(Call<Speaker> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //This formats the dates found in each event object to the format seen in the MyriadChallenge document.
    static public String parseDate(String startTime, String endTime){
        String[] arrStr = startTime.split("\\+");
        startTime = arrStr[0];
        String finalStartTime = startTime.replace("T"," ");
        String[] arrStr2 = endTime.split("T");
        String parsedTime = arrStr2[1];
        String[] onlyTime = parsedTime.split("\\+");
        String finalEndTime = onlyTime[0];

        String inputStart = "yyyy-MM-dd HH:mm:ss";
        String outputStart = "MM/dd/yy hh:mma";
        String inputEnd = "HH:mm:ss";
        String outputEnd = "hh:mma";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputStart);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputStart);
        SimpleDateFormat inputFmt = new SimpleDateFormat(inputEnd);
        SimpleDateFormat outputFmt = new SimpleDateFormat(outputEnd);

        Date startDate = null;
        Date endDate = null;
        String str = null;

        try {
            startDate = inputFormat.parse(finalStartTime);
            String strStart = outputFormat.format(startDate);
            endDate = inputFmt.parse(finalEndTime);
            String strEnd = outputFmt.format(endDate);
            str = strStart + "-" + strEnd;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return str;
    }
}
