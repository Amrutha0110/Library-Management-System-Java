package com.library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a book borrowing/return transaction
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String bookId;
    private String memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;
    private TransactionStatus status;
    
    public Transaction() {
        this.id = UUID.randomUUID().toString();
        this.borrowDate = LocalDate.now();
        this.status = TransactionStatus.BORROWED;
    }
    
    public Transaction(String bookId, String memberId, int loanDays) {
        this();
        this.bookId = bookId;
        this.memberId = memberId;
        this.dueDate = borrowDate.plusDays(loanDays);
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public String getMemberId() {
        return memberId;
    }
    
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    public double getFine() {
        return fine;
    }
    
    public void setFine(double fine) {
        this.fine = fine;
    }
    
    public TransactionStatus getStatus() {
        return status;
    }
    
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return String.format("Transaction [ID: %s, Book ID: %s, Member ID: %s, Borrow Date: %s, Due Date: %s, Status: %s]", 
                            id, bookId, memberId, borrowDate, dueDate, status);
    }
    
    // Transaction status enum
    public enum TransactionStatus {
        BORROWED,
        RETURNED,
        OVERDUE,
        LOST
    }
}
