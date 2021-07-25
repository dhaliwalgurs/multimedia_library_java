package ui;

import model.*;

import java.util.HashSet;
import java.util.Scanner;

// LibraryApp is responsible for the user console and all user I/O (provides an interface to the other classes)
public class ConsoleLibraryApp extends MediaLibraryUI {
    private Scanner input;

    /*
     * EFFECTS: runs the console application for the library
     */
    public ConsoleLibraryApp() {
        runLibrary();
    }

    /*
     * MODIFIES: this
     * EFFECTS: dynamically displays the console menus to read and process user inputs
     *          at any time in a sub menu, entering "q" will terminate the program
     */
    private void runLibrary() {
        boolean appRunning = true;
        currentLibrary = MAIN_MENU_CODE;
        String userInput;
        input = new Scanner(System.in);

        initializeLibraries();
        while (appRunning) {
            runCurrentMenu();

            userInput = input.nextLine();

            if (userInput.equals("q")) {
                appRunning = false;
            } else if (userInput.equals("s")) {
                saveAllLibrariesToFile();
            } else {
                processInput(userInput);
            }
        }

        System.out.println("\nAll libraries successfully closed.");
    }

    /*
     * EFFECTS: helper function which is used to determine which menu to display to the console
     */
    private void runCurrentMenu() {
        switch (currentMenu) {
            case MAIN_MENU_CODE:
                mainMenu();
                break;
            case JSON_BOOK_LIB_FILE:
            case JSON_MOVIE_LIB_FILE:
            case JSON_ALBUM_LIB_FILE:
                subLibraryMenu();
                break;
            case VIEW_MENU_CODE:
                viewMenu();
                break;
            case GENRE_MENU_CODE:
                genreMenu();
                break;
        }
    }

    /*
     * EFFECTS: displays the main menu options to user
     */
    private void mainMenu() {
        System.out.println("\nSelect media sub-library to access:");
        System.out.println("\tb -> books");
        System.out.println("\tm -> movies");
        System.out.println("\ta -> albums");
        System.out.println("\ts -> save all changes");
        System.out.println("\tl -> load all files");
        System.out.println("\tq -> quit");
    }

    /*
     * EFFECTS: displays the menu of operations for any specific sub-library to the user
     */
    private void subLibraryMenu() {
        System.out.println("\nSelect option for " + currentMenu + " library:");
        System.out.println("\tv -> view options ");
        System.out.println("\ta -> add " + currentMenu);
        System.out.println("\tr -> remove " + currentMenu);
        System.out.println("\tc -> check for " + currentMenu);
        System.out.println("\tb -> back to main menu");
        System.out.println("\ts -> save all changes");
        System.out.println("\tq -> quit");
    }

    /*
     * EFFECTS: displays the viewing menu
     *          displays options for viewing different cross-sections of a library
     */
    private void viewMenu() {
        System.out.println("\nSelect view option for " + currentLibrary + " library:");
        System.out.println("\tg -> by genre");
        System.out.println("\tr -> by rating (1 to 10)");
        System.out.println("\ta -> view all entries");
        System.out.println("\tb -> back to previous menu");
        System.out.println("\ts -> save all changes");
        System.out.println("\tq -> quit");
    }

    /*
     * EFFECTS: displays the menu for viewing a library by genre
     */
    private void genreMenu() {
        System.out.println("\nEnter from one of the following genres: ");
        for (String genre : MediaLibrary.getAllowedGenres()) {
            System.out.println("\t" + genre + " -> view by " + genre);
        }
        System.out.println("\tb -> back to previous menu");
        System.out.println("\ts -> save all changes");
        System.out.println("\tq -> quit");
    }

