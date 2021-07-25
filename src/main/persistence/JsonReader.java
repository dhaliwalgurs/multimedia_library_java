package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// Citation: basic structure of JsonReader based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String sourceFile;
    private String libraryMediaType;

    /*
     * EFFECTS: constructs reader to read from source file
     */
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    /*
     * REQUIRES: libraryType must be one of "book", "movie", "album"
     * EFFECTS: reads MediaLibrary from file and returns it;
     *          throws IOException if an error occurs reading data from file
     */
    public MediaLibrary readInLibrary(String libraryMediaType) throws IOException {
        this.libraryMediaType = libraryMediaType;
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMediaLibrary(jsonObject);
    }

    /*
     * EFFECTS: reads source file as string and returns it
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    /*
     * EFFECTS: parses MediaLibrary from JSON object and returns it
     */
    private MediaLibrary parseMediaLibrary(JSONObject jsonObject) {
        JSONArray mediaItemArray = jsonObject.getJSONArray("media");
        MediaLibrary mediaLibrary = new MediaLibrary();

        addMediaLibrary(mediaLibrary, mediaItemArray);
        return mediaLibrary;
    }

    /*
     * MODIFIES: mediaLibrary
     * EFFECTS: parses Media items from JSON object and adds them to MediaLibrary
     */
    private void addMediaLibrary(MediaLibrary mediaLibrary, JSONArray mediaItemArray) {

        for (Object json : mediaItemArray) {
            addMediaItem(mediaLibrary, (JSONObject) json);
        }
    }

    /*
     * MODIFIES: mediaLibrary
     * EFFECTS: parses Media item from JSON object and adds it to MediaLibrary
     */
    private void addMediaItem(MediaLibrary mediaLibrary, JSONObject jsonObject) {
        Media mediaItem;
        String title = jsonObject.getString("title");
        String artist = jsonObject.getString("artist");
        String genre = jsonObject.getString("genre");
        int year = jsonObject.getInt("year");
        int rating = jsonObject.getInt("rating");

        if (libraryMediaType.equals("book")) {
            int pages = jsonObject.getInt("pages");
            mediaItem = new Book(title, artist, genre, year, rating, pages);
        } else if (libraryMediaType.equals("movie")) {
            int min = jsonObject.getInt("min");
            mediaItem = new Movie(title, artist, genre, year, rating, min);
        } else {
            int numSongs = jsonObject.getInt("numSongs");
            mediaItem = new Album(title, artist, genre, year, rating, numSongs);
        }

        mediaLibrary.addItem(mediaItem);
    }
}
