package persistence;

import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Citation: basic structure of JasonWriterTest based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {
    private MediaLibrary mediaLibrary;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;
    private Book book1;
    private Album album1;

    @BeforeEach
    public void runBefore() {
        mediaLibrary = new MediaLibrary();
        movie1 = new Movie("Blade Runner", "Ridley Scott", "Sci-Fi", 1982, 10, 117);
        movie2 = new Movie("Arrival", "Denis Villeneuve", "Sci-Fi", 2016, 10, 118);
        movie3 = new Movie("Ferris Bueller’s Day Off", "John Hughes", "Comedy", 1986, 9, 103);
        book1 = new Book("Blade Runner", "Alan E. Nourse", "Sci-Fi", 1974, 9, 245);
        album1 = new Album("Ballads 1", "Joji", "Pop", 2018, 10, 12);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected branch
        }
    }

    @Test
    void testWriterEmptyMediaLibrary() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMediaLibrary.json");
            writer.open();
            writer.write(mediaLibrary);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMediaLibrary.json");
            mediaLibrary = reader.readInLibrary("album");
            assertEquals("Media Library", mediaLibrary.getName());
            assertEquals(0, mediaLibrary.librarySize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMovieLibrary() {
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralMovieLibrary.json");
        JsonReader reader = new JsonReader("./data/testWriterGeneralMovieLibrary.json");

        try {
            mediaLibrary.addItem(movie1);
            mediaLibrary.addItem(movie2);

            writer.open();
            writer.write(mediaLibrary);
            writer.close();

            mediaLibrary = reader.readInLibrary("movie");


            assertEquals("Media Library", mediaLibrary.getName());
            assertEquals(2, mediaLibrary.librarySize());
            checkMedia("Blade Runner", "Ridley Scott", "Sci-Fi", 1982, 10, 117, movie1);
            checkMedia("Arrival", "Denis Villeneuve", "Sci-Fi", 2016, 10, 118, movie2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBookLibrary() {
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookLibrary.json");
        JsonReader reader = new JsonReader("./data/testWriterGeneralBookLibrary.json");

        try {
            mediaLibrary.addItem(book1);

            writer.open();
            writer.write(mediaLibrary);
            writer.close();

            mediaLibrary = reader.readInLibrary("book");

            assertEquals("Media Library", mediaLibrary.getName());
            assertEquals(1, mediaLibrary.librarySize());
            checkMedia("Blade Runner", "Alan E. Nourse", "Sci-Fi", 1974, 9, 245, book1);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testWriterGeneralAlbumLibrary() {
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralAlbumLibrary.json");
        JsonReader reader = new JsonReader("./data/testWriterGeneralAlbumLibrary.json");


        try {
            mediaLibrary.addItem(album1);

            writer.open();
            writer.write(mediaLibrary);
            writer.close();

            mediaLibrary = reader.readInLibrary("album");

            assertEquals("Media Library", mediaLibrary.getName());
            assertEquals(1, mediaLibrary.librarySize());
            checkMedia("Ballads 1", "Joji", "Pop", 2018, 10, 12, album1);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testWriterRemoveMovieLibrary() {
        JsonWriter writer = new JsonWriter("./data/testWriterRemoveMovieLibrary.json");
        JsonReader reader = new JsonReader("./data/testWriterRemoveMovieLibrary.json");

        try {
            mediaLibrary.addItem(movie1);
            mediaLibrary.addItem(movie2);
            mediaLibrary.addItem(movie3);

            writer.open();
            writer.write(mediaLibrary);
            writer.close();
            mediaLibrary = reader.readInLibrary("movie");

            assertEquals(3, mediaLibrary.librarySize());
            checkMedia("Blade Runner", "Ridley Scott", "Sci-Fi", 1982, 10, 117, movie1);
            checkMedia("Arrival", "Denis Villeneuve", "Sci-Fi", 2016, 10, 118, movie2);
            checkMedia("Ferris Bueller’s Day Off", "John Hughes", "Comedy", 1986, 9, 103, movie3);

            mediaLibrary.removeItem(movie3.getTitle(), movie3.getArtist());
            writer.open();
            writer.write(mediaLibrary);
            writer.close();
            mediaLibrary = reader.readInLibrary("movie");

            assertEquals(2, mediaLibrary.librarySize());
            checkMedia("Blade Runner", "Ridley Scott", "Sci-Fi", 1982, 10, 117, movie1);
            checkMedia("Arrival", "Denis Villeneuve", "Sci-Fi", 2016, 10, 118, movie2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}