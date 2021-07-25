package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Citation: basic structure of JasonReaderTest based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonReaderTest extends JsonTest {
    private MediaLibrary mediaLibrary;
    private Album album1;
    private Album album2;

    @BeforeEach
    public void runBefore() {
        mediaLibrary = new MediaLibrary();
        album1 = new Album("Innervisions", "Stevie Wonder", "Soul", 1973, 10, 9);
        album2 = new Album("Ballads 1", "Joji", "Pop", 2018, 10, 12);
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MediaLibrary mediaLibrary = reader.readInLibrary("album");
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMediaLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMediaLibrary.json");
        try {
            MediaLibrary mediaLibrary = reader.readInLibrary("album");
            assertEquals("Media Library", mediaLibrary.getName());
            assertEquals(0, mediaLibrary.librarySize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAlbumLibrary() {
        JsonWriter writer = new JsonWriter("./data/testReaderGeneralAlbumLibrary.json");
        JsonReader reader = new JsonReader("./data/testReaderGeneralAlbumLibrary.json");


        try {
            mediaLibrary = reader.readInLibrary("album");

            assertEquals("Media Library", mediaLibrary.getName());
            assertEquals(2, mediaLibrary.librarySize());
            checkMedia("Innervisions", "Stevie Wonder", "Soul", 1973, 10, 9, album1);
            checkMedia("Ballads 1", "Joji", "Pop", 2018, 10, 12, album2);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}