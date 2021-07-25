package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumTest {
    private MediaLibrary albumLibrary;
    private Album album1;

    @BeforeEach
    public void runBefore() {
        albumLibrary = new MediaLibrary();
        album1 = new Album("Innervisions", "Stevie Wonder", "Soul", 1973, 10, 9);
    }

    @Test
    public void testGetNumSongs() {
        albumLibrary.addItem(album1);

        assertEquals(9, album1.getNumSongs());
    }

    @Test
    public void testCreateMediaURI(){

    }

    @Test
    public void openWebsiteReview(){
        assertTrue(album1.openWebsiteReview(album1.createMediaURI()));
    }
}
