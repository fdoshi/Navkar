package com.solutionplanets.navkar;

public class Summary {
    //private String uId;
    private String date;
    private String duration;
    private String count;

    public Summary() {
        //empty constructor is needed
    }

    public Summary(String date, String duration, String count) {
        //this.uId = uId;
        this.date = date;
        this.duration = duration;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getCount() {
        return count;
    }
}