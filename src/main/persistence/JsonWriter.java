package persistence;

import model.*;
import org.json.JSONObject;

import java.io.*;

// Writer which writes JSON representation of a MediaLibrary to a library specific JSON file
// Citation: basic structure of JsonWriter based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB_WHITESPACE = 4;
    private PrintWriter writer;
    private String fileName;


    /*
     * EFFECTS: constructor which initializes the JSON file to write to
     */
    public JsonWriter(String fileName) {
        this.fileName = fileName;
    }

    /*
     * MODIFIES: this
     * EFFECTS: opens the writer
     *          throws FileNotFoundException if destination file cannot be opened for writing
     */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(fileName));
    }

    /*
     * MODIFIES: this
     * EFFECTS: writes JSON representation of MediaLibrary to file
     */
    public void write(MediaLibrary mediaLibrary) {
        JSONObject json = mediaLibrary.toJson();
        saveToFile(json.toString(TAB_WHITESPACE));
    }

    /*
     * MODIFIES: this
     * EFFECTS: closes the writer
     */
    public void close() {
        writer.close();
    }

    /*
     * MODIFIES: this
     * EFFECTS: writes the JSON string to the file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}
