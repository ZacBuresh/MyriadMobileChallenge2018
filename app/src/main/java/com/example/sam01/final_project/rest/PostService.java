package com.example.sam01.final_project.rest;

import com.example.sam01.final_project.model.User;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//This contains everything used to post credentials to retrofit and retrieving the authorization token.
public class PostService {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://challenge.myriadapps.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private EventService loginService;

    public PostService() {
        loginService = retrofit.create(EventService.class);
    }

    public void getToken(Callback<User> callback, String username, String password){
        loginService.basicLogin(username, password).enqueue(callback);
    }
}
