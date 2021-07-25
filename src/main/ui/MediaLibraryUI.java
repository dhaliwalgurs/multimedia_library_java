package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

// MediaLibraryUI houses methods for UI layout, functionality and sound effects for both console based and graphical UI
// Citation: sound effects from https://www.wavsource.com/sfx/sfx3.htm
public abstract class MediaLibraryUI {
    public static final String JSON_BOOK_LIB_FILE = "./data/bookLibrary.json";
    public static final String JSON_MOVIE_LIB_FILE = "./data/movieLibrary.json";
    public static final String JSON_ALBUM_LIB_FILE = "./data/albumLibrary.json";
    public static final String MAIN_MENU_CODE = "Main";
    public static final String VIEW_MENU_CODE = "View";
    public static final String GENRE_MENU_CODE = "Genre";
    public static final String SOUND_OPEN_PROGRAM = "./data/sound_open_program.wav";
    public static final String SOUND_CLOSE_PROGRAM = "./data/sound_close_program.wav";
    public static final String SOUND_ADD_ITEM = "./data/sound_add_item.wav";
    public static final String SOUND_REMOVE_ITEM = "./data/sound_remove_item.wav";
    public static final String SOUND_ONLINE_ITEM = "./data/sound_online_item.wav";
    public static final String SOUND_LOAD_LIBRARIES = "./data/sound_load_libraries.wav";
    public static final String SOUND_SAVE_LIBRARIES = "./data/sound_save_libraries.wav";
    public static final String SOUND_VIEW_LIBRARY = "./data/sound_view_library.wav";
    public static final String SOUND_CHANGE_LIBRARY = "./data/sound_change_library.wav";
    public static final String SOUND_ERROR = "./data/sound_error.wav";

    protected MediaLibrary bookLibrary;
    protected MediaLibrary movieLibrary;
    protected MediaLibrary albumLibrary;
    protected String currentLibrary;
    protected String currentMenu = MAIN_MENU_CODE;

    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;
    protected Boolean muteProgram = false;

    /*
     * MODIFIES: this
     * EFFECTS: initializes the book, movie and album libraries
     */
    protected void initializeLibraries() {
        bookLibrary = new MediaLibrary();
        movieLibrary = new MediaLibrary();
        albumLibrary = new MediaLibrary();
    }

    protected abstract void addEntryToCurrentLibrary();

    protected abstract void removeEntryFromLibrary();

    /*
     * MODIFIES: this
     * EFFECTS: loads a specific library from JSON file
     */
    protected void loadLibraryFromFile(String fileLocation) {
        jsonReader = new JsonReader(fileLocation);

        try {
            switch (fileLocation) {
                case JSON_BOOK_LIB_FILE:
                    System.out.println("Loaded " + bookLibrary.getName() + " from " + fileLocation);
                    bookLibrary = jsonReader.readInLibrary("book");
                    break;
                case JSON_MOVIE_LIB_FILE:
                    movieLibrary = jsonReader.readInLibrary("movie");
                    movieLibrary = jsonReader.readInLibrary("movie");
                    break;
                case JSON_ALBUM_LIB_FILE:
                    albumLibrary = jsonReader.readInLibrary("album");
                    albumLibrary = jsonReader.readInLibrary("album");
                    break;
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + fileLocation);
            playSoundFile(SOUND_ERROR);
        }
        playSoundFile(SOUND_ADD_ITEM);
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads Book, Movie and Album libraries from their respective JSON files
     */
    protected void loadAllLibrariesFromFile() {
        muteProgram = true;
        loadLibraryFromFile(JSON_BOOK_LIB_FILE);
        loadLibraryFromFile(JSON_MOVIE_LIB_FILE);
        loadLibraryFromFile(JSON_ALBUM_LIB_FILE);
        muteProgram = false;
    }

    /*
     * EFFECTS: saves all of the Media libraries to their own JSON file
     */
    protected void saveAllLibrariesToFile() {
        saveBookLibraryToFile();
        saveMovieLibraryToFile();
        saveAlbumLibraryToFile();
        System.out.println("All libraries have been successfully saved to file.");
        playSoundFile(SOUND_SAVE_LIBRARIES);
    }

    /*
     * EFFECTS: saves the book library to it's JSON file
     */
    protected void saveBookLibraryToFile() {
        jsonWriter = new JsonWriter(JSON_BOOK_LIB_FILE);

        try {
            jsonWriter.open();
            jsonWriter.write(bookLibrary);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_BOOK_LIB_FILE);
            playSoundFile(SOUND_ERROR);
        }
    }

