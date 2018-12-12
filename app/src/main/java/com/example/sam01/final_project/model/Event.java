package com.example.sam01.final_project.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Event {
    private Integer id;
    private String title, image_url, start_date_time, end_date_time, location, event_description;
    private Boolean featured;
    private ArrayList speakers;

    public Event(){

    }

    public Event(Integer id, String title, String image_url, String event_description,
                 String start_date_time, String end_date_time, String location, Boolean featured,
                 ArrayList speakers) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
        this.event_description = event_description;
        this.start_date_time = start_date_time;
        this.end_date_time = end_date_time;
        this.location = location;
        this.featured = featured;
        this.speakers = speakers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(String end_date_time) {
        this.end_date_time = end_date_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public ArrayList getSpeakers() {
        return speakers;
    }

    public void setSpeakers(ArrayList speakers) {
        this.speakers = speakers;
    }
}
