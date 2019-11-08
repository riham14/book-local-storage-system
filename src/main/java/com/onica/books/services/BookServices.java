package com.onica.books.services;

import com.onica.books.dtos.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookServices {

    private static List<Book> books = new ArrayList<Book>();

    public List<String> listAllBooks() {
        return books.stream().map(book -> book.getTitle()).collect(Collectors.toList());
    }

    public int addBook(String title, String author, String description) {
        Book book = new Book(title, author, description);
        books.add(book);
        return book.getId();
    }

    public List<String> searchForBooks(String keyWords) {
        return books.stream().filter(book -> book.getTitle().contains(keyWords)).map(book -> book.getTitle()).collect(Collectors.toList());
    }

    public Book getBookById(int bookId) {
        return books.stream().filter(book -> book.getId() == bookId).findFirst().orElse(null);
    }

    public void editBook(Book book, String newTitle, String newAuthor, String newDescription) {
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setDescription(newDescription);
    }

    public void deserializeBooks() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("books.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        books = (List<Book>) objectInputStream.readObject();
        objectInputStream.close();
    }

    public void serializeBooks() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("books.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(books);
        objectOutputStream.close();
    }
}
