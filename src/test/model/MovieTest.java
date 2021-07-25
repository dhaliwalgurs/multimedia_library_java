package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {
    private MediaLibrary movieLibrary;
    private Movie movie1;

    @BeforeEach
    public void runBefore() {
        movieLibrary = new MediaLibrary();
        movie1 = new Movie("Blade Runner", "Ridley Scott", "Sci-Fi", 1982, 10, 117);
    }

    @Test
    public void testGetNumSongs() {
        movieLibrary.addItem(movie1);

        assertEquals(117, movie1.getMin());
    }

    @Test
    public void openWebsiteReview(){
        assertTrue(movie1.openWebsiteReview(movie1.createMediaURI()));
    }
}
