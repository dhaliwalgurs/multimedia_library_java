package ui;

import model.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashSet;

// GraphicalLibraryApp is responsible for the user's GUI layout, functionality and sound effects
public class GraphicalLibraryApp extends MediaLibraryUI implements ActionListener {
    private JFrame mainFrame;
    private JFrame addEntryPopUp;
    private ButtonGroup group;
    private JRadioButton bookOption;
    private JRadioButton movieOption;
    private JRadioButton albumOption;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton onlineBtn;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu viewMenu;
    private JMenuItem loadAll;
    private JMenuItem saveAll;
    private JMenuItem viewAll;
    private JMenuItem viewByRating;
    private JMenuItem viewByGenre;

    private JPanel mainPanel;
    private JTextArea viewLibraryArea;
    private JTextField field;

    /*
     * EFFECTS: runs the GUI application for the Media libraries
     */
    public GraphicalLibraryApp() {
        mainFrame = new JFrame("Media Library App");
        playSoundFile(SOUND_OPEN_PROGRAM);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        initializeLibraries();
        initializeJFrame();
        initializeButtons();
        initializeMenu();

        addEntryPopUp = new JFrame();
        viewLibraryArea = new JTextArea("Library Entries");
        field = new JTextField(0);
        bookOption.setSelected(true);                        // selects default radio button
        currentLibrary = JSON_BOOK_LIB_FILE;                 // sets the default library
        viewLibraryArea.setText(printCurrentMediaLibrary()); // view the default library

        mainFrame.add(mainPanel);
        mainFrame.add(field);
        mainPanel.add(viewLibraryArea);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setResizable(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes some of the characteristics of the JFrame object
     *          includes a sound effect before closing the program
     */
    private void initializeJFrame() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                playSoundFile(SOUND_CLOSE_PROGRAM);
                System.exit(0);
            }
        });
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLayout(new FlowLayout());
    }

    /*
     * MODIFIES: this
     * EFFECTS: calls helper methods to create buttons and add them to the JFrame object with action listeners
     */
    private void initializeButtons() {
        createButtons();
        setButtonCommands();
        addButtonListeners();
        addButtonsToFrame();
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates the button objects for the GUI
     */
    private void createButtons() {
        bookOption = new JRadioButton("Book Library");
        movieOption = new JRadioButton("Movie Library");
        albumOption = new JRadioButton("Album Library");
        group = new ButtonGroup();
        group.add(bookOption);
        group.add(movieOption);
        group.add(albumOption);
        addBtn = new JButton("Add Entry");
        removeBtn = new JButton("Remove Entry");
        onlineBtn = new JButton("Find Online");
    }

    /*
     * MODIFIES: this
     * EFFECTS: allows actions on the buttons to be differentiated within the program using String labels
     */
    private void setButtonCommands() {
        bookOption.setActionCommand("bookOption");
        movieOption.setActionCommand("movieOption");
        albumOption.setActionCommand("albumOption");
        addBtn.setActionCommand("addBtn");
        removeBtn.setActionCommand("removeBtn");
        onlineBtn.setActionCommand("onlineBtn");
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds action listeners to all the buttons
     */
    private void addButtonListeners() {
        bookOption.addActionListener(this);
        movieOption.addActionListener(this);
        albumOption.addActionListener(this);
        addBtn.addActionListener(this);
        removeBtn.addActionListener(this);
        onlineBtn.addActionListener(this);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds all the buttons to the JFrame
     */
    private void addButtonsToFrame() {
        mainFrame.add(bookOption);
        mainFrame.add(movieOption);
        mainFrame.add(albumOption);
        mainFrame.add(addBtn);
        mainFrame.add(removeBtn);
        mainFrame.add(onlineBtn);
    }

    /*
     * MODIFIES: this
     * EFFECTS: add a menu to the JFrame which allows for load and save options in a dropbox
     */
    private void initializeMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        viewMenu = new JMenu("View");

        addOptionsToFileMenu();
        addOptionsToViewMenu();

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        mainFrame.setJMenuBar(menuBar);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds options to the file menu
     */
    private void addOptionsToFileMenu() {
        loadAll = new JMenuItem("Load All");
        loadAll.setActionCommand("loadAll");
        loadAll.addActionListener(this);
        fileMenu.add(loadAll);

        saveAll = new JMenuItem("Save All");
        saveAll.setActionCommand("saveAll");
        saveAll.addActionListener(this);
        fileMenu.add(saveAll);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds options to the view menu
     */
    private void addOptionsToViewMenu() {
        viewAll = new JMenuItem("View All");
        viewAll.setActionCommand("viewAll");
        viewAll.addActionListener(this);
        viewMenu.add(viewAll);

        viewByRating = new JMenuItem("View by Rating");
        viewByRating.setActionCommand("viewByRating");
        viewByRating.addActionListener(this);
        viewMenu.add(viewByRating);

        viewByGenre = new JMenuItem("View by Genre");
        viewByGenre.setActionCommand("viewByGenre");
        viewByGenre.addActionListener(this);
        viewMenu.add(viewByGenre);
    }

    /*
     * MODIFIES: this
     * EFFECTS: handles actions performed on the various components of the JFrame
     */
    public void actionPerformed(ActionEvent e) {
        processMenuAction(e);
        processLibraryOption(e);
        processButtonAction(e);
    }

    /*
     * MODIFIES: this
     * EFFECTS: handles actions performed on the menu components of the JFrame
     */
    private void processMenuAction(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "viewAll":
            case "viewByRating":
            case "viewByGenre":
                viewLibraryWithOptions(e.getActionCommand());
                break;
            case "loadAll":
                loadAllLibrariesFromFile();
                break;
            case "saveAll":
                saveAllLibrariesToFile();
                break;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: handles actions performed on the library option radio buttons of the JFrame
     */
    private void processLibraryOption(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "bookOption":
                currentLibrary = JSON_BOOK_LIB_FILE;
                viewLibraryArea.setText(printCurrentMediaLibrary());
                playSoundFile(SOUND_CHANGE_LIBRARY);
                break;
            case "movieOption":
                currentLibrary = JSON_MOVIE_LIB_FILE;
                viewLibraryArea.setText(printCurrentMediaLibrary());
                playSoundFile(SOUND_CHANGE_LIBRARY);
                break;
            case "albumOption":
                currentLibrary = JSON_ALBUM_LIB_FILE;
                viewLibraryArea.setText(printCurrentMediaLibrary());
                playSoundFile(SOUND_CHANGE_LIBRARY);
                break;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: handles actions performed on the buttons of the JFrame
     */
    private void processButtonAction(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "addBtn":
                addEntryToCurrentLibrary();
                break;
            case "removeBtn":
                removeEntryFromLibrary();
                break;
            case "onlineBtn":
                openWithOnlineDatabase();
                break;
        }
    }

    /*
     * REQUIRES: all fields must be appropriately filled in when prompted
     * MODIFIES: this
     * EFFECTS: prompts for a Media item to enter into the current library
     */
    protected void addEntryToCurrentLibrary() {
        String title = JOptionPane.showInputDialog(addEntryPopUp, "Enter title", null);
        String artist = JOptionPane.showInputDialog(addEntryPopUp, "Enter artist", null);
        String genre = JOptionPane.showInputDialog(addEntryPopUp, "Enter genre", null);
        int year = Integer.parseInt(JOptionPane.showInputDialog(addEntryPopUp, "Enter year", null));
        int rating = Integer.parseInt(JOptionPane.showInputDialog(addEntryPopUp, "Enter rating", null));

        switch (currentLibrary) {
            case JSON_BOOK_LIB_FILE:
                int pages = Integer.parseInt(JOptionPane.showInputDialog(addEntryPopUp, "Enter pages", null));
                bookLibrary.addItem(new Book(title, artist, genre, year, rating, pages));
                break;
            case JSON_MOVIE_LIB_FILE:
                int min = Integer.parseInt(JOptionPane.showInputDialog(addEntryPopUp, "Enter min", null));
                movieLibrary.addItem(new Movie(title, artist, genre, year, rating, min));
                break;
            case JSON_ALBUM_LIB_FILE:
                int numSongs = Integer.parseInt(JOptionPane.showInputDialog(addEntryPopUp, "Enter numSongs", null));
                albumLibrary.addItem(new Album(title, artist, genre, year, rating, numSongs));
                break;
        }
        viewLibraryArea.setText(printCurrentMediaLibrary());
        playSoundFile(SOUND_ADD_ITEM);
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts a user for a Media items title and author, removes that item if found in library
     */
    protected void removeEntryFromLibrary() {
        String title = JOptionPane.showInputDialog(addEntryPopUp, "Enter title", null);
        String artist = JOptionPane.showInputDialog(addEntryPopUp, "Enter artist", null);

        switch (currentLibrary) {
            case JSON_BOOK_LIB_FILE:
                bookLibrary.removeItem(title, artist);
                break;
            case JSON_MOVIE_LIB_FILE:
                movieLibrary.removeItem(title, artist);
                break;
            case JSON_ALBUM_LIB_FILE:
                albumLibrary.removeItem(title, artist);
                break;
        }
        viewLibraryArea.setText(printCurrentMediaLibrary());
        playSoundFile(SOUND_REMOVE_ITEM);
    }

    /*
     * REQUIRES: all fields must be appropriately filled in when prompted
     *           the format for title and author should be as generic as possible for online database searches
     * EFFECTS: calls helper methods in Media class to open the item specified by the user
     */
    private void openWithOnlineDatabase() {
        String title = JOptionPane.showInputDialog(addEntryPopUp, "Enter title", null);
        String artist = JOptionPane.showInputDialog(addEntryPopUp, "Enter artist", null);

        switch (currentLibrary) {
            case JSON_BOOK_LIB_FILE:
                if (bookLibrary.getMediaItem(title, artist) != null) {
                    bookLibrary.getMediaItem(title, artist).openWebsiteReview(
                            (bookLibrary.getMediaItem(title, artist)).createMediaURI());
                }
                break;
            case JSON_MOVIE_LIB_FILE:
                if (movieLibrary.getMediaItem(title, artist) != null) {
                    movieLibrary.getMediaItem(title, artist).openWebsiteReview(
                            (movieLibrary.getMediaItem(title, artist)).createMediaURI());
                }
                break;
            case JSON_ALBUM_LIB_FILE:
                if (albumLibrary.getMediaItem(title, artist) != null) {
                    albumLibrary.getMediaItem(title, artist).openWebsiteReview(
                            (albumLibrary.getMediaItem(title, artist)).createMediaURI());
                }
                break;
        }
        playSoundFile(SOUND_ONLINE_ITEM);
    }

    /*
     * MODIFIES: this
     * EFFECTS: allows the user to view the current library as
     *          - All entries
     *          - Entries by Rating
     *          - Entries by Genre
     *          prompts the user for Rating or Genre filter if option is selected
     */
    private void viewLibraryWithOptions(String viewOption) {
        switch (viewOption) {
            case "viewAll":
                viewLibraryArea.setText(printCurrentMediaLibrary());
                break;
            case "viewByRating":
                try {
                    int rating = Integer.parseInt(JOptionPane.showInputDialog(addEntryPopUp, "Enter rating (1-10)"));
                    viewLibraryArea.setText(viewLibraryByRating(rating));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid rating.");
                    playSoundFile(SOUND_ERROR);
                }
                break;
            case "viewByGenre":
                String genre = JOptionPane.showInputDialog(addEntryPopUp, "Enter genre");
                if (genre != null) {
                    viewLibraryArea.setText(viewLibraryByGenre(genre));
                }
                break;
        }
        playSoundFile(SOUND_VIEW_LIBRARY);
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads Book, Movie and Album libraries from their respective JSON files
     */
    @Override
    protected void loadAllLibrariesFromFile() {
        super.loadAllLibrariesFromFile();
        viewLibraryArea.setText(printCurrentMediaLibrary());
        playSoundFile(SOUND_LOAD_LIBRARIES);
    }

    /*
     * EFFECTS: passes the current library with each Media entry formatted on it's own new line
     *          formatted library is returned as a String to the caller
     */
    private String printCurrentMediaLibrary() {
        HashSet<Media> library = null;

        switch (currentLibrary) {
            case JSON_BOOK_LIB_FILE:
                library = bookLibrary.getLibraryList();
                break;
            case JSON_MOVIE_LIB_FILE:
                library = movieLibrary.getLibraryList();
                break;
            case JSON_ALBUM_LIB_FILE:
                library = albumLibrary.getLibraryList();
                break;
        }
        return printMediaLibrary(library);
    }
}