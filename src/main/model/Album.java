package model;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

// Represents an album object which extends media and adds a numSongs field
public class Album extends Media {
    private int numSongs;

    /*
     * REQUIRES: numSongs must be positive
     * EFFECTS: calls the super class Media constructor then initializes the numSongs field
     */
    public Album(String title, String artist, String genre, int year, int rating, int numSongs) {
        super(title, artist, genre, year, rating);
        this.numSongs = numSongs;
    }

    /*
     * EFFECTS: calls super generic toJson and appends class specific field numSongs
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = super.genericMediaToJson();
        return json.put("numSongs", numSongs);
    }

    /*
     * REQUIRES: the pitchfork.com includes the entry being searched for
     *           the formatting of title and author is valid for pitchfork.com database search function
     * EFFECTS:  return pitchfork.com URI for media item reviews and public ratings
     */
    @Override
    public String createMediaURI() {
        String uri = "https://pitchfork.com/search/?query=" + title + " " + artist;
        uri = uri.replace(" ", "%20");
        return uri;
    }

    public int getNumSongs() {
        return numSongs;
    }
}
