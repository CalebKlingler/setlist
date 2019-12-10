package edu.launchcode.setlist.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.nio.MappedByteBuffer;
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
    private List<Category> categories;


    @ManyToMany(mappedBy = "songs")
    private List<Setlist> setlists;


    @ManyToOne
    private Library library;


    public Song() {
    }

    public String getTotalTime() {
        String stringMinutes = String.valueOf(minutes);
        if (minutes < 10) {
            stringMinutes = "0" + String.valueOf(minutes);
        }
        String stringSeconds = String.valueOf(seconds);
        if (seconds < 10) {
            stringSeconds = "0" + String.valueOf(seconds);
        }
        return stringMinutes + ":" + stringSeconds;
    }

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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }


    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}

