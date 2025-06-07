import java.util.Formatter;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Description: Represents a reference book in the library, which can only be viewed and not borrowed.
 * Inherits common fields and behavior from Book and uses "Reference" as its genre.
 * Student Name: Shane O'Connell
 * Student Number: 041144343
 * Section #: 311
 * Course: CST8130 - Data Structures, Assignment 2.
 *
 * @author James Mwangi PhD
 */

public class ReferenceBook extends Book {

    /** Variable so the user can select an edition for the reference book.*/
    private String edition;

    /**
     * Constructs a ReferenceBook with the genre preset to "Reference".
     * Other fields are initialized to placeholder values and filled later via addBook().
     */
    public ReferenceBook() {
        super(0, 0, "", "", "Reference");
    }

    /**
     * Constructs a ReferenceBook with the genre preset to "Reference".
     * Other fields are initialized to placeholder values and filled later via addBook().
     */
    @Override
    public String toString() {
        return super.toString() + " Edition: " + edition;
    }

    /**
     * Prompts the user to enter this reference book's details including edition.
     * If reading from a file, it assumes the values are on separate lines.
     * Otherwise, prompts interactively and validates each field.
     *
     * @param scanner  Scanner to read input from (file or user)
     * @param fromFile true if reading from a file, false if from user input
     * @return true if the book was successfully added; false otherwise
     */
    @Override
    public boolean addBook(Scanner scanner, boolean fromFile) {
        if (!super.addBook(scanner, fromFile)) return false;

        if (fromFile) {
            //Read edition from file.
            if (!scanner.hasNextLine()) return false;
            edition = scanner.nextLine().trim();
        } else {
            //Prompt user for edition.
            while (true) {
                System.out.print("Enter the edition of the book: ");
                edition = scanner.nextLine().trim();
                if (!edition.isEmpty()) break;
                System.out.println("Invalid entry, there must be an edition for a reference book.");
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
        //Prints header for reference books.
    writer.format("r%n");
    writer.format("Book: %d \nTitle: %s \nQuantity: %d \nAuthor: %s \nGenre: %s \nEdition: %s \n", bookCode, title, quantityInStock, author, genre, edition);
    }
}