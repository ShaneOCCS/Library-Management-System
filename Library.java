import java.io.*;
import java.util.*;

/**
 * CET - CS Academic Level 3
 * Description: Manages a catalog of Book objects using an ArrayList. Supports operations such as
 * adding books (manually or from file), borrowing and returning books, searching
 * using binary search, and saving/loading the catalog to/from a file.
 * Student Name: Shane O'Connell
 * Student Number: 041144343
 * Section #: 311
 * Course: CST8130 - Data Structures, Assignment 2.
 *
 * @author/Professor: James Mwangi PhD.
 */
public class Library {
    /**
     * List of books to be dynamically added to by the user.
     */
    private final ArrayList<Book> catalog;

    /**
     * Constructs an empty Library with a list of books.
     */
    public Library() {
        catalog = new ArrayList<>();
    }

    /**
     * Returns a listing of all books in the catalog, one per line.
     *
     * @return A string starting with "Library Catalog:" and a newline (if there are books),
     * followed by each Book’s toString() on its own line.
     */
    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder("Library Catalog: ");
        //Checks if books exist in catalog.
        if (!catalog.isEmpty()) {
            strb.append("\n");

            //Sorts the catalog by bookCode.
            Collections.sort(catalog);

            //Append each book’s string representation to the output.
            for (int i = 0; i < catalog.size(); i++) {
                strb.append(catalog.get(i).toString());
                if (i < catalog.size() - 1) {
                    strb.append("\n");
                }
            }
        }
        return strb.toString();
    }

    /**
     * Returns the index of a Book in the catalog list if a book with the same
     * title and author or bookCode exists. If no match is found, returns -1.
     *
     * @param book the Book to compare against existing entries
     * @return index of matching book if found; -1 otherwise
     */
    public int alreadyExists(Book book) {
        //Loop through all entries in the catalog.
        for (int i = 0; i < catalog.size(); i++) {
            //Check for title-author match or duplicate bookCode.
            if (catalog.get(i).isEqual(book) || catalog.get(i).bookCode == book.bookCode) {
                return i;
            }
        }
        //If method fails, return -1.
        return -1;
    }

    /**
     * Adds a new book to the catalog.
     * If reading from a file, the book type and details are read directly.
     * Otherwise, prompts the user for the type and fields.
     * Valid types are 'f' (Fiction), 'n' (Non-Fiction), and 'r' (Reference).
     * Prevents duplicate entries based on title and author.
     *
     * @param scanner  Scanner for input
     * @param fromFile true to read from file, false for manual input
     */
    public void addBook(Scanner scanner, boolean fromFile) {
        Book book;
        //Determine book type.
        String type;
        if (fromFile) {
            //If reading from a file, get the book type from the next line.
            if (!scanner.hasNextLine()) return;
            type = scanner.nextLine().trim().toLowerCase();
        } else {
            //Prompt user for book type until valid input is given.
            System.out.print("Do you wish to add a Fiction(f), Non-Fiction(n), or Reference(r) book? ");
            while (true) {
                type = scanner.nextLine().trim().toLowerCase();
                if (type.matches("[fnr]")) break;
                System.out.println("Invalid entry, please enter f, n, or r.");
            }
        }
        //Create the appropriate book subclass based on user-input.
        switch (type) {
            case "f":
                book = new FictionBook();
                break;
            case "n":
                book = new NonFictionBook();
                break;
            case "r":
                book = new ReferenceBook();
                break;
            default:
                return;
        }
        //Read book details and check for duplicates.
        if (book.addBook(scanner, fromFile)) {
            for (Book b : catalog)
                if (b.isEqual(book)) {
                    System.out.println("Book already exists.");
                    return;
                }
            //Add book if no duplicate found.
            catalog.add(book);
            System.out.println("Book added.");
        }
    }

    /**
     * Prompts for a book code and borrow quantity, then decreases that book’s stock
     * and increments its borrowedCount if valid.
     *
     * @param scanner Scanner for reading the book code and borrow quantity
     */
    public void borrowBook(Scanner scanner) {
        //If the catalog is empty, borrowing cannot proceed.
        if (catalog.isEmpty()) {
            System.out.println("Error...could not borrow book.");
            return;
        }

        System.out.print("Enter the code for the book: ");
        int bookCode;
        try {
            //Read book code from user.
            bookCode = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please enter an integer for the book code to search for...");
            scanner.nextLine();
            return;
        }

        //Search for matching book by code.
        for (Book book : catalog) {
            if (book.bookCode == bookCode) {
                //Reference books cannot be borrowed.
                if ("Reference".equals(book.genre)) {
                    System.out.println("Error...cannot borrow reference book");
                    return;
                }

                System.out.print("Enter valid quantity to borrow: ");
                try {
                    int qty = Integer.parseInt(scanner.nextLine().trim());
                    //Check if enough stock exists.
                    if (qty < 1 || !book.updateQuantity(-qty)) {
                        System.out.println("Error...could not borrow book");
                    } else {
                        book.borrowedCount += qty;
                        System.out.println("Borrowed: " + book.borrowedCount + " Copies of: " + book.title);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error...could not borrow book");
                }
                return;
            }
        }
        //No matching code found.
        System.out.println("Error...book not in the catalog.");
    }

    /**
     * Prompts for a book code and return quantity, then updates that book’s stock
     * and borrowedCount if valid.
     *
     * @param scanner Scanner for reading the book code and return quantity
     */
    public void returnBook(Scanner scanner) {
        //If the catalog is empty, returning cannot proceed.
        if (catalog.isEmpty()) {
            System.out.println("Error...could not return book.");
            return;
        }

        System.out.print("Enter the code for the book: ");
        int bookCode;
        try {
            //Read book code from user.
            bookCode = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please enter an integer for the book code to search for...");
            scanner.nextLine();
            return;
        }

        //Search for matching book by code.
        for (Book book : catalog) {
            if (book.bookCode == bookCode) {
                System.out.print("Enter valid quantity to return: ");
                try {
                    int qty = Integer.parseInt(scanner.nextLine().trim());
                    //Check if return quantity is valid.
                    if (qty < 1 || qty > book.borrowedCount) {
                        System.out.println("Error...Trying to return more than checkout quantity");
                    } else {
                        book.updateQuantity(qty);
                        book.borrowedCount -= qty;
                        System.out.println("Successfully returned " + qty + " Copies of " + book.title + " book.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error...could not return book");
                }
                return;
            }
        }
        //No matching code found.
        System.out.println("Error...book not in the catalog.");
    }

    /**
     * Performs a binary search for a book using its book code.
     * Prompts the user to enter the book code, then searches the sorted catalog.
     * If found, displays the book and time taken. Otherwise, shows an error.
     *
     * @param scanner Scanner for reading user input.
     */
    public void searchBook(Scanner scanner) {
        //Prompts user to enter book code.
        System.out.print("Enter the code for the book: ");
        //Local variable for book code.
        int bookCode;
        try {
            bookCode = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please enter an integer for the book code to search for...");
            return;
        }

        //Timer to check duration of bin search.
        long startNano = System.nanoTime();
        //Search range for binary search.
        int low = 0;
        int high = catalog.size() - 1;
        //Binary loop.
        while (low <= high) {
            //Finds the midpoint index.
            int mid = (low + high) / 2;
            int midCode = catalog.get(mid).bookCode;
            //Checks if the middle of the search is what we are looking for.
            if (midCode == bookCode) {
                System.out.println(catalog.get(mid));
                //End timers.
                long durationNano = System.nanoTime() - startNano;
                System.out.printf("Binary Search completed in: %d nsec%n", durationNano);
                return;
            } else if (midCode < bookCode) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        //Prints if the bookCode cannot be found in the list.
        System.out.println("Code not found in catalog...");
    }

    /**
     * Reads book data from a file and adds valid entries to the catalog.
     * Each book entry begins with a single line specifying its type.
     * Subsequent lines contain the book's details depending on type.
     * If a book entry is invalid or a duplicate is found, the process is aborted.
     *
     * @param scanner Scanner used to read the filename input from the user
     * @throws IOException if there is an issue opening or reading the file
     */
    public void readFromFile(Scanner scanner) throws IOException {
        System.out.print("Enter the filename to read from: ");
        String filename = scanner.nextLine().trim();
        File file = new File(filename);

        //Check if file exists before attempting to read.
        if (!file.exists()) {
            System.out.println("Error: File does not exist.");
            return;
        }

        //Open file and reading of MyLibrary2.txt content.
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String typeLine = fileScanner.nextLine().trim().toLowerCase();

                //Only accept exact "f", "n", or "r".
                if (!(typeLine.equals("f") || typeLine.equals("n") || typeLine.equals("r"))) {
                    System.out.println("Invalid book type: " + typeLine);
                    return;
                }

                //Instantiate appropriate subclass.
                Book book = switch (typeLine) {
                    case "f" -> new FictionBook();
                    case "n" -> new NonFictionBook();
                    case "r" -> new ReferenceBook();
                    default -> null;
                };

                //Population of data.
                if (!book.addBook(fileScanner, true)) {
                    System.out.println("Error reading book from file.");
                    return;
                }

                //Checks if the book already exists in the catalog.
                if (alreadyExists(book) >= 0) {
                    System.out.println("Book code already exists");
                    System.out.println("Error Encountered while reading the file, aborting...");
                    return;
                }
                //Adds books to catalog.
                catalog.add(book);
            }
            System.out.println("Books successfully loaded from file.");
        }
    }

    /**
     * Saves the library catalog to an existing text file.
     * Prompts the user for a filename and writes each book using outputBook.
     * Skips saving if the file doesn't exist.
     *
     * @param scanner Scanner to read the filename input.
     * @throws IOException if an error occurs while writing to the file.
     */

    public void saveToFile(Scanner scanner) throws IOException {
        System.out.print("Enter the filename to save to: ");
        String filename = scanner.nextLine().trim();
        File file = new File(filename);

        //Check if file exists before attempting to write.
        if (!file.exists()) {
            System.out.println("Error: File does not exist.");
            return;
        }

        //Write catalog contents to file using a Formatter.
        try (Formatter writer = new Formatter(file)) {
            for (Book book : catalog) {
                book.outputBook(writer);
                writer.format("%n");
            }
            System.out.println("Library catalog successfully written to file.");
        }
    }
}