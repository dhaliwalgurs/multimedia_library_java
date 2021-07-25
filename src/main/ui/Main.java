package ui;

// The main class, used to launch the project application
public class Main {
    private static MediaLibraryUI userInterface;

    /*
     * EFFECTS: initializes a new LibraryApp, launching the console for the application
     */
    public static void main(String[] args) {
        // userInterface = new ConsoleLibraryApp();         // uncomment to launch the console version of the program
        userInterface = new GraphicalLibraryApp();
    }
}
