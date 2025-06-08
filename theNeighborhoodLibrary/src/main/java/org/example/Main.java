package org.example;

public class Main {
    public static void main(String[] args) {
        package org.example;

import java.util.Scanner;

        public class Main {

            public static void main(String[] args) {
                // Book inventory setup
                Book[] inventory = new Book[20];
                inventory[0] = new Book(73, "0-060-93546-4", "To Kill a Mockingbird", true, "Clayton Rahmsey", "A Southern coming-of-age tale tackling racial injustice and moral growth.");
                inventory[1] = new Book(12, "0-307-47427-5", "Da Vinci Code", false, "A modern thriller involving a cryptic murder and ancient religious secrets.");
                inventory[2] = new Book(12, "0-590-93002-8", "No David!", true, "Clair Huggins", "A mischievous child’s adventures told through vivid illustrations.");
                inventory[3] = new Book(45, "1-4028-9462-7", "1984", true, "Morgan Ellis", "A dystopian novel where Big Brother watches and truth is controlled.");
                inventory[4] = new Book(33, "0-7432-7356-7", "Angels & Demons", false, "A gripping tale of secret societies and ancient mysteries.");
                inventory[5] = new Book(20, "0-14-240112-9", "The Giver", true, "Samantha Tran", "A boy discovers memory, emotion, and the truth in a controlled society.");
                // Add more books as needed...

                // Genre setup
                Genre classicFiction = new Genre("Classic Fiction", "Literary works with enduring themes, often exploring human nature, society, and morality.");
                Genre comingOfAge = new Genre("Coming-of-Age", "Focuses on the personal growth and development of a young protagonist as they transition into adulthood.");
                Genre thriller = new Genre("Thriller", "Fast-paced stories filled with suspense, danger, and high-stakes conflict—often involving crime or espionage.");
                // Add more genres as needed...

                Scanner scanner = new Scanner(System.in);

                // Main menu interaction
                System.out.println("Welcome to the Library! \nPlease select an action from the menu\n1) Display All Books Available\n2) Display Books Checked Out\n3) Exit");
                int userSelection = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character after nextInt()

                switch (userSelection) {
                    case 1:
                        displayAllBooks(inventory);
                        break;
                    case 2:
                        displayBooksCheckedOut(inventory);
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid selection. Please choose 1, 2, or 3.");
                }

            }

            public static void displayAllBooks(Book[] inventory) {
                System.out.println("All Books Available:");
                for (Book book : inventory) {
                    if (book != null) {
                        System.out.println(book.getTitle() + " by " + book.getCheckedOutTo() + (book.isCheckedOut() ? " (Checked Out)" : ""));
                    }
                }
            }

            public static void displayBooksCheckedOut(Book[] inventory) {
                System.out.println("Books Currently Checked Out:");
                for (Book book : inventory) {
                    if (book != null && book.isCheckedOut()) {
                        System.out.println(book.getTitle() + " by " + book.getCheckedOutTo());
                    }
                }
            }

            public static void searchByTitle(Book[] inventory, Scanner scanner) {
                System.out.println("Enter the title to search for:");
                String title = scanner.nextLine().toLowerCase();  // Convert to lowercase for case-insensitive search
                boolean found = false;
                for (Book book : inventory) {
                    if (book != null && book.getTitle().toLowerCase().contains(title)) {
                        System.out.println(book.getTitle() + " by " + book.getCheckedOutTo());
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No books found with that title.");
                }
            }

            public static void returnedBook(Book[] inventory, Scanner scanner

        }
}