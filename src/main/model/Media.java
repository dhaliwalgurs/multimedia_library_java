package model;

import org.json.JSONObject;
import persistence.Writable;

import java.io.IOException;
import java.net.URISyntaxException;

// Represents a type of media with title, artist, genre, year and rating (1 to 10)
public abstract class Media implements Writable {
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 10;

    protected String title;
    protected String artist;
    protected String genre;
    protected int year;         // year is in CE, BCE is represented by negative integer
    protected int rating;       // 1 to 10 inclusive


    /*
     * REQUIRES: two entries may not simultaneously have the same title and artist
     *           string parameters must be non-zero in length
     * EFFECTS: abstract class constructor, sets fields for non-specific media object
     *          negative years will be treated as BCE
     *          rating below MIN_RATING is set as MIN_RATING, and above MAX_RATING is set as MAX_RATING
     */
    public Media(String title, String artist, String genre, int year, int rating) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        if (rating < MIN_RATING) {
            this.rating = MIN_RATING;
        } else {
            this.rating = Math.min(rating, MAX_RATING);
        }
    }

    /*
     * EFFECTS: responsible for turning a Media object into a JSONobject to be stored in a JSON file
     */
    @Override
    public abstract JSONObject toJson();

    /*
     * EFFECTS: converts implementation independent Media fields to Json, to be called by subclasses
     */
    protected JSONObject genericMediaToJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("artist", artist);
        json.put("genre", genre);
        json.put("year", year);
        json.put("rating", rating);
        return json;
    }

    /*
     * REQUIRES: the website includes the entry being searched for
     *           the formatting of title and author is valid for online database search function
     * EFFECTS:  opens a website with ratings and/or critical reviews of the media item
     *           returns true if successful, false otherwise
     */
    public boolean openWebsiteReview(String uri) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(uri));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
     * EFFECTS:  turns the given media item into a URI in string format, specified to a specific online database
     */
    public abstract String createMediaURI();

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    /*
     * MODIFIES: this
     * EFFECTS: rating below MIN_RATING is set as MIN_RATING, and above MAX_RATING is set as MAX_RATING
     */
    public void setRating(int rating) {
        if (rating < MIN_RATING) {
            this.rating = MIN_RATING;
        } else {
            this.rating = Math.min(rating, MAX_RATING);
        }
    }

    public int getYear() {
        return year;
    }
}
