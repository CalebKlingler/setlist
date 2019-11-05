package edu.launchcode.setlist.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
public class Song {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String artist;

    @NotNull
    private String name;

    @NotNull
    private int minutes;

    @NotNull
    private int seconds;

    @ManyToMany(mappedBy = "songs")
    private List<Songlist> songlists;


    @ManyToMany(mappedBy = "songs")
    private List<Setlist> setlists;

    @ManyToOne
    private Library library;


    public Song(){}


    public int getId() {
        return id;
    }


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public List<Songlist> getSonglists() {
        return songlists;
    }

    public void setSonglists(List<Songlist> songlists) {
        this.songlists = songlists;
    }

    public Library getLibary() {
        return library;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
