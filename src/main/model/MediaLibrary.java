package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

// Represents a HashSet library of generic Media objects, which can be specified by changing the libraryType field
public class MediaLibrary implements Writable {
    private String name = "Media Library";
    private Media libraryType;
    private HashSet<Media> libraryList = new HashSet<>();
    private static ArrayList<String> allowedGenres = new ArrayList<>(Arrays.asList(
            "Action",
            "Adventure",
            "Crime",
            "Comedy",
            "Drama",
            "Horror",
            "Sci-Fi"));


    /*
     * MODIFIES: this
     * EFFECTS: adds the item to the library and returns true if successful
     *          returns false otherwise
     */
    public boolean addItem(Media item) {
        if (libraryType == null) {
            libraryType = item;
            libraryList.add(item);
            return true;
        } else if (libraryType.getClass() == item.getClass()) {
            libraryList.add(item);
            return true;
        }
        return false;
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes an item from the library and returns true if successful
     *          returns false otherwise
     *          if the library is empty after removal the library type is set to the non specific Media class again
     */
    public boolean removeItem(Media item) {
        for (Media listEntry : libraryList) {
            if (listEntry.equals(item)) {
                libraryList.remove(listEntry);
                return true;
            }
        }

        if (libraryList.isEmpty()) {
            libraryType = null;
        }

        return false;
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes an item from the library and returns true if successful
     *          returns false otherwise
     *          if the library is empty after removal the library type is set to the non specific Media class again
     */
    public boolean removeItem(String title, String artist) {

        for (Media listEntry : libraryList) {
            if (listEntry.title.equals(title) && listEntry.artist.equals(artist)) {
                libraryList.remove(listEntry);
                return true;
            }
        }

        if (libraryList.isEmpty()) {
            libraryType = null;
        }
        return false;
    }

    /*
     * EFFECTS: returns true if an item is in the library, false otherwise
     */
    public boolean contains(Media item) {
        for (Media listEntry : libraryList) {
            if (listEntry.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /*
     * EFFECTS: returns true if an item is in the library, false otherwise
     */
    public boolean contains(String title, String artist) {

        for (Media listEntry : libraryList) {
            if (listEntry.title.equals(title) && listEntry.artist.equals(artist)) {
                return true;
            }
        }
        return false;
    }

    /*
     * EFFECTS: returns the media item if it is found in the library, null otherwise
     */
    public Media getMediaItem(String title, String artist) {

        for (Media listEntry : libraryList) {
            if (listEntry.title.equals(title) && listEntry.artist.equals(artist)) {
                return listEntry;
            }
        }
        return null;
    }


    /*
     * EFFECTS: returns the number of entries in a library
     */
    public int librarySize() {
        return libraryList.size();
    }

    /*
     * EFFECTS: returns a new HashSet<Media> of current entries that match the genre
     */
    public HashSet<Media> getLibraryByGenre(String genre) {
        HashSet<Media> returnList = new HashSet<>();

        for (Media listEntry : libraryList) {
            if (listEntry.getGenre().equals(genre)) {
                returnList.add(listEntry);
            }
        }
        return returnList;
    }

    /*
     * EFFECTS: returns a new HashSet<Media> of current entries that match the rating
     */
    public HashSet<Media> getLibraryByRating(int rating) {
        HashSet<Media> returnList = new HashSet<>();

        for (Media listEntry : libraryList) {
            if (listEntry.getRating() == rating) {
                returnList.add(listEntry);
            }
        }
        return returnList;
    }

    /*
     * REQUIRES: newGenre is not null
     * MODIFIES: this
     * EFFECTS: if the genre is not in the list, it is added and true is returned
     *          false otherwise
     */
    public static boolean addGenreOption(String newGenre) {
        if (!allowedGenres.contains(newGenre)) {
            allowedGenres.add(newGenre);
            return true;
        }
        return false;
    }

    /*
     * REQUIRES: newGenre is not null
     * MODIFIES: this
     * EFFECTS: if the genre is in the list, it is removed and true is returned
     *          false otherwise
     */
    public static boolean removeGenreOption(String newGenre) {
        if (allowedGenres.contains(newGenre)) {
            allowedGenres.remove(newGenre);
            return true;
        }
        return false;
    }

    /*
     * EFFECTS: responsible for turning a MediaLibrary object into a JSONobject to be stored in a JSON file
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("media", mediaItemsToJson());
        return json;
    }

    /*
     * EFFECTS: returns this MediaLibrary as a JSON array
     */
    private JSONArray mediaItemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Media media : libraryList) {
            jsonArray.put(media.toJson());
        }

        return jsonArray;
    }

    public static ArrayList<String> getAllowedGenres() {
        return allowedGenres;
    }

    public HashSet<Media> getLibraryList() {
        return libraryList;
    }

    public String getName() {
        return name;
    }

}
