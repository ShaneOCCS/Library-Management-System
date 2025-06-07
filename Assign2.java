import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Description: Main menu for the program, accepts input for specific user cases.
 * Student Name: Shane O'Connell
 * Student Number: 041144343
 * Section #: 311
 * Course: CST8130 - Data Structures, Assignment 2.
 *
 * @author/Professor: James Mwangi PhD.
 */

public class Assign2 {
    /** Variable for user-input. */
    private static int decision = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();

        while (decision != 8) {
            //Main menu brought into the loop for continuous running.
            displayMenu();
            try {
                //Checks if the input is a valid integer.
                decision = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("...Invalid input, please try again");
                sc.nextLine();
                continue;
            }

            //Options for user to choose from in the program (start of switch).
            switch (decision) {
                case 1:
                    //Adds a book to the catalog.
                    lib.addBook(sc, false);
                    break;

                case 2:
                    //Display current library catalog.
                    System.out.println(lib);
                    break;

                case 3:
                    //Borrow book(s).
                    lib.borrowBook(sc);
                    break;

                case 4:
                    //Return book(s).
                    lib.returnBook(sc);
                    break;

                case 5:
                    //Search books using binary search.
                    lib.searchBook(sc);
                    break;

                case 6:
                    //Save Library Catalog to File.
                    try {
                        lib.saveToFile(sc);
                    } catch (IOException e) {
                        System.out.println("Error saving to file: ");
                    }
                    break;

                case 7:
                    //Read Library Catalog from File.
                    try {
                        lib.readFromFile(sc);
                    } catch (IOException e) {
                        System.out.println("Error reading from file: ");
                    }
                    break;

                case 8: //Option to exit the user from the program.
                    System.out.println("This program was created by: Shane O'Connell (#041144343)");
                    System.out.println("Exiting...\n");
                    sc.close();
                    break;

                default: //Handles incorrect number input for menu.
                    System.out.println("Please enter an integer between 1 and 8.");
                    break;
            }
        }
    }

    /**
     * Displays the main menu to the user.
     */
    public static void displayMenu() {
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println("Please select one of the following: ");
        System.out.println("1: Add Book to Library");
        System.out.println("2: Display Current Library Catalog");
        System.out.println("3: Borrow Book(s)");
        System.out.println("4: Return Book(s)");
        System.out.println("5: Search for book");
        System.out.println("6: Save Library Catalog to File");
        System.out.println("7: Read Library Catalog from File");
        System.out.println("8: To Exit");
        System.out.println("-------------------------------------------------");
        System.out.print(">");
    }
}