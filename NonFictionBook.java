import java.util.Formatter;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Description: Represents a non-fiction book in the library, which can be borrowed and returned.
 * Inherits common fields and behavior from Book and uses "Non-Fiction" as its genre.
 * Student Name: Shane O'Connell
 * Student Number: 041144343
 * Section #: 311
 * Course: CST8130 - Data Structures, Assignment 2.
 *
 * @author/Professor: James Mwangi PhD.
 */

public class NonFictionBook extends Book {

    /** Allows the user to see the field of study the book is based on in the library. */
    private String fieldOfStudy;

    /**
     * Constructs a NonFictionBook with the genre preset to "Non-Fiction".
     * Other fields are initialized to placeholder values and
     * filled later via addBook(Scanner).
     */
    public NonFictionBook() {
        super(0, 0 , "", "", "Non-Fiction");
    }

    /**
     * Returns a string representation of this NonFictionBook,
     * including its field of study.
     *
     * @return formatted string including code, title, quantity, author, genre,
     *         and field of study
     */
    @Override
    public String toString() {
        return super.toString() + " Field of Study: " + fieldOfStudy;
    }

    /**
     * Reads or prompts for NonFictionBook data including code, title, author, quantity,
     * and the specific field of study. Delegates shared field input to the base Book class.
     *
     * @param scanner   Scanner to read input (from user or file)
     * @param fromFile  true if input is from file; false if interactive
     * @return true if all fields were successfully read; false otherwise
     */
    @Override
    public boolean addBook(Scanner scanner, boolean fromFile) {
        if (!super.addBook(scanner, fromFile)) return false;
        //If reading from file, try to read the field of study from the next line.
        if (fromFile) {
            if (!scanner.hasNextLine()) return false;
            fieldOfStudy = scanner.nextLine().trim();
        } else {
            //Prompt user if not reading from file.
            while (true) {
                System.out.print("Enter the field of Study: ");
                fieldOfStudy = scanner.nextLine().trim();
                if (!fieldOfStudy.isEmpty()) break;
                System.out.println("Invalid entry. There must be at least one field of study.");
            }
        }
        return true;
    }


    /**
     * Writes the reference book's details to the specified Formatter in the expected file format.
     *
     * @param writer the Formatter used to output the reference book's data to a file
     */
    @Override
    public void outputBook(Formatter writer) {
        writer.format("n%n");
        writer.format("Book: %d\nTitle: %s\nQuantity: %d\nAuthor: %s\nGenre: %s\nField: %s\n", bookCode, title, quantityInStock, author, genre, fieldOfStudy);
    }
}