    /*
     * EFFECTS: saves the movie library to it's JSON file
     */
    protected void saveMovieLibraryToFile() {
        jsonWriter = new JsonWriter(JSON_MOVIE_LIB_FILE);

        try {
            jsonWriter.open();
            jsonWriter.write(movieLibrary);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_MOVIE_LIB_FILE);
            playSoundFile(SOUND_ERROR);
        }
    }

    /*
     * EFFECTS: saves the album library to it's JSON file
     */
    protected void saveAlbumLibraryToFile() {
        jsonWriter = new JsonWriter(JSON_ALBUM_LIB_FILE);

        try {
            jsonWriter.open();
            jsonWriter.write(albumLibrary);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_ALBUM_LIB_FILE);
            playSoundFile(SOUND_ERROR);
        }
    }

    /*
     * EFFECTS: takes rating the user wants and prints the returned library to the UI
     */
    protected String viewLibraryByRating(int rating) {
        HashSet<Media> librarySubsetToDisplay = null;

        switch (currentLibrary) {
            case JSON_BOOK_LIB_FILE:
                librarySubsetToDisplay = bookLibrary.getLibraryByRating(rating);
                break;
            case JSON_MOVIE_LIB_FILE:
                librarySubsetToDisplay = movieLibrary.getLibraryByRating(rating);
                break;
            case JSON_ALBUM_LIB_FILE:
                librarySubsetToDisplay = albumLibrary.getLibraryByRating(rating);
                break;
        }
        printMediaLibrary(librarySubsetToDisplay);
        return printMediaLibrary(librarySubsetToDisplay);
    }

    /*
     * EFFECTS: calls getLibraryByGenre helper and prints the returned library to the console/GUI
     */
    protected String viewLibraryByGenre(String genre) {
        HashSet<Media> librarySubsetToDisplay = null;

        switch (currentLibrary) {
            case JSON_BOOK_LIB_FILE:
                librarySubsetToDisplay = bookLibrary.getLibraryByGenre(genre);
                break;
            case JSON_MOVIE_LIB_FILE:
                librarySubsetToDisplay = movieLibrary.getLibraryByGenre(genre);
                break;
            case JSON_ALBUM_LIB_FILE:
                librarySubsetToDisplay = albumLibrary.getLibraryByGenre(genre);
                break;
        }
        System.out.println("PREVIOUS MENU IS: " + currentLibrary);
        System.out.println("CURRENT MENU IS: " + currentMenu);
        printMediaLibrary(librarySubsetToDisplay);
        return printMediaLibrary(librarySubsetToDisplay);
    }

    /*
     * EFFECTS: method returns a formatted string to the caller for a given HashSet<Media> library field
     *          and prints the library to the console
     *          otherwise prints an error message
     */
    protected String printMediaLibrary(HashSet<Media> library) {
        String mediaLibraryAsString = "";

        if (library != null) {
            System.out.println("\nBelow are the details for your entry!");
            for (Media media : library) {
                mediaLibraryAsString = mediaLibraryAsString.concat(printMediaEntry(media, currentLibrary));
            }
        } else {
            System.out.println("No matching entries were found in the " + currentLibrary + " library:");
        }
        return mediaLibraryAsString;
    }

    /*
     * EFFECTS: prints a single formatted Media entry to the console on a new line, also returns as a string
     */
    protected String printMediaEntry(Media mediaItem, String menuToUse) {
        String mediaEntryAsString = "";

        System.out.print("{Title: " + mediaItem.getTitle() + ", Artist: " + mediaItem.getArtist()
                + ", Genre: " + mediaItem.getGenre() + ", Year: " + mediaItem.getYear() + ", Rating: "
                + mediaItem.getRating());

        mediaEntryAsString = mediaEntryAsString.concat("{Title: " + mediaItem.getTitle() + ", Artist: "
                + mediaItem.getArtist() + ", Genre: " + mediaItem.getGenre() + ", Year: " + mediaItem.getYear()
                + ", Rating: " + mediaItem.getRating());

        switch (menuToUse) {
            case JSON_BOOK_LIB_FILE:
                System.out.println(", Pages: " + ((Book) mediaItem).getPages() + "}");
                mediaEntryAsString = mediaEntryAsString.concat(", Pages: " + ((Book) mediaItem).getPages() + "}");
                break;
            case JSON_MOVIE_LIB_FILE:
                System.out.println(", Min: " + ((Movie) mediaItem).getMin() + "}");
                mediaEntryAsString = mediaEntryAsString.concat(", Min: " + ((Movie) mediaItem).getMin() + "}");
                break;
            case JSON_ALBUM_LIB_FILE:
                System.out.println(", Songs: " + ((Album) mediaItem).getNumSongs() + "}");
                mediaEntryAsString = mediaEntryAsString.concat(", Songs: " + ((Album) mediaItem).getNumSongs() + "}");
                break;
        }
        return mediaEntryAsString + "\n";
    }

    /*
     * EFFECTS: plays the passed .wav sound file if the GUI is not currently muted
     */
    protected void playSoundFile(String soundFile) {
        if (!muteProgram) {
            try {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                System.out.println("Unsupported Audio File Exception");
                playSoundFile(SOUND_ERROR);
            } catch (LineUnavailableException e) {
                System.out.println("Line Unavailable Exception");
                playSoundFile(SOUND_ERROR);
            } catch (IOException e) {
                System.out.println("IOException Exception");
                playSoundFile(SOUND_ERROR);
            }
        }
    }
}
