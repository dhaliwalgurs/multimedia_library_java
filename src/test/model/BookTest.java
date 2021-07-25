package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    private MediaLibrary bookLibrary;
    private Book book1;

    @BeforeEach
    public void runBefore() {
        bookLibrary = new MediaLibrary();
        book1 = new Book("Blade Runner", "Alan E. Nourse", "Sci-Fi", 1974, 9, 245);
    }

    @Test
    public void testGetNumSongs() {
        bookLibrary.addItem(book1);

        assertEquals(245, book1.getPages());
    }

    @Test
    public void openWebsiteReview(){
        assertTrue(book1.openWebsiteReview(book1.createMediaURI()));
    }
}
