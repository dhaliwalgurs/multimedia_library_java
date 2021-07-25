package model;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

// Represents an movie object which extends media and adds a min field
public class Movie extends Media {
    private int min;

    /*
     * REQUIRES: min must be positive
     * EFFECTS: calls the super class Media constructor then initializes the min field
     */
    public Movie(String title, String artist, String genre, int year, int rating, int min) {
        super(title, artist, genre, year, rating);
        this.min = min;
    }

    /*
     * EFFECTS: calls super generic toJson and appends class specific field min
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = super.genericMediaToJson();
        return json.put("min", min);
    }

    /*
     * REQUIRES: the imdb.com includes the entry being searched for
     *           the formatting of title and author is valid for imdb.com database search function
     * EFFECTS:  return imdb.com URI for media item reviews and public ratings
     */
    @Override
    public String createMediaURI() {
        String uri = "https://www.imdb.com/find?q=" + title + "&ref_=nv_sr_sm";
        uri = uri.replace(" ", "+");
        return uri;
    }

    public int getMin() {
        return min;
    }
}
