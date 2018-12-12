package com.example.sam01.final_project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sam01.final_project.R;
import com.example.sam01.final_project.model.Event;
import com.example.sam01.final_project.model.EventListAdapter;
import com.example.sam01.final_project.rest.EventService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.HORIZONTAL;
import static com.example.sam01.final_project.activity.LoginActivity.ct;
import static com.example.sam01.final_project.activity.LoginActivity.strToken;

public class EventList extends AppCompatActivity {

    static ArrayList<Event> eventList = new ArrayList<>();
    private RecyclerView.Adapter eAdapter;
    private RecyclerView.LayoutManager eLayoutManager;
    static String username;
    static SharedPreferences tokenPref, usernamePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView eventData;
        eventData = findViewById(R.id.eventData);

        tokenPref = getApplicationContext().getSharedPreferences("tokenPref", 0);
        usernamePref = getApplicationContext().getSharedPreferences("usernamePref", 0);

        //Retrieves the token and username from a previous login
        strToken = tokenPref.getString("tokenPref","");
        username = usernamePref.getString("usernamePref", "UNABLE TO RETRIEVE USER");
        System.out.println(strToken + ", " + username);
        eventData.setText(username);

        //If there are no credentials stored in shared preferences, the app starts at the login screen.
        if (strToken != "") {
            //Only retrieves data with retrofit the first time this screen shows.
            if(ct == 0) {
                ct++;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://challenge.myriadapps.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EventService eventService = retrofit.create(EventService.class);

                //Uses the interfaces getEvents method to retrieve and create event objects.
                eventService.getEvents(strToken).enqueue(new Callback<ArrayList<Event>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                        for (Event event : response.body()) {
                            //Add event objects to eventList to use with the EventListAdapter and display.
                            eventList.add(event);
                        }
                        //Puts the event objects into a recycler view using EventListAdapter.
                        RecyclerView rView = (RecyclerView) findViewById(R.id.recyclerView);
                        rView.setHasFixedSize(true);
                        DividerItemDecoration itemDecor = new DividerItemDecoration(rView.getContext(), 1);
                        rView.addItemDecoration(itemDecor);
                        eLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rView.setLayoutManager(eLayoutManager);
                        rView.setItemAnimator(new DefaultItemAnimator());
                        eAdapter = new EventListAdapter(eventList);
                        rView.setAdapter(eAdapter);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                        finish();
                    }
                });
            }
            else{
                //This runs when the user navigates back to the Events list activity.
                username = getIntent().getStringExtra("User");
                eventData.setText(username);

                RecyclerView rView = (RecyclerView) findViewById(R.id.recyclerView);
                rView.setHasFixedSize(true);
                DividerItemDecoration itemDecor = new DividerItemDecoration(rView.getContext(), 1);
                rView.addItemDecoration(itemDecor);
                eLayoutManager = new LinearLayoutManager(getApplicationContext());
                rView.setLayoutManager(eLayoutManager);
                rView.setItemAnimator(new DefaultItemAnimator());

                eAdapter = new EventListAdapter(eventList);
                rView.setAdapter(eAdapter);
            }
        }
        else{
            //Brings the user to the login screen if shared preferences doesn't have any data.
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //clears the token and username from Shared Preferences (When the app is closed you'll have to log in)
    public void signOut(MenuItem item) {
        strToken = "";
        username = "";
        tokenPref.edit().putString("tokenPref", strToken).apply();
        usernamePref.edit().putString("usernamePref", username).apply();
        Intent myIntent = new Intent(EventList.this, LoginActivity.class);
        startActivity(myIntent);
    }
}
