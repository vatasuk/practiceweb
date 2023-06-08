package com.example.practice;

public class Stop {
    int id, race_id, cost_pr_stop, av_places;
    String arr_time, dep_time, stop_place, next_place;
    public Stop() {
    }
    protected Stop (int id, int race_id, String stop_place, String arr_time, String dep_time, int cost_pr_stop, int av_places, String next_place){
        this.id = id;
        this.arr_time = arr_time;
        this.av_places = av_places;
        this.dep_time = dep_time;
        this.cost_pr_stop = cost_pr_stop;
        this.race_id =race_id;
        this.stop_place = stop_place;
        this.next_place = next_place;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getAv_places() {
        return av_places;
    }

    public int getCost_pr_stop() {
        return cost_pr_stop;
    }

    public int getRace_id() {
        return race_id;
    }

    public String getArr_time() {
        return arr_time;
    }

    public String getDep_time() {
        return dep_time;
    }

    public void setArr_time(String arr_time) {
        this.arr_time = arr_time;
    }

    public void setAv_places(int av_places) {
        this.av_places = av_places;
    }

    public void setDep_time(String dep_time) {
        this.dep_time = dep_time;
    }

    public void setCost_pr_stop(int cost_pr_stop) {
        this.cost_pr_stop = cost_pr_stop;
    }

    public void setRace_id(int race_id) {
        this.race_id = race_id;
    }

    public String getStop_place() {
        return stop_place;
    }

    public void setStop_place(String stop_place) {
        this.stop_place = stop_place;
    }

    public String getNext_place() {
        return next_place;
    }

    public void setNext_place(String next_place) {
        this.next_place = next_place;
    }
}
