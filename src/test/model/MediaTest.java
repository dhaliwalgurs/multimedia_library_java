package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MediaTest {
    private MediaLibrary bookLibrary;
    private MediaLibrary movieLibrary;
    private MediaLibrary albumLibrary;
    private Media movie1;
    private Media book1;
    private Media album1;
    private String newGenre;
    private int newRating;

    @BeforeEach
    public void runBefore() {
        bookLibrary = new MediaLibrary();
        movieLibrary = new MediaLibrary();
        albumLibrary = new MediaLibrary();
        movie1 = new Movie("Blade Runner", "Ridley Scott", "Sci-Fi", 1982, 10, 117);
        book1 = new Book("Blade Runner", "Alan E. Nourse", "Sci-Fi", 1974, 9, 245);
        album1 = new Album("Innervisions", "Stevie Wonder", "Soul", 1973, 10, 9);
        newGenre = "Action";
        newRating = 10;
    }

    @Test
    public void testSetGenre() {
        movieLibrary.addItem(movie1);
        movieLibrary.getMediaItem(movie1.getTitle(), movie1.getArtist()).setGenre(newGenre);
        assertEquals(newGenre, movieLibrary.getMediaItem(movie1.getTitle(), movie1.getArtist()).getGenre());
    }

    @Test
    public void testSetRating() {
        bookLibrary.addItem(book1);
        bookLibrary.getMediaItem(book1.getTitle(), book1.getArtist()).setRating(newRating);
        assertEquals(newRating, bookLibrary.getMediaItem(book1.getTitle(), book1.getArtist()).getRating());
    }

    @Test
    public void testGetYear() {
        albumLibrary.addItem(album1);
        albumLibrary.getMediaItem(album1.getTitle(), album1.getArtist()).setRating(newRating);
        assertEquals(1973, albumLibrary.getMediaItem(album1.getTitle(), album1.getArtist()).getYear());
    }

    @Test
    public void openWebsiteReviewUnsuccessful(){
        assertFalse(album1.openWebsiteReview("NOT A CORRECT URI FORMAT"));
    }
}


