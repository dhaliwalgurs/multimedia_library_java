package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkMedia(String title, String artist, String genre, int year, int rating, int min, Media media) {
        assertEquals(title, media.getTitle());
        assertEquals(artist, media.getArtist());
        assertEquals(genre, media.getGenre());
        assertEquals(year, media.getYear());
        assertEquals(rating, media.getRating());
    }
}
