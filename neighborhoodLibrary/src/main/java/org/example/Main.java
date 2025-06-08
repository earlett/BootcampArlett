package org.example;
import org.example.Book;
import org.example.Genre;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Book[] inventory = new Book[20];

        inventory[0] = new Book(73, "0-060-93546-4", "To Kill a Mockingbird", true, "Clayton Rahmsey", "A Southern coming-of-age tale tackling racial injustice and moral growth.", "Classic Fiction");
        inventory[1] = new Book(12, "0-307-47427-5", "Da Vinci Code", false, "A modern thriller involving a cryptic murder and ancient religious secrets.", "Thriller");
        inventory[2] = new Book(12, "0-590-93002-8", "No David!", true, "Clair Huggins", "A mischievous child’s adventures told through vivid illustrations.", "Children's Picture book");
        inventory[3] = new Book(45, "1-4028-9462-7", "1984", true, "Morgan Ellis", "A dystopian novel where Big Brother watches and truth is controlled.", "Dystopian");
        inventory[4] = new Book(33, "0-7432-7356-7", "Angels & Demons", false, "A gripping tale of secret societies and ancient mysteries.", "Thriller");
        inventory[5] = new Book(20, "0-14-240112-9", "The Giver", true, "Samantha Tran", "A boy discovers memory, emotion, and the truth in a controlled society.", "Dystopian");
        inventory[6] = new Book(18, "0-06-112008-1", "Brave New World", false, "A futuristic society explores pleasure, control, and individuality.", "Dystopian");
        inventory[7] = new Book(25, "0-06-440055-7", "Charlotte's Web", true, "Luis Martinez", "A young pig and spider form an unlikely friendship on a farm.", "Animal Fiction");
        inventory[8] = new Book(30, "0-394-82375-0", "The Hobbit", true, "Jennifer Snow", "A hobbit embarks on a quest to reclaim treasure from a dragon.", "Fantasy");
        inventory[9] = new Book(10, "1-56619-909-3", "Where the Wild Things Are", false, "A wild creature-filled dreamland helps a boy express his emotions.", "Children's Picture book");
        inventory[10] = new Book(15, "0-14-143955-6", "Pride and Prejudice", true, "Elena Brooks", "A witty romance that challenges class and pride in 19th-century England.", "Romance");
        inventory[11] = new Book(22, "0-553-21311-3", "Fahrenheit 451", false, " A dystopian future where books are banned and burned.", "Dystopian");
        inventory[12] = new Book(40, "0-316-76948-7", "The Catcher in the Rye", true, "Derrick James", "A teen struggles with isolation and rebellion in postwar America.", "Classic Fiction");
        inventory[13] = new Book(17, "0-7432-7355-9", "Digital Fortress", false, "A codebreaker stumbles upon a deadly NSA secret.", "Thriller");
        inventory[14] = new Book(29, "0-394-80001-7", "The Great Gatsby", true, "Kelsey Monroe", "A mysterious millionaire and lost love collide in Jazz Age New York.", "Classic Fiction");
        inventory[15] = new Book(11, "0-06-025665-6", "Where the Sidewalk Ends", false, "A collection of poems blending whimsy and wisdom for all ages.", "children's Picture book");
        inventory[16] = new Book(26, "0-394-90016-2", "The Outsiders", true, "Marcus Dean", "A tale of youth rebellion, loyalty, and class divide.", "Coming-of-Age");
        inventory[17] = new Book(19, "0-14-241037-X", "Looking for Alaska", true, "Naomi Greene", "A teenager finds love, tragedy, and meaning in a strange boarding school.", "Young Adult");
        inventory[18] = new Book(14, "0-525-47881-7", "The Fault in Our Stars", false, "Two teens fall in love while battling terminal illness.", "Romance");
        inventory[19] = new Book(21, "0-679-60139-0", "A Wrinkle in Time", true, "Tobias Reed", "A girl journeys through space and time to rescue her missing father", "Science Fiction ");

        Genre classicFiction = new Genre("Classic Fiction", "literary works with enduring themes, often exploring human nature, society, and morality.");
        Genre comingOfAge = new Genre("Coming-of-Age", "Focuses on the personal growth and development of a young protagonist as they transition into adulthood.");
        Genre thriller = new Genre("Thriller", "Fast-paced stories filled with suspense, danger, and high-stakes conflict—often involving crime or espionage.");
        Genre childrenPictureBook = new Genre("Children’s Picture Book", "Illustrated books aimed at young readers, often with simple text and moral lessons.");
        Genre mystery = new Genre("Mystery", " Revolves around solving a puzzle, crime, or unexplained event, often involving detectives or amateur sleuths.");
        Genre dystopian = new Genre("Dystopian", "Set in oppressive, controlled societies where freedom is limited—often a critique of political or social systems.");
        Genre youngAdult = new Genre("Young Adult", " Written for teen audiences, typically centered on identity, relationships, rebellion, and self-discovery.");
        Genre scienceFiction = new Genre("Science Fiction", "Focuses on futuristic technology, space, time travel, and scientific possibilities—often examining “what if?” scenarios.");
        Genre ChildrenLiterature = new Genre("Children’s Literature", "Focuses on futuristic technology, space, time travel, and scientific possibilities—often examining “what if?” scenarios.");
        Genre animalFiction = new Genre("Animal Fiction", "Features animal characters with human traits, often conveying friendship, bravery, or life lessons.");
        Genre fantasy = new Genre("Fantasy", "Features magical worlds, mythical creatures, and supernatural elements beyond the realm of reality.");
        Genre romance = new Genre("Romance", " Centers around love and romantic relationships, often with emotional or dramatic twists.");
        Genre literacyFiction = new Genre("Literacy Fiction", " Emphasizes character development, language, and thematic depth over plot-driven narratives.");
        Genre tragedy = new Genre("Tragedy", "Involves serious, often somber themes where the main character faces downfall, loss, or fatal consequences.");


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Library! \n" +
                    "Please select an action from the menu: \n" +
                    "1) Display All Books Available \n" +
                    "2) Display Books Checked Out \n" +
                    "3) Display Books by Genre \n" +
                    "4) Search Books by Title \n" +
                    "5) Check Out a Book \n" +
                    "6) Return a Book \n" +
                    "7) Exit \n");

            int userSelection = scanner.nextInt();
            scanner.nextLine();

            switch (userSelection) {
                case 1:
                    displayAllBooks(inventory);
                    break;
                case 2:
                    displayBooksCheckedOut(inventory);
                    break;
                case 3:
                    displayByGenre(inventory, scanner);
                    break;
                case 4:
                    searchByTitle(inventory, scanner);
                    break;
                case 5:
                    checkOutBook(inventory, scanner);
                    break;
                case 6:
                    returnBook(inventory, scanner);
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }

    }

    public static void displayAllBooks(Book[] inventory) {
        System.out.println("\nAll Books in Inventory:");
        for (Book book : inventory) {
            if (book != null) {
                System.out.println("- " + book.getTitle() + " | Genre: " + book.getGenre());
            }
        }
    }

    public static void displayBooksCheckedOut(Book[] inventory) {
        System.out.println("\nBooks Currently Checked Out:");
        for (Book book : inventory) {
            if (book != null && book.isCheckedOut()) {
                System.out.println("- " + book.getTitle() + " | Genre: " + book.getGenre());
            }
        }
    }

    public static void displayByGenre(Book[] inventory, Scanner scanner) {
        String[] genres = {
                "Classic Fiction", "Thriller", "Children's Picture book", "Dystopian",
                "Animal Fiction", "Fantasy", "Romance", "Young Adult", "Science Fiction",
                "Coming-of-Age"
        };

        System.out.println("\nSelect a genre to display books:");
        for (int i = 0; i < genres.length; i++) {
            System.out.println((i + 1) + ") " + genres[i]);
        }

        System.out.print("Enter the number for your chosen genre: ");
        int genreChoice;
        try {
            genreChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if (genreChoice < 1 || genreChoice > genres.length) {
            System.out.println("Invalid genre choice. Please select a valid number.");
            return;
        }

        String selectedGenre = genres[genreChoice - 1];
        System.out.println("\nBooks in the genre: " + selectedGenre);

        boolean found = false;
        for (Book book : inventory) {
            if (book != null && book.getGenre().contains(selectedGenre.toLowerCase())) {
                System.out.println("- " + book.getTitle());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found in this genre.");
        }
    }

    public static void searchByTitle(Book[] inventory, Scanner scanner) {
        System.out.print("\nEnter the title to search for: \n");
        String title = scanner.nextLine();
        boolean found = false;

        for (Book book : inventory) {
            if (book != null && book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println("- " + book.getTitle() + " | Genre: " + book.getGenre());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with that title.");
        }
    }

    public static void checkOutBook(Book[] inventory, Scanner scanner) {
        System.out.print("\nEnter the title of the book you would like to check out: \n");
        String title = scanner.nextLine();
        boolean isCheckedOut = false;

        for (Book book : inventory) {
            if (book != null && book.getTitle().contains(title.toLowerCase())) {
                boolean found = true;
                if (book.isCheckedOut()) {
                    System.out.println("Sorry, that book is already checked out.");
                } else {
                    book.setCheckedOut(true);
                    System.out.println("You have successfully checked out \"" + book.getTitle() + "\".");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Book not available for checkout.");
        }
    }

    public static void returnBook(Book[] inventory, Scanner scanner) {
        System.out.print("\nEnter the title of the book to return: \n");
        String title = scanner.nextLine();
        boolean found = false;

        for (Book book : inventory) {
            if (book != null && book.getTitle().contains(title.toLowerCase())) {
                found = true;
                if (!book.isCheckedOut()) {
                    System.out.println("That book is already returned.");
                } else {
                    book.setCheckedOut(false);
                    System.out.println("Thank you for returning \"" + book.getTitle() + "\".");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Book not available for checkout.");
        }
    }
}
