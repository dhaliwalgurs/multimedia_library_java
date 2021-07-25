package model;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

// Represents an book object which extends media and adds a pages field
public class Book extends Media {
    private int pages;

    /*
     * REQUIRES: pages must be positive
     * EFFECTS: calls the super class Media constructor then initializes the pages field
     */
    public Book(String title, String artist, String genre, int year, int rating, int pages) {
        super(title, artist, genre, year, rating);
        this.pages = pages;
    }

    /*
     * EFFECTS: calls super generic toJson and appends class specific field pages
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = super.genericMediaToJson();
        return json.put("pages", pages);
    }

    /*
     * REQUIRES: the goodreads.com includes the entry being searched for
     *           the formatting of title and author is valid for goodreads.com database search function
     * EFFECTS:  return goodreads.com URI for media item reviews and public ratings
     */
    @Override
    public String createMediaURI() {
        String uri = "https://www.goodreads.com/search?q=" + title + " " + artist + "&qid=";
        uri = uri.replace(" ", "+");
        return uri;
    }

    public int getPages() {
        return pages;
    }
}
