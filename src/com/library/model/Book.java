package com.library.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a book in the library system
 */
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int publicationYear;
    private BookStatus status;
    private BookCategory category;
    
    public Book() {
        this.id = UUID.randomUUID().toString();
        this.status = BookStatus.AVAILABLE;
    }
    
    public Book(String title, String author, String isbn, String publisher, 
                int publicationYear, BookCategory category) {
        this();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
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
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public int getPublicationYear() {
        return publicationYear;
    }
    
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
    
    public BookStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookStatus status) {
        this.status = status;
    }
    
    public BookCategory getCategory() {
        return category;
    }
    
    public void setCategory(BookCategory category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return String.format("Book [ID: %s, Title: %s, Author: %s, ISBN: %s, Status: %s]", 
                            id, title, author, isbn, status);
    }
    
    // Book status enum
    public enum BookStatus {
        AVAILABLE,
        BORROWED,
        RESERVED,
        LOST
    }
    
    // Book category enum
    public enum BookCategory {
        FICTION,
        NON_FICTION,
        REFERENCE,
        TEXTBOOK,
        CHILDREN,
        SCIENCE,
        HISTORY,
        BIOGRAPHY,
        SELF_HELP,
        OTHER
    }
}
