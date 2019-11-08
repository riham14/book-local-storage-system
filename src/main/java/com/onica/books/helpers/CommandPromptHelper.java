package com.onica.books.helpers;

import com.onica.books.dtos.Book;
import com.onica.books.services.BookServices;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CommandPromptHelper {

    private Scanner scanner;

    private BookServices bookServices = new BookServices();

    public int fireInitialPrompt() {
        scanner = new Scanner(System.in);
        System.out.println("\n ==== Book Manager ====");
        System.out.println("1) View all books");
        System.out.println("2) Add a book");
        System.out.println("3) Edit a book");
        System.out.println("4) Search for a book");
        System.out.println("5) Save and exit\n ");
        System.out.print("Choose [1-5]: ");

        return scanner.nextInt();
    }

    public void handle_listBooks() {
        System.out.println("\n ==== View Books ==== ");
        List<String> books = bookServices.listAllBooks();
        this.viewBooksList(books);

        if (!books.isEmpty()) {
            String bookId = "";
            do {
                scanner = new Scanner(System.in);
                System.out.println("\n To view details enter the book ID, to return press <Enter>.");
                System.out.print("Book ID: ");
                bookId = scanner.nextLine();
                if (bookId != null && !bookId.isEmpty()) {
                    Book book = bookServices.getBookById(Integer.parseInt(bookId));
                    this.viewBookDetails(book);
                }
            } while (!bookId.isEmpty());
        }
    }

    public void handle_booksSearch() {
        scanner = new Scanner(System.in);

        System.out.println("\n ==== Search  ====\n");
        System.out.println("Type in one or more keywords to search for:\n");
        System.out.print("Search: ");

        String keyWords = scanner.nextLine();
        List<String> booksSearchList = bookServices.searchForBooks(keyWords);

        System.out.println("\n The following books matched your query. Enter the book ID to see more details, or <Enter> to return.");
        this.viewBooksList(booksSearchList);
        this.handle_chooseBookId();
    }

    public int handle_addBook() {
        scanner = new Scanner(System.in);

        System.out.println("\n ==== Add a Book ====\n");
        System.out.println("Please enter the following information:\n");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();

        return bookServices.addBook(title, author, description);
    }

    private void handle_chooseBookId() {
        String bookId = "";
        do {
            scanner = new Scanner(System.in);
            System.out.print("\n Book ID: ");
            bookId = scanner.nextLine();
            Book book = bookServices.getBookById(Integer.parseInt(bookId));
            if (Objects.nonNull(book)) {
                viewBookDetails(book);
            } else {
                System.out.println("No Book Exists with Such ID");
            }
        } while (bookId != null);
    }

    public void handle_editBook() {
        System.out.println("\n ==== Edit a Book ====");
        List<String> books = bookServices.listAllBooks();
        this.viewBooksList(books);
        this.editBooks();
    }

    private void editBooks() {
        String bookId = "";
        do {
            scanner = new Scanner(System.in);
            System.out.println("\n Enter the book ID of the book you want to edit; to return press <Enter>.");
            System.out.print("Book ID: ");
            bookId = scanner.nextLine();
            if (bookId != null && !bookId.isEmpty()) {
                this.viewBookDetailsForEdit(Integer.parseInt(bookId));
            }
        } while (!bookId.isEmpty());
    }

    private void viewBookDetailsForEdit(int bookId) {
        Book book = bookServices.getBookById(bookId);
        System.out.println("\n Input the following information. To leave a field unchanged, hit <Enter>");

        scanner = new Scanner(System.in);
        System.out.println("Title: [" + book.getTitle() + "]: ");
        String input = scanner.nextLine();
        String newTitle = input.isEmpty() ? book.getAuthor() : input;
        System.out.println("Author: [" + book.getAuthor() + "]: ");
        input = scanner.nextLine();
        String newAuthor = input.isEmpty() ? book.getAuthor() : input;
        System.out.println("Description: [" + book.getDescription() + "]: ");
        input = scanner.nextLine();
        String newDescription = input.isEmpty() ? book.getDescription() : input;
        bookServices.editBook(book, newTitle, newAuthor, newDescription);
        System.out.println("\n Book saved.");

    }

    private void viewBookDetails(Book book) {
        System.out.println("\n ID: " + book.getId());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Description: " + book.getDescription());
    }

    private void viewBooksList(List<String> books) {
        IntStream.range(0, books.size()).forEach(index -> {
            System.out.println("[" + (index + 1) + "] " + books.get(index));
        });
    }

    public void handle_save() {
        try {
            bookServices.serializeBooks();
            System.out.println("Library saved.");
        } catch (IOException ex) {
            System.out.println("Failed To Save Books.");
        }
    }

    public void handle_readBooks(){
        try {
            bookServices.deserializeBooks();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed To Load Books.");
        }
    }
}
