import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Description: Book class represents a generic library book, holding its unique code,
 * title, author, genre, and the number of copies currently in stock.
 * Student Name: Shane O'Connell
 * Student Number: 041144343
 * Section #: 311
 * Course: CST8130 - Data Structures, Assignment 2
 *
 * @author Professor James Mwangi PhD
 */
public class Book implements Comparable<Book> {

    /** bookCode variable shows the code of the book in the library, used to borrow and return by code. */
    protected int bookCode;
    /** quantityInStock shows the user how many books are left in stock, so they can borrow x amount.*/
    protected int quantityInStock;
    /** Book title variable, allows users to see what the title is.*/
    protected String title;
    /** Book author variable, allows users to see who the author is.*/
    protected String author;
    /** Book genre variable, allows users to see what genre the book is.*/
    protected String genre;
    /** Tracks how many copies are currently checked out. */
    protected int borrowedCount;

    /**
     * Constructs a new Book with the given details.
     *
     * @param bookCode the unique identifier for the book.
     * @param quantityInStock the starting number of copies available.
     * @param title the title of the book.
     * @param author the name of the book's author.
     * @param genre the genre of the book (e.g., Fiction, Non-Fiction, Reference).
     */
    public Book(int bookCode, int quantityInStock, String title, String author, String genre) {
        this.bookCode = bookCode;
        this.quantityInStock = quantityInStock;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    /**
     * Reads an integer book code from input (user or file).
     * Validates that the code is 4 digits or fewer.
     *
     * @param bookSC the Scanner used for reading input
     * @param fromFile true if reading from a file, false if from user input
     * @return true if a valid code was entered; false otherwise
     */
    public boolean inputCode(Scanner bookSC, boolean fromFile) {
        try {
            if (fromFile) {
                //From file: parse entire line
                bookCode = Integer.parseInt(bookSC.nextLine().trim());
            } else {
                //From user input: assume prompt is handled outside
                bookCode = bookSC.nextInt();
                bookSC.nextLine();
            }

            //Checks if the bookCode is greater than 4 digits.
            if (Math.abs(bookCode) > 9999) {
                System.out.println("The book code must not be greater than 4 digits.");
                return false;
            }

            return true;

        } catch (InputMismatchException | NumberFormatException e) {
            if (!fromFile) bookSC.nextLine();
            return false;
        }
    }

    /**
     * Prompts the user for the book’s code, title, author, and quantity.
     * Keeps re-prompting until each field is valid. If reading from a file, reads the values directly.
     *
     * @param bookSC    the Scanner to read user input or file input from
     * @param fromFile  true if data is being read from a file, false if from user input
     * @return true if all values were read and assigned successfully; false otherwise
     */
    public boolean addBook(Scanner bookSC, boolean fromFile) {

        //Check if we are reading the book data from a file.
        if(fromFile) {
            try {
                //Attempt to read and validate the book code from file.
                if (!inputCode(bookSC, true)) return false;
                //Read title, quantity, and author from the next lines.
                title = bookSC.nextLine().trim();
                quantityInStock = Integer.parseInt(bookSC.nextLine().trim());
                author = bookSC.nextLine().trim();
                return true;
            } catch (InputMismatchException e) {
                System.out.println("Error reading book from file.");
                return false;
            }
        }

        //Loop until a valid integer is entered.
        while(true) {
            System.out.print("Enter the code for the book: ");
            if (inputCode(bookSC, false)) {
                break; // Exit loop if valid input
            } else {
                System.out.println("Invalid code. Please enter a valid integer.");
            }
        }

        //Loop until non-empty.
        while(true) {
            System.out.print("Enter the title of the book: ");
            title = bookSC.nextLine().trim();
            if (!title.isEmpty()) {
                break;
            } else {
                System.out.println("Invalid entry. There must be a title.");
            }
        }

        //Loop until non-empty.
        while(true) {
            System.out.print("Enter the author of the book: ");
            author = bookSC.nextLine().trim();
            if (!author.isEmpty()) {
                break;
            } else {
                System.out.println("Invalid entry. There must be an author.");
            }
        }

        //Loop until a non-negative integer is entered.
        while(true) {
            System.out.print("Enter the quantity of the book: ");
            try {
                quantityInStock = bookSC.nextInt();
                bookSC.nextLine();
                if (quantityInStock < 0) {
                    System.out.println("Invalid quantity. Must be 0 or greater.");
                } else {
                    break;
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid quantity. Please enter an integer.");
                bookSC.nextLine();
            }
        }
        return true;
    }

    /**
     * Updates stock by the given amount.
     *
     * @param amount positive to add, negative to remove
     * @return true if stock was changed; false if removal was attempted and stock would go negative
     */
    public boolean updateQuantity(int amount) {
        if(quantityInStock + amount < 0) return false;
        quantityInStock += amount;
        return true;
    }

    /**
     * Checks whether this book and another book have the same title and author.
     *
     * @param book the Book to compare against
     * @return true if both title and author match (case-insensitive); false otherwise
     */
    public boolean isEqual(Book book) {
        return this.title.equalsIgnoreCase(book.title) && this.author.equalsIgnoreCase(book.author);
    }

    /**
     * Returns a string representation of this Book in the format:
     * "Book: <code> , Title: <title> , Quantity: <quantity> , Author: <author> , Genre: <genre>"
     *
     * @return formatted string with commas between fields
     */
    @Override
    public String toString() {
        return String.format(
                "Book: %d , Title: %s , Quantity: %d , Author: %s , Genre: %s", bookCode, title, quantityInStock, author, genre);
    }

    /**
     * compareTo is used to compare two book objects.
     * Used to determine how to sort the elements of the catalog.
     *
     * @param obj the object to be compared.
     * @return a comparison between two book objects, returns either -1, 0 or 1.
     */
    @Override
    public int compareTo(Book obj) {
        return Integer.compare(this.bookCode, obj.bookCode);
    }

    /**
     * Outputs this book's data to a given Formatter in the expected file format.
     * Each field is written on a new line in the following order:
     * bookCode, title, quantityInStock, author, genre.
     *
     * @param writer the Formatter used to write the book's data to a file
     */
    public void outputBook(Formatter writer) {
        writer.format("Book: %d , Title: %s , Quantity: %d , Author: %s , Genre: %s", bookCode, title, quantityInStock, author, genre);
    }
}