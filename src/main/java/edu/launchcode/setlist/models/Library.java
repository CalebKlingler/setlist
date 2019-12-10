package edu.launchcode.setlist.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Library {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne(mappedBy = "library", fetch = FetchType.EAGER)
    private User user;

    @OneToMany
    @JoinColumn(name = "song_id")
    private List<Song> songs;

    @OneToMany
    @JoinColumn(name = "setlist_id")
    private List<Setlist> setlists;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Category> categories;



    public Library(){

    }
    public void addASong(Song song){
        this.songs.add(song);
    }
    public void addSongs(List<Song> songs){
        for (Song song : songs){
            this.songs.add(song);
        }
    }
    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Setlist> getSetlists() {
        return setlists;
    }

    public void setSetlists(List<Setlist> setlists) {
        this.setlists = setlists;
    }

    public int getId() {
        return id;
    }


    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
