package com.example.practice;

public class Race {
    int id, all_cost, places;
    String time_arrival, time_departure, arrival_place, departure_place, time;
    public Race() {
    }
    protected  Race(int id, String departure_place, String arrival_place, String time_departure, String time_arrival, String time, int all_cost, int places)
    {
        this.all_cost = all_cost;
        this.id = id;
        this.departure_place = departure_place;
        this.arrival_place = arrival_place;
        this.time_arrival =time_arrival;
        this.time_departure =time_departure;
        this.time = time;
        this.places =places;
    }
    public int getAll_cost() {
        return all_cost;
    }
    public void setAll_cost(int all_cost) {
        this.all_cost = all_cost;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public String getArrival_place() {
        return arrival_place;
    }

    public String getDeparture_place() {
        return departure_place;
    }

    public String getTime() {
        return time;
    }

    public String getTime_arrival() {
        return time_arrival;
    }

    public String getTime_departure() {
        return time_departure;
    }

    public void setArrival_place(String arrival_place) {
        this.arrival_place = arrival_place;
    }

    public void setDeparture_place(String departure_place) {
        this.departure_place = departure_place;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTime_arrival(String time_arrival) {
        this.time_arrival = time_arrival;
    }

    public void setTime_departure(String time_departure) {
        this.time_departure = time_departure;
    }
}
