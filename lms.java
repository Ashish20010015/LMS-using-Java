import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Book {
    private String name;
    private String author;
    private String publisher;
    private String address;
    private String status;
    private String ISBNCode;
    private int availability;

    public Book() {}

    public Book(String name, String author, String publisher, String address, String status, String ISBNCode, int availability) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.address = address;
        this.status = status;
        this.ISBNCode = ISBNCode;
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", ISBNCode='" + ISBNCode + '\'' +
                ", availability=" + availability +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getISBNCode() {
        return ISBNCode;
    }

    public void setISBNCode(String ISBNCode) {
        this.ISBNCode = ISBNCode;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}

abstract class User {
    protected String name;
    protected IOOperation[] operations;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    abstract public int menu();

    public void doOperation(int selection, BookList bookList) {
        operations[selection].operate(bookList);
    }
}

class NormalUser extends User {
    public NormalUser(String name) {
        super(name);
        this.operations = new IOOperation[] {
                new ExitSystem(),
                new DisplayBooks(),
                new BorrowBook(),
                new ReturnBook()
        };
    }

    @Override
    public int menu() {
        System.out.println("=============================");
        System.out.println("Hello " + this.name + ", Welcome to the library management system!");
        System.out.println("1. View books");
        System.out.println("2. Borrow books");
        System.out.println("3. Return books");
        System.out.println("0. Exit the system");
        System.out.println("=============================");
        System.out.println("Please enter your choice:");
        return LMSUtility.readMenuSelection(3);
    }
}

class Admin extends User {
    public Admin(String name) {
        super(name);
        this.operations = new IOOperation[] {
                new ExitSystem(),
                new DisplayBooks(),
                new AddBook(),
                new DeleteBook(),
                new ReplaceBook()
        };
    }

    @Override
    public int menu() {
        System.out.println("=============================");
        System.out.println("Hello " + this.name + ", Welcome to the library management system!");
        System.out.println("1. View books");
        System.out.println("2. Add books");
        System.out.println("3. Delete book");
        System.out.println("4. Modify books");
        System.out.println("0. Exit the system");
        System.out.println("=============================");
        System.out.println("Please enter your choice:");
        return LMSUtility.readMenuSelection(4);
    }
}

interface IOOperation {
    void operate(BookList books);
}

class AddBook implements IOOperation {
    @Override
    public void operate(BookList books) {
        System.out.print("Please enter Book Name: ");
        String name = LMSUtility.readString(30);
        System.out.print("Please enter book author: ");
        String author = LMSUtility.readString(20);
        System.out.print("Please enter the book publisher: ");
        String publisher = LMSUtility.readString(32);
        System.out.print("Please enter the book collection address: ");
        String address = LMSUtility.readString(24);
        System.out.print("Please enter the ISBN Code: ");
        String ISBNCode = LMSUtility.readString(20);
        System.out.print("Please enter the number of available copies: ");
        int availability = LMSUtility.readInt();

        Book book = new Book(name, author, publisher, address, "free", ISBNCode, availability);
        books.addBook(book);
        System.out.println("Successfully added!");
    }
}

class DeleteBook implements IOOperation {
    @Override
    public void operate(BookList books) {
        System.out.print("Please enter the ISBN Code of the book you want to delete: ");
        String ISBN = LMSUtility.readString(20);
        books.deleteBook(ISBN);
    }
}

class ReplaceBook implements IOOperation {
    @Override
    public void operate(BookList books) {
        if (books.getBookNumber() == 0) {
            System.out.println("There are no books in the library at present");
            return;
        }

        System.out.print("Please enter the book ISBN Code to modify: ");
        String ISBN = LMSUtility.readString(20);

        Book book = books.findBookByISBN(ISBN);
        if (book == null) {
            System.out.println("ISBN Code \"" + ISBN + "\" does not exist and cannot be modified");
            return;
        }

        System.out.print("Please enter the revised title (" + book.getName() + "): ");
        String name = LMSUtility.readString(30, book.getName());
        System.out.print("Please enter the author after modification (" + book.getAuthor() + "): ");
        String author = LMSUtility.readString(20, book.getAuthor());
        System.out.print("Please enter the revised publisher (" + book.getPublisher() + "): ");
        String publisher = LMSUtility.readString(32, book.getPublisher());
        System.out.print("Please enter the modified collection address (" + book.getAddress() + "): ");
        String address = LMSUtility.readString(24, book.getAddress());
        System.out.print("Please enter the modified number of available copies (" + book.getAvailability() + "): ");
        int availability = LMSUtility.readInt(book.getAvailability());

        book.setName(name);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setAddress(address);
        book.setAvailability(availability);

        System.out.println("Modified successfully");
    }
}

class DisplayBooks implements IOOperation {
    @Override
    public void operate(BookList books) {
        Book[] allBooks = books.getBookList();
        System.out.println("\n============================Book information============================\n");
        for (int i = 0; i < books.getBookNumber(); i++) {
            System.out.println(allBooks[i].toString());
        }
        System.out.println("\n=======================================================================\n");
    }
}

class BorrowBook implements IOOperation {
    @Override
    public void operate(BookList books) {
        if (books.getBookNumber() == 0) {
            System.out.println("There are no books in the library at present");
            return;
        }

        System.out.print("Please enter the ISBN Code of the book to borrow: ");
        String ISBN = LMSUtility.readString(20);

        Book book = books.findBookByISBN(ISBN);
        if (book == null) {
            System.out.println("ISBN Code \"" + ISBN + "\" does not exist and cannot be borrowed");
            return;
        }

        if (book.getAvailability() <= 0) {
            System.out.println("Sorry, all copies of the book have been lent out");
            return;
        }

        System.out.print("Confirm lease <" + book.getName() + "> (Y/N): ");
        char confirm = LMSUtility.readConfirmSelection();
        if (confirm == 'N') {
            return;
        }

        book.setAvailability(book.getAvailability() - 1);
        System.out.println("Lease successful");
    }
}

class ReturnBook implements IOOperation {
    @Override
    public void operate(BookList books) {
        System.out.print("Please enter the ISBN Code of the book to return: ");
        String ISBN = LMSUtility.readString(20);

        Book book = books.findBookByISBN(ISBN);
        if (book == null) {
            System.out.println("ISBN Code \"" + ISBN + "\" does not exist and cannot be returned");
            return;
        }

        System.out.print("Confirm return <" + book.getName() + "> (Y/N): ");
        char confirm = LMSUtility.readConfirmSelection();
        if (confirm == 'N') {
            return;
        }

        book.setAvailability(book.getAvailability() + 1);
        System.out.println("Return successful");
    }
}

class ExitSystem implements IOOperation {
    @Override
    public void operate(BookList books) {
        System.out.print("Are you sure to exit the account (Y/N): ");
        char confirm = LMSUtility.readConfirmSelection();
        if (confirm == 'N') {
            return;
        }
        System.out.println("Exit successful");
    }
}

class LMSUtility {
    private static Scanner sc = new Scanner(System.in);

    public static int readMenuSelection(int maxSelection) {
        while (true) {
            int selection = 0;
            try {
                selection = Integer.parseInt(readKeyBoard(1, false));
            } catch (NumberFormatException e) {
                System.out.print("Incorrect input options, please re-enter: ");
                continue;
            }
            if (selection < 0 || selection > maxSelection) {
                System.out.print("Incorrect input options, please re-enter: ");
                continue;
            }
            return selection;
        }
    }

    public static char readChar() {
        return readKeyBoard(1, false).charAt(0);
    }

    public static char readChar(char defaultValue) {
        String str = readKeyBoard(1, true);
        return str.length() == 0 ? defaultValue : str.charAt(0);
    }

    public static int readInt() {
        int number;
        while (true) {
            try {
                number = Integer.parseInt(readKeyBoard(2, false));
                break;
            } catch (NumberFormatException e) {
                System.out.print("Digital input error, please re-enter: ");
            }
        }
        return number;
    }

    public static int readInt(int defaultValue) {
        int number;
        while (true) {
            String str = readKeyBoard(2, true);
            try {
                number = str.equals("") ? defaultValue : Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Digital input error, please re-enter: ");
            }
        }
        return number;
    }

    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    public static String readString(int limit, String defaultValue) {
        String str = readKeyBoard(limit, true);
        return str.equals("") ? defaultValue : str;
    }

    public static char readConfirmSelection() {
        while (true) {
            char ch = readKeyBoard(1, false).toUpperCase().charAt(0);
            if (ch == 'Y' || ch == 'N') {
                return ch;
            } else {
                System.out.print("Selection error, please re select: ");
            }
        }
    }

    private static String readKeyBoard(int limit, boolean blankReturn) {
        String line = "";
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.length() == 0) {
                if (blankReturn) {
                    return line;
                } else {
                    continue;
                }
            }
            if (line.length() < 1 || line.length() > limit) {
                System.out.print("The length shall not be greater than " + limit + ", please re-enter: ");
                continue;
            }
            break;
        }
        return line;
    }
}

class BookList {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(String ISBN) {
        books.removeIf(book -> book.getISBNCode().equals(ISBN));
    }

    public Book findBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.getISBNCode().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

    public Book[] getBookList() {
        return books.toArray(new Book[0]);
    }

    public int getBookNumber() {
        return books.size();
    }
}

public class lms {
    public static void main(String[] args) {
        BookList bookList = new BookList();
        User user = login();
        while (true) {
            if (user == null) {
                break;
            }
            int selection = user.menu();
            if (selection == 0) {
                user = login();
                continue;
            }
            user.doOperation(selection, bookList);
        }
    }

    public static User login() {
        System.out.println("1. Ordinary users\t\t2. Administrators");
        System.out.println("0. Exit the system");
        System.out.print("Please make your choice: ");
        int selection = LMSUtility.readMenuSelection(2);
        if (selection == 0) {
            return null;
        }
        System.out.print("Please enter your name: ");
        String name = LMSUtility.readString(10);
        return selection == 1 ? new NormalUser(name) : new Admin(name);
    }
}