    /*
     * MODIFIES: this
     * EFFECTS: calls the appropriate method to handle user input processing (depending on the current menu)
     */
    private void processInput(String command) {
        switch (currentMenu) {
            case MAIN_MENU_CODE:
                processMainMenu(command);
                break;
            case JSON_BOOK_LIB_FILE:
            case JSON_MOVIE_LIB_FILE:
            case JSON_ALBUM_LIB_FILE:
                processSubLibraryMenu(command);
                break;
            case VIEW_MENU_CODE:
                processViewMenu(command);
                break;
            case GENRE_MENU_CODE:
                processGenreMenu(command);
                break;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the currentLibrary field, to indicate the current library being accessed
     */
    private void updateCurrentLibrary() {
        if (currentMenu.equals(JSON_BOOK_LIB_FILE)
                || currentMenu.equals(JSON_MOVIE_LIB_FILE)
                || currentMenu.equals(JSON_ALBUM_LIB_FILE)) {
            currentLibrary = currentMenu;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user input as a string when the main menu is open
     *          "b" will change the current library to the book library
     *          "m" will change the current library to the movie library
     *          "a" will change the current library to the album library
     *          otherwise displays an error message
     */
    private void processMainMenu(String command) {
        switch (command) {
            case "b":
                updateCurrentLibrary();
                currentMenu = JSON_BOOK_LIB_FILE;
                break;
            case "m":
                updateCurrentLibrary();
                currentMenu = JSON_MOVIE_LIB_FILE;
                break;
            case "a":
                updateCurrentLibrary();
                currentMenu = JSON_ALBUM_LIB_FILE;
                break;
            case "l":
                loadAllLibrariesFromFile();
                break;
            default:
                System.out.println("Invalid selection...");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user input as a string when any specific media library is open
     *          "v" will open the view options
     *          "a" will prompt a user to add an item
     *          "r" will prompt a user to remove an item
     *          "c" will prompt a user to check for an item
     *          "b" will return user to the main menu
     *          otherwise displays an error message
     */
    private void processSubLibraryMenu(String command) {
        switch (command) {
            case "v":
                updateCurrentLibrary();
                currentMenu = VIEW_MENU_CODE;
                break;
            case "a":
                addEntryToCurrentLibrary();
                break;
            case "r":
                removeEntryFromLibrary();
                break;
            case "c":
                checkForEntry();
                break;
            case "b":
                updateCurrentLibrary();
                currentMenu = MAIN_MENU_CODE;
                break;
            default:
                System.out.println("Invalid selection...");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user input as a string when the view menu is open
     *          "g" will open the genre options menu
     *          "r" will prompt user for a rating, to view corresponding entries
     *          "a" will call viewAllInLibrary helper, and display the whole library to console
     *          "b" will return user to the current sub-library menu
     *          otherwise displays an error message
     */
    private void processViewMenu(String command) {
        switch (command) {
            case "g":
                updateCurrentLibrary();
                currentMenu = GENRE_MENU_CODE;
                break;
            case "r":
                System.out.println("Please enter a rating (1 to 10) to see associated entries: ");
                viewLibraryByRating(Integer.parseInt(input.nextLine()));
                break;
            case "a":
                viewAllInLibrary();
                break;
            case "b":
                currentMenu = currentLibrary;
                break;
            default:
                System.out.println("Invalid selection...");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user input as a string when the view by genre menu is open
     *          displays the current list of acceptable genres
     *              if the user enters a string on the list, all entries with that genre will be displayed
     *          "b" will return user to the sub-library view menu
     *          otherwise displays an error message
     */
    private void processGenreMenu(String command) {
        boolean validSelection = false;

        for (String genre : MediaLibrary.getAllowedGenres()) {
            if (command.equals(genre)) {
                viewLibraryByGenre(genre);
                validSelection = true;
            } else if (command.equals("b")) {
                currentMenu = VIEW_MENU_CODE;
                validSelection = true;
            }
        }

        if (!validSelection) {
            System.out.println("Invalid selection...");
        }
    }

    /*
     * EFFECTS: prints the entire library of current menu to the console
     */
    private void viewAllInLibrary() {
        HashSet<Media> librarySubsetToDisplay = null;

        switch (currentLibrary) {
            case JSON_BOOK_LIB_FILE:
                librarySubsetToDisplay = bookLibrary.getLibraryList();
                break;
            case JSON_MOVIE_LIB_FILE:
                librarySubsetToDisplay = movieLibrary.getLibraryList();
                break;
            case JSON_ALBUM_LIB_FILE:
                librarySubsetToDisplay = albumLibrary.getLibraryList();
                break;
        }

        printMediaLibrary(librarySubsetToDisplay);
    }

    /*
     * REQUIRES: two entries may not simultaneously have the same author and the same title
     * MODIFIES: this
     * EFFECTS: queries user for Media object fields
     *          then calls and passes user input as parameters to helper function addToSpecificLibrary
     */
    protected void addEntryToCurrentLibrary() {
        String title;
        String artist;
        String genre;
        int year;
        int rating;

        System.out.println("Please enter the " + currentMenu + " title:");
        title = input.nextLine();
        System.out.println("Please enter the " + currentMenu + " artist:");
        artist = input.nextLine();
        System.out.println("Please enter the " + currentMenu + " genre:");
        genre = input.nextLine();
        System.out.println("Please enter the " + currentMenu + " year (CE):");
        year = Integer.parseInt(input.nextLine());
        System.out.println("Please enter the " + currentMenu + " rating (1 to 10):");
        rating = Integer.parseInt(input.nextLine());

        addToSpecificLibrary(title, artist, genre, year, rating);
    }

    /*
     * REQUIRES: two entries may not simultaneously have the same author and the same title
     * MODIFIES: this
     * EFFECTS: helper function to addMediaItem, uses parameters to create a new Media object
     *          and adds it to the appropriate library. Makes use of the current library to determine
     *          the actual type of the Media object
     */
    private void addToSpecificLibrary(String title, String artist, String genre, int year, int rating) {
        int librarySpecificInput;

        switch (currentMenu) {
            case JSON_BOOK_LIB_FILE:
                System.out.println("Please enter the " + currentMenu + " pages:");
                librarySpecificInput = Integer.parseInt(input.nextLine());
                bookLibrary.addItem(new Book(title, artist, genre, year, rating, librarySpecificInput));
                break;
            case JSON_MOVIE_LIB_FILE:
                System.out.println("Please enter the " + currentMenu + " min:");
                librarySpecificInput = Integer.parseInt(input.nextLine());
                movieLibrary.addItem(new Movie(title, artist, genre, year, rating, librarySpecificInput));
                break;
            case JSON_ALBUM_LIB_FILE:
                System.out.println("Please enter the " + currentMenu + " numSongs:");
                librarySpecificInput = Integer.parseInt(input.nextLine());
                albumLibrary.addItem(new Album(title, artist, genre, year, rating, librarySpecificInput));
                break;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: gets console input for the Title and Artist and searches the current library for an entry
     *          removes the Media entry if found, and displays a confirmation message
     */
    protected void removeEntryFromLibrary() {
        boolean removed = true;
        String title;
        String artist;

        System.out.println("Please enter the " + currentMenu + " title:");
        title = input.nextLine();
        System.out.println("Please enter the " + currentMenu + " artist:");
        artist = input.nextLine();

        switch (currentMenu) {
            case JSON_BOOK_LIB_FILE:
                bookLibrary.removeItem(title, artist);
                break;
            case JSON_MOVIE_LIB_FILE:
                movieLibrary.removeItem(title, artist);
                break;
            case JSON_ALBUM_LIB_FILE:
                albumLibrary.removeItem(title, artist);
                break;
            default:
                removed = false;
        }

        if (removed) {
            System.out.println("\nEntry from " + currentMenu + " library was successfully removed.");
        }
    }

    /*
     * EFFECTS: gets console input for the Title and Artist and searches the current library for an entry
     *          calls printMediaEntry, and prints the formatted Media entry if found
     *          otherwise prints an error message
     */
    private void checkForEntry() {
        String title;
        String artist;

        System.out.println("Please enter the " + currentMenu + " title:");
        title = input.nextLine();
        System.out.println("Please enter the " + currentMenu + " artist:");
        artist = input.nextLine();

        System.out.println("\nBelow are the details for your entry!");
        if (currentMenu.equals(JSON_BOOK_LIB_FILE) && bookLibrary.contains(title, artist)) {
            printMediaEntry(bookLibrary.getMediaItem(title, artist), currentMenu);
        } else if (currentMenu.equals(JSON_MOVIE_LIB_FILE) && movieLibrary.contains(title, artist)) {
            printMediaEntry(movieLibrary.getMediaItem(title, artist), currentMenu);
        } else if (currentMenu.equals(JSON_ALBUM_LIB_FILE) && albumLibrary.contains(title, artist)) {
            printMediaEntry(albumLibrary.getMediaItem(title, artist), currentMenu);
        } else {
            System.out.println("\n...no matching entry in " + currentMenu + " library found.");
        }
    }
}
