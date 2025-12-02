package com.library.exception;

/**
 * Exception thrown when a book is not found
 */
public class BookNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public BookNotFoundException(String message) {
        super(message);
    }
}
