package com.example.sam01.final_project.rest;

import com.example.sam01.final_project.model.Event;
import com.example.sam01.final_project.model.Speaker;
import com.example.sam01.final_project.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

//This interface creates all the methods used for retrofit retrieval.
public interface EventService {

    @GET("api/v1/events")
    Call<ArrayList<Event>> getEvents(@Header("Authorization") String token);

    @GET("api/v1/events/{id}")
    Call<Event> getEventDetails(@Header("Authorization") String token,
                                @Path("id") Integer eventId);

    @GET("api/v1/speakers/{id}")
    Call<Speaker> getSpeakerDetails(@Header("Authorization") String token,
                                    @Path("id") Integer speakerId);

    @FormUrlEncoded
    @POST("api/v1/login")
    Call<User> basicLogin(
            @Field("Username") String username,
            @Field("Password") String password
    );
}
