package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class MediaLibraryTest {
    private MediaLibrary bookLibrary;
    private MediaLibrary movieLibrary;
    private MediaLibrary albumLibrary;
    private Media movie1;
    private Media movie2;
    private Media movie3;
    private Media movie4;
    private Media movie5;
    private Media book1;
    private Media album1;
    private String newGenre;


    @BeforeEach
    public void runBefore() {
        bookLibrary = new MediaLibrary();
        movieLibrary = new MediaLibrary();
        albumLibrary = new MediaLibrary();
        movie1 = new Movie("Blade Runner", "Ridley Scott", "Sci-Fi", 1982, 10, 117);
        movie2 = new Movie("Arrival", "Denis Villeneuve", "Sci-Fi", 2016, 10, 118);
        movie3 = new Movie("Ferris Bueller’s Day Off", "John Hughes", "Comedy", 1986, 9, 103);
        movie4 = new Movie("Gattaca", "Andrew Niccol", "Sci-Fi", 1997, -10, 121);
        movie5 = new Movie("Gravity", "Alfonso Cuaron", "Sci-Fi", 2013, 99, 91);
        book1 = new Book("Blade Runner", "Alan E. Nourse", "Sci-Fi", 1974, 9, 245);
        album1 = new Album("Innervisions", "Stevie Wonder", "Soul", 1973, 10, 9);
        newGenre = "Romance";
    }

    @Test
    public void testMediaConstructorRatingTooLow() {
        assertEquals(Media.MIN_RATING, movie4.getRating());
    }

    @Test
    public void testMediaConstructorRatingTooHigh() {
        assertEquals(Media.MAX_RATING, movie5.getRating());
    }

    @Test
    public void testSetRatingTooLow() {
        movie5.setRating(-1);
        assertEquals(Media.MIN_RATING, movie4.getRating());
    }

    @Test
    public void testAddItem() {
        movieLibrary.addItem(movie1);

        assertTrue(movieLibrary.contains(movie1));
        assertEquals(1, movieLibrary.librarySize());
    }

    @Test
    public void testAddItemTwice() {
        albumLibrary.addItem(album1);
        albumLibrary.addItem(album1);

        assertTrue(albumLibrary.contains(album1));
        assertEquals(1, albumLibrary.librarySize());
    }

    @Test
    public void testAddTwoItems() {
        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie2);

        assertTrue(movieLibrary.contains(movie1));
        assertTrue(movieLibrary.contains(movie2));
        assertEquals(2, movieLibrary.librarySize());
    }

    @Test
    public void testAddItemWrongLibrary() {
        movieLibrary.addItem(movie1);
        movieLibrary.addItem(book1);

        assertTrue(movieLibrary.contains(movie1));
        assertEquals(1, movieLibrary.librarySize());
    }

    @Test
    public void testAddItemSameItem() {
        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie1);

        assertTrue(movieLibrary.contains(movie1));
        assertEquals(1, movieLibrary.librarySize());
    }

    @Test
    public void testRemoveItem() {
        albumLibrary.addItem(album1);
        albumLibrary.removeItem(album1);

        assertFalse(albumLibrary.contains(album1));
        assertEquals(0, albumLibrary.librarySize());
    }

    @Test
    public void testRemoveItemOverload() {
        albumLibrary.addItem(album1);
        albumLibrary.removeItem(album1.getTitle(), album1.getArtist());

        assertFalse(albumLibrary.contains(album1.getTitle(), album1.getArtist()));
        assertEquals(0, albumLibrary.librarySize());
    }

    @Test
    public void testRemoveItemArtistNotFound() {
        movieLibrary.addItem(movie1);
        movieLibrary.removeItem(movie1.getTitle(), movie2.getArtist());

        assertFalse(movieLibrary.contains(movie1.getTitle(), movie2.getArtist()));
        assertEquals(1, movieLibrary.librarySize());
    }

    @Test
    public void testRemoveItemTitleNotFound() {
        movieLibrary.addItem(movie1);
        movieLibrary.removeItem(movie2.getTitle(), movie1.getArtist());

        assertFalse(movieLibrary.contains(movie2.getTitle(), movie2.getArtist()));
        assertEquals(1, movieLibrary.librarySize());
    }

    @Test
    public void testRemoveItemNoItemToRemove() {
        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie2);

        assertFalse(movieLibrary.removeItem(movie3));
        assertEquals(2, movieLibrary.librarySize());
    }

    @Test
    public void testRemoveItemNoItemToRemoveOverload() {
        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie2);

        assertFalse(movieLibrary.removeItem(movie3.getTitle(), movie3.getArtist()));
        assertEquals(2, movieLibrary.librarySize());
    }

    @Test
    public void testRemoveItemEmptyLibrary() {
        assertFalse(bookLibrary.removeItem(book1));
        assertEquals(0, bookLibrary.librarySize());
    }

    @Test
    public void testRemoveItemEmptyLibraryOverload() {
        assertFalse(bookLibrary.removeItem(book1.getTitle(), book1.getArtist()));
        assertEquals(0, bookLibrary.librarySize());
    }

    @Test
    public void testRemoveItemSearchForItem() {
        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie2);
        movieLibrary.addItem(movie3);
        movieLibrary.removeItem(movie3);

        assertTrue(movieLibrary.contains(movie1));
        assertTrue(movieLibrary.contains(movie2));
        assertFalse(movieLibrary.contains(movie3));
        assertEquals(2, movieLibrary.librarySize());
    }

    @Test
    public void testRemoveItemSearchForItemOverload() {
        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie2);
        movieLibrary.addItem(movie3);
        movieLibrary.removeItem(movie3.getTitle(), movie3.getArtist());

        assertTrue(movieLibrary.contains(movie1.getTitle(), movie1.getArtist()));
        assertTrue(movieLibrary.contains(movie2.getTitle(), movie2.getArtist()));
        assertFalse(movieLibrary.contains(movie3.getTitle(), movie3.getArtist()));
        assertEquals(2, movieLibrary.librarySize());
    }

    @Test
    public void testContainsWrongTitle() {
        movieLibrary.addItem(movie1);

        assertFalse(movieLibrary.contains(movie2.getTitle(), movie1.getArtist()));
        assertEquals(1, movieLibrary.librarySize());
    }

    @Test
    public void testContainsWrongArtist() {
        movieLibrary.addItem(movie1);

        assertFalse(movieLibrary.contains(movie1.getTitle(), movie2.getArtist()));
        assertEquals(1, movieLibrary.librarySize());
    }

    @Test
    public void testGetMediaItem() {
        albumLibrary.addItem(album1);
        assertEquals(album1, albumLibrary.getMediaItem(album1.getTitle(), album1.getArtist()));
    }

    @Test
    public void testGetMediaItemCorrectTitleWrongArtist() {
        albumLibrary.addItem(movie1);
        assertNull(albumLibrary.getMediaItem(movie1.getTitle(), movie2.getArtist()));
    }

    @Test
    public void testGetMediaItemWrongTitleCorrectArtist() {
        albumLibrary.addItem(movie1);
        assertNull(albumLibrary.getMediaItem(movie2.getTitle(), movie1.getArtist()));
    }

    @Test
    public void testGetMediaItemNoItem() {
        assertNull(albumLibrary.getMediaItem(album1.getTitle(), album1.getArtist()));
    }

    @Test
    public void testGetMediaItemSearchForItem() {
        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie2);
        movieLibrary.addItem(movie3);
        assertEquals(movie3, movieLibrary.getMediaItem(movie3.getTitle(), movie3.getArtist()));
    }

    @Test
    public void testGetLibraryByGenre() {
        HashSet<Media> returnList;

        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie2);
        movieLibrary.addItem(movie3);

        returnList = movieLibrary.getLibraryByGenre("Sci-Fi");
        assertTrue(movieLibrary.contains(movie1));
        assertTrue(movieLibrary.contains(movie2));
        assertEquals(2, returnList.size());
    }

    @Test
    public void testGetLibraryByRating() {
        HashSet<Media> returnList;

        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie2);
        movieLibrary.addItem(movie3);

        returnList = movieLibrary.getLibraryByRating(10);
        assertTrue(movieLibrary.contains(movie1));
        assertTrue(movieLibrary.contains(movie2));
        assertEquals(2, returnList.size());
    }

    @Test
    public void testAddGenreOption() {
        int originalNumGenreOptions = MediaLibrary.getAllowedGenres().size();

        assertTrue(MediaLibrary.addGenreOption(newGenre));
        assertEquals(originalNumGenreOptions + 1, MediaLibrary.getAllowedGenres().size());
        MediaLibrary.removeGenreOption(newGenre);
    }

    @Test
    public void testAddGenreOptionAlreadyIncluded() {
        int originalNumGenreOptions = MediaLibrary.getAllowedGenres().size();

        assertTrue(MediaLibrary.addGenreOption(newGenre));
        assertFalse(MediaLibrary.addGenreOption(newGenre));
        assertEquals(originalNumGenreOptions + 1, MediaLibrary.getAllowedGenres().size());
        MediaLibrary.removeGenreOption(newGenre);
    }

    @Test
    public void removeGenreOption() {
        int originalNumGenreOptions = MediaLibrary.getAllowedGenres().size();

        assertTrue(MediaLibrary.addGenreOption(newGenre));
        assertTrue(MediaLibrary.removeGenreOption(newGenre));
        assertEquals(originalNumGenreOptions, MediaLibrary.getAllowedGenres().size());
    }

    @Test
    public void removeGenreOptionAlreadyRemoved() {
        int originalNumGenreOptions = MediaLibrary.getAllowedGenres().size();

        assertFalse(MediaLibrary.removeGenreOption(newGenre));
        assertEquals(originalNumGenreOptions, MediaLibrary.getAllowedGenres().size());
    }

    @Test
    public void testGetLibraryList() {
        movieLibrary.addItem(movie1);
        movieLibrary.addItem(movie2);

        assertEquals(movieLibrary.librarySize(), movieLibrary.getLibraryList().size());
    }


}