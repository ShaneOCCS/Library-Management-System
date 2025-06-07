import java.util.Formatter;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Description:Represents a fiction book in the library, which can be borrowed and returned.
 * Inherits common fields and behavior from Book and uses "Fiction" as its genre.
 * Student Name: Shane O'Connell
 * Student Number: 041144343
 * Section #: 311
 * Course: CST8130 - Data Structures, Assignment 2.
 *
 * @author/Professor: James Mwangi PhD.
 */

public class FictionBook extends Book {

    /**
     * Constructs a FictionBook with the genre preset to "Fiction".
     * Other fields are initialized to placeholder values and
     * filled later via addBook(Scanner).
     */
    public FictionBook() {
        super(0, 0 , "", "", "Fiction");
    }

    /**
     * Prompts the user to enter this book's code, title, author, and quantity.
     * Delegates the common prompt logic to the base class.
     *
     * @return formatted string including code, title, quantity, author, and genre.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Prompts the user to enter this fiction book's code, title, author, and quantity.
     * This method simply reuses the base Book class logic since no extra fields are required.
     *
     * @param scanner  Scanner to read user input or file content from
     * @param fromFile true if reading from a file; false if interactive input
     * @return true if all fields were successfully read and validated; false otherwise
     */
    @Override
    public boolean addBook(Scanner scanner, boolean fromFile) {
        return super.addBook(scanner, fromFile);
    }

    /**
     * Writes the reference book's details to the specified Formatter in the expected file format.
     *
     * @param writer the Formatter used to output the reference book's data to a file
     */
    @Override
    public void outputBook(Formatter writer) {
        //Header for fictional books.
        writer.format("f%n");
        writer.format("Book: %d\nTitle: %s\nQuantity: %d\nAuthor: %s\nGenre: %s\n", bookCode, title, quantityInStock, author, genre);
    }
}