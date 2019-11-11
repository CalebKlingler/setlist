package edu.launchcode.setlist.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
public class Setlist {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String venue;

    @NotNull
    private String day;

    @NotNull
    private String month;

    @NotNull
    private String year;


    @ManyToMany
    private List<Song> songs;

    public Setlist(){}

    public int getId() {
        return id;
    }


    public String getVenue() {
        return venue;
    }

    public void addSong(Song song){
        songs.add(song);
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }



    public List<Song> getSongs() {
        return songs;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
