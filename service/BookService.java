package com.library.service;

import com.library.model.Book;
import com.library.exception.BookNotFoundException;
import com.library.util.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Service for managing books in the library
 */
public class BookService {
    private Map<String, Book> books;
    private FileHandler fileHandler;
    private static final String BOOKS_FILE = "books.dat";
    
    public BookService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        loadBooks();
    }
    
    /**
     * Loads books from file or initializes empty map if file doesn't exist
     */
    @SuppressWarnings("unchecked")
    private void loadBooks() {
        Object data = fileHandler.readFromFile(BOOKS_FILE);
        if (data != null) {
            books = (Map<String, Book>) data;
        } else {
            books = new HashMap<>();
        }
    }
    
    /**
     * Saves books to file
     */
    private void saveBooks() {
        fileHandler.writeToFile(BOOKS_FILE, books);
    }
    
    /**
     * Adds a new book to the library
     * @param book Book to add
     * @return Added book with generated ID
     */
    public Book addBook(Book book) {
        books.put(book.getId(), book);
        saveBooks();
        return book;
    }
    
    /**
     * Updates an existing book
     * @param book Book with updated information
     * @return Updated book
     * @throws BookNotFoundException if book doesn't exist
     */
    public Book updateBook(Book book) throws BookNotFoundException {
        if (!books.containsKey(book.getId())) {
            throw new BookNotFoundException("Book with ID " + book.getId() + " not found");
        }
        books.put(book.getId(), book);
        saveBooks();
        return book;
    }
    
    /**
     * Removes a book from the library
     * @param bookId ID of book to remove
     * @return true if book was removed, false otherwise
     */
    public boolean removeBook(String bookId) {
        if (books.containsKey(bookId)) {
            books.remove(bookId);
            saveBooks();
            return true;
        }
        return false;
    }
    
    /**
     * Gets a book by its ID
     * @param bookId ID of book to get
     * @return Book if found
     * @throws BookNotFoundException if book doesn't exist
     */
    public Book getBookById(String bookId) throws BookNotFoundException {
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }
        return book;
    }
    
    /**
     * Gets all books in the library
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    /**
     * Searches for books by title
     * @param title Title to search for
     * @return List of matching books
     */
    public List<Book> searchByTitle(String title) {
        return books.values().stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
    }
    
    /**
     * Searches for books by author
     * @param author Author to search for
     * @return List of matching books
     */
    public List<Book> searchByAuthor(String author) {
        return books.values().stream()
                    .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .collect(Collectors.toList());
    }
    
    /**
     * Searches for books by ISBN
     * @param isbn ISBN to search for
     * @return List of matching books
     */
    public List<Book> searchByISBN(String isbn) {
        return books.values().stream()
                    .filter(book -> book.getIsbn().equals(isbn))
                    .collect(Collectors.toList());
    }
    
    /**
     * Gets all available books
     * @return List of available books
     */
    public List<Book> getAvailableBooks() {
        return books.values().stream()
                    .filter(book -> book.getStatus() == Book.BookStatus.AVAILABLE)
                    .collect(Collectors.toList());
    }
    
    /**
     * Gets books by category
     * @param category Category to filter by
     * @return List of books in the given category
     */
    public List<Book> getBooksByCategory(Book.BookCategory category) {
        return books.values().stream()
                    .filter(book -> book.getCategory() == category)
                    .collect(Collectors.toList());
    }
    
    /**
     * Updates the status of a book
     * @param bookId ID of book to update
     * @param status New status
     * @throws BookNotFoundException if book doesn't exist
     */
    public void updateBookStatus(String bookId, Book.BookStatus status) throws BookNotFoundException {
        Book book = getBookById(bookId);
        book.setStatus(status);
        saveBooks();
    }
}
