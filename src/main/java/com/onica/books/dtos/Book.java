package com.onica.books.dtos;


import java.io.Serializable;

public class Book implements Serializable {

    private int id;
    private String title;
    private String author;
    private String description;

    private static int bookIdCounter = 1;

    public Book(){
        this.id = bookIdCounter++;
    }

    public Book(String title, String author, String description){
        this.id = bookIdCounter++;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
