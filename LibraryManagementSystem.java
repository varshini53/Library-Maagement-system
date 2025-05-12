import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagementSystem {

    // Book class
    static class Book {
        private String title;
        private String author;
        private boolean isAvailable;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
            this.isAvailable = true;
        }

        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public boolean isAvailable() { return isAvailable; }

        public void borrowBook() { isAvailable = false; }
        public void returnBook() { isAvailable = true; }

        @Override
        public String toString() {
            return title + " by " + author + " [" + (isAvailable ? "Available" : "Issued") + "]";
        }
    }

    // User class
    static class User {
        private String name;
        private ArrayList<Book> borrowedBooks;

        public User(String name) {
            this.name = name;
            borrowedBooks = new ArrayList<>();
        }

        public String getName() { return name; }

        public void borrowBook(Book book) {
            borrowedBooks.add(book);
            book.borrowBook();
        }

        public void returnBook(Book book) {
            borrowedBooks.remove(book);
            book.returnBook();
        }

        public void viewBorrowedBooks() {
            System.out.println(name + "'s borrowed books:");
            if (borrowedBooks.isEmpty()) {
                System.out.println("- None");
            } else {
                for (Book book : borrowedBooks) {
                    System.out.println("- " + book);
                }
            }
        }

        public Book getBorrowedBookByTitle(String title) {
            for (Book b : borrowedBooks) {
                if (b.getTitle().equalsIgnoreCase(title)) return b;
            }
            return null;
        }
    }

    // Library class
    static class Library {
        private ArrayList<Book> books;
        private ArrayList<User> users;

        public Library() {
            books = new ArrayList<>();
            users = new ArrayList<>();
        }

        public void addBook(Book book) { books.add(book); }
        public void registerUser(User user) { users.add(user); }

        public void showAllBooks() {
            System.out.println("Available Library Books:");
            for (Book book : books) {
                System.out.println("- " + book);
            }
        }

        public Book findBookByTitle(String title) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                    return book;
                }
            }
            return null;
        }

        public User findUserByName(String name) {
            for (User user : users) {
                if (user.getName().equalsIgnoreCase(name)) {
                    return user;
                }
            }
            return null;
        }
    }

    // Main menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();

        // Sample books and users
        lib.addBook(new Book("Java Basics", "James Gosling"));
        lib.addBook(new Book("Python Crash Course", "Eric Matthes"));
        lib.addBook(new Book("Data Structures", "Robert Lafore"));

        lib.registerUser(new User("Alice"));
        lib.registerUser(new User("Bob"));

        while (true) {
            System.out.println("\n===== Library Menu =====");
            System.out.println("1. View all books");
            System.out.println("2. Register new user");
            System.out.println("3. Borrow book");
            System.out.println("4. Return book");
            System.out.println("5. View borrowed books");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1:
                    lib.showAllBooks();
                    break;

                case 2:
                    System.out.print("Enter new user name: ");
                    String newName = sc.nextLine();
                    if (lib.findUserByName(newName) == null) {
                        lib.registerUser(new User(newName));
                        System.out.println("User registered.");
                    } else {
                        System.out.println("User already exists.");
                    }
                    break;

                case 3:
                    System.out.print("Enter your name: ");
                    String uname = sc.nextLine();
                    User user = lib.findUserByName(uname);
                    if (user == null) {
                        System.out.println("User not found. Please register first.");
                        break;
                    }
                    System.out.print("Enter book title to borrow: ");
                    String btitle = sc.nextLine();
                    Book book = lib.findBookByTitle(btitle);
                    if (book == null) {
                        System.out.println("Book not available.");
                    } else {
                        user.borrowBook(book);
                        System.out.println("Book borrowed successfully.");
                    }
                    break;

                case 4:
                    System.out.print("Enter your name: ");
                    uname = sc.nextLine();
                    user = lib.findUserByName(uname);
                    if (user == null) {
                        System.out.println("User not found.");
                        break;
                    }
                    System.out.print("Enter book title to return: ");
                    btitle = sc.nextLine();
                    Book borrowedBook = user.getBorrowedBookByTitle(btitle);
                    if (borrowedBook == null) {
                        System.out.println("You haven't borrowed this book.");
                    } else {
                        user.returnBook(borrowedBook);
                        System.out.println("Book returned.");
                    }
                    break;

                case 5:
                    System.out.print("Enter your name: ");
                    uname = sc.nextLine();
                    user = lib.findUserByName(uname);
                    if (user != null)
                        user.viewBorrowedBooks();
                    else
                        System.out.println("User not found.");
                    break;

                case 6:
                    System.out.println("Thank you for using the Library System. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
