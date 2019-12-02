package edu.launchcode.setlist.models;

import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.security.sasl.SaslServer;
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

    public String getTotalTime(){
        int totalMinutes = 0;
        int totalSeconds =0;
        String stringTotalSeconds ="0";
        List<Song> theSongs = this.getSongs();
        for (Song song : theSongs){
            totalMinutes += song.getMinutes();
            totalSeconds += song.getSeconds();
        }

        int secondsMinutes = totalSeconds/60;
        totalSeconds = totalSeconds % 60;
        if (totalSeconds < 10){
           stringTotalSeconds += String.valueOf(totalSeconds);
        }
        else {
           stringTotalSeconds = String.valueOf(totalSeconds);
        }
        totalMinutes += secondsMinutes;
        String stringTotalMinutes = String.valueOf(totalMinutes);
        String totalTime = stringTotalMinutes + ":" + stringTotalSeconds;
        return totalTime;
    }
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

    public void setSongs(List<Song> songs){
        this.songs = songs;
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
