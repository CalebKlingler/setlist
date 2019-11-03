package edu.launchcode.setlist.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Library {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    @JoinColumn(name = "song_id")
    private List<Song> songs;


    public int getId() {
        return id;
    }


    @OneToMany
    @JoinColumn(name = "song_id")
    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
