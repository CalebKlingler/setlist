package edu.launchcode.setlist.models.forms;

import edu.launchcode.setlist.models.Setlist;
import edu.launchcode.setlist.models.Song;

import javax.validation.constraints.NotNull;

public class AddSongToSetlistForm {
    private Setlist setlist;
    private Iterable<Song> songs;

    @NotNull
    private int setlistId;
    @NotNull
    private int songId;

    public AddSongToSetlistForm() {
    }

    public AddSongToSetlistForm(Setlist setlist, Iterable<Song> songs) {
        this.setlist = setlist;
        this.songs = songs;
    }

    public Setlist getSetlist() {
        return setlist;
    }

    public void setSetlist(Setlist setlist) {
        this.setlist = setlist;
    }

    public Iterable<Song> getSongs() {
        return songs;
    }

    public void setSongs(Iterable<Song> songs) {
        this.songs = songs;
    }

    public int getSetlistId() {
        return setlistId;
    }

    public void setSetlistId(int setlistId) {
        this.setlistId = setlistId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
