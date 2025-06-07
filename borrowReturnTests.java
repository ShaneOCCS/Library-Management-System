import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Description: JUnit tests for borrowBook and returnBook operations in Library.
 * Student Name: Shane O'Connell
 * Student Number: 041144343
 * Section #: 311
 * Course: CST8130 - Data Structures, Assignment 2.
 *
 * @author James Mwangi PhD
 */
public class borrowReturnTests {

    /**
     * Helper to create a Scanner from a string of inputs, each separated by newline.
     */
    private Scanner scannerFromString(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void testBorrowValidFiction() {
        Library lib = new Library();
        //Add a FictionBook with code 1, quantity 10.
        lib.addBook(scannerFromString("f\n1\nTest Title\nTest Author\n10\n"), false);

        //Now borrow 3 copies.
        Scanner borrowSc = scannerFromString("1\n3\n");
        lib.borrowBook(borrowSc);

        //After borrowing, toString should show quantity 7 in the new comma format.
        String catalog = lib.toString();
        assertTrue("Quantity should decrease to 7 after borrowing 3",
                catalog.contains("Book: 1 , Title: Test Title , Quantity: 7 , Author: Test Author , Genre: Fiction"));
    }

    @Test
    public void testBorrowReferenceFails() {
        Library lib = new Library();
        //Add a ReferenceBook with code 2, quantity 5.
        lib.addBook(scannerFromString("r\n2\nRef Title\nRef Author\n5\nThird\n"), false);

        //Attempt to borrow 1 copy of reference
        Scanner borrowSc = scannerFromString("2\n1\n");
        lib.borrowBook(borrowSc);

        //Quantity should remain 5 in the new comma format.
        String catalog = lib.toString();
        assertTrue("ReferenceBook quantity should stay 5",
                catalog.contains("Book: 2 , Title: Ref Title , Quantity: 5 , Author: Ref Author , Genre: Reference"));
    }

    @Test
    public void testBorrowInvalidCode() {
        Library lib = new Library();
        //No books added; attempt to borrow code 999.
        Scanner borrowSc = scannerFromString("999\n");
        lib.borrowBook(borrowSc);

        //Catalog should remain empty (just the header).
        String catalog = lib.toString();
        assertEquals("Empty catalog should be just header", "Library Catalog:", catalog.trim());
    }

    @Test
    public void testReturnValid() {
        Library lib = new Library();
        //Add a FictionBook with code 1, quantity 10.
        lib.addBook(scannerFromString("f\n1\nReturnTest\nAuth\n10\n"), false);
        //Borrow 4 copies.
        Scanner borrowSc = scannerFromString("1\n4\n");
        lib.borrowBook(borrowSc);

        //Return 2 copies.
        Scanner returnSc = scannerFromString("1\n2\n");
        lib.returnBook(returnSc);

        //After return, quantity should be 8 (10 - 4 + 2) in the new comma format.
        String catalog = lib.toString();
        assertTrue("Quantity should be 8 after returning 2",
                catalog.contains("Book: 1 , Title: ReturnTest , Quantity: 8 , Author: Auth , Genre: Fiction"));
    }

    @Test
    public void testReturnTooMany() {
        Library lib = new Library();
        //Add a FictionBook with code 1, quantity 5.
        lib.addBook(scannerFromString("f\n1\nOverReturn\nAuth2\n5\n"), false);
        //Borrow 2 copies.
        Scanner borrowSc = scannerFromString("1\n2\n");
        lib.borrowBook(borrowSc);

        //Attempt to return 3 copies (only 2 were borrowed).
        Scanner returnSc = scannerFromString("1\n3\n");
        lib.returnBook(returnSc);

        //Quantity should remain 3 (5 - 2) in the new comma format.
        String catalog = lib.toString();
        assertTrue("Quantity should remain 3 after invalid return attempt", catalog.contains(
                "Book: 1 , Title: OverReturn , Quantity: 3 , Author: Auth2 , Genre: Fiction"));
    }
}