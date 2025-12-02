package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberNotFoundException;
import com.library.util.FileHandler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Service for managing book transactions (borrowing/returning)
 */
public class TransactionService {
    private Map<String, Transaction> transactions;
    private BookService bookService;
    private MemberService memberService;
    private FileHandler fileHandler;
    private static final String TRANSACTIONS_FILE = "transactions.dat";
    private static final int DEFAULT_LOAN_DAYS = 14;
    private static final double DAILY_FINE_RATE = 1.0; // $1 per day
    
    public TransactionService(BookService bookService, MemberService memberService, FileHandler fileHandler) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.fileHandler = fileHandler;
        loadTransactions();
    }
    
    /**
     * Loads transactions from file or initializes empty map if file doesn't exist
     */
    @SuppressWarnings("unchecked")
    private void loadTransactions() {
        Object data = fileHandler.readFromFile(TRANSACTIONS_FILE);
        if (data != null) {
            transactions = (Map<String, Transaction>) data;
        } else {
            transactions = new HashMap<>();
        }
    }
    
    /**
     * Saves transactions to file
     */
    private void saveTransactions() {
        fileHandler.writeToFile(TRANSACTIONS_FILE, transactions);
    }
    
    /**
     * Borrows a book
     * @param bookId ID of book to borrow
     * @param memberId ID of borrowing member
     * @return Transaction record
     * @throws BookNotFoundException if book doesn't exist or isn't available
     * @throws MemberNotFoundException if member doesn't exist or is inactive
     */
    public Transaction borrowBook(String bookId, String memberId) 
            throws BookNotFoundException, MemberNotFoundException {
        Book book = bookService.getBookById(bookId);
        Member member = memberService.getMemberById(memberId);
        
        // Check if book is available
        if (book.getStatus() != Book.BookStatus.AVAILABLE) {
            throw new BookNotFoundException("Book is not available for borrowing");
        }
        
        // Check if member is active
        if (!member.isActive()) {
            throw new MemberNotFoundException("Member is not active");
        }
        
        // Check if member has reached their borrowing limit
        List<Transaction> activeBorrowings = getMemberActiveTransactions(memberId);
        if (activeBorrowings.size() >= member.getMembershipType().getMaxBorrowItems()) {
            throw new MemberNotFoundException("Member has reached their borrowing limit");
        }
        
        // Create transaction
        Transaction transaction = new Transaction(bookId, memberId, DEFAULT_LOAN_DAYS);
        transactions.put(transaction.getId(), transaction);
        
        // Update book status
        bookService.updateBookStatus(bookId, Book.BookStatus.BORROWED);
        
        saveTransactions();
        return transaction;
    }
    
    /**
     * Returns a borrowed book
     * @param transactionId ID of transaction to complete
     * @return Updated transaction with fine if applicable
     * @throws BookNotFoundException if transaction doesn't exist
     */
    public Transaction returnBook(String transactionId) throws BookNotFoundException {
        Transaction transaction = transactions.get(transactionId);
        if (transaction == null) {
            throw new BookNotFoundException("Transaction not found");
        }
        
        // Check if book is already returned
        if (transaction.getStatus() == Transaction.TransactionStatus.RETURNED) {
            throw new BookNotFoundException("Book is already returned");
        }
        
        // Set return date and calculate fine if overdue
        LocalDate returnDate = LocalDate.now();
        transaction.setReturnDate(returnDate);
        
        // Calculate fine if returned after due date
        double fine = 0.0;
        if (returnDate.isAfter(transaction.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(transaction.getDueDate(), returnDate);
            fine = daysLate * DAILY_FINE_RATE;
            transaction.setFine(fine);
            transaction.setStatus(Transaction.TransactionStatus.OVERDUE);
        } else {
            transaction.setStatus(Transaction.TransactionStatus.RETURNED);
        }
        
        // Update book status
        bookService.updateBookStatus(transaction.getBookId(), Book.BookStatus.AVAILABLE);
        
        saveTransactions();
        return transaction;
    }
    
    /**
     * Renews a borrowed book (extends due date)
     * @param transactionId ID of transaction to renew
     * @param additionalDays Number of days to extend
     * @return Updated transaction
     * @throws BookNotFoundException if transaction doesn't exist
     */
    public Transaction renewBook(String transactionId, int additionalDays) throws BookNotFoundException {
        Transaction transaction = transactions.get(transactionId);
        if (transaction == null) {
            throw new BookNotFoundException("Transaction not found");
        }
        
        // Check if book is already returned
        if (transaction.getStatus() == Transaction.TransactionStatus.RETURNED) {
            throw new BookNotFoundException("Book is already returned");
        }
        
        // Extend due date
        LocalDate newDueDate = transaction.getDueDate().plusDays(additionalDays);
        transaction.setDueDate(newDueDate);
        
        // Reset overdue status if applicable
        if (transaction.getStatus() == Transaction.TransactionStatus.OVERDUE) {
            transaction.setStatus(Transaction.TransactionStatus.BORROWED);
        }
        
        saveTransactions();
        return transaction;
    }
    
    /**
     * Gets a transaction by ID
     * @param transactionId ID of transaction to get
     * @return Transaction if found, null otherwise
     */
    public Transaction getTransactionById(String transactionId) {
        return transactions.get(transactionId);
    }
    
    /**
     * Gets all transactions
     * @return List of all transactions
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }
    
    /**
     * Gets all active (not returned) transactions for a member
     * @param memberId ID of member
     * @return List of active transactions
     */
    public List<Transaction> getMemberActiveTransactions(String memberId) {
        return transactions.values().stream()
                          .filter(t -> t.getMemberId().equals(memberId))
                          .filter(t -> t.getStatus() == Transaction.TransactionStatus.BORROWED || 
                                       t.getStatus() == Transaction.TransactionStatus.OVERDUE)
                          .collect(Collectors.toList());
    }
    
    /**
     * Gets transaction history for a member
     * @param memberId ID of member
     * @return List of all transactions for the member
     */
    public List<Transaction> getMemberTransactionHistory(String memberId) {
        return transactions.values().stream()
                          .filter(t -> t.getMemberId().equals(memberId))
                          .collect(Collectors.toList());
    }
    
    /**
     * Gets transaction history for a book
     * @param bookId ID of book
     * @return List of all transactions for the book
     */
    public List<Transaction> getBookTransactionHistory(String bookId) {
        return transactions.values().stream()
                          .filter(t -> t.getBookId().equals(bookId))
                          .collect(Collectors.toList());
    }
    
    /**
     * Gets all overdue transactions
     * @return List of overdue transactions
     */
    public List<Transaction> getOverdueTransactions() {
        LocalDate today = LocalDate.now();
        return transactions.values().stream()
                          .filter(t -> t.getStatus() == Transaction.TransactionStatus.BORROWED || 
                                       t.getStatus() == Transaction.TransactionStatus.OVERDUE)
                          .filter(t -> t.getDueDate().isBefore(today))
                          .collect(Collectors.toList());
    }
    
    /**
     * Updates overdue status of all transactions
     * Sets transactions to OVERDUE status if past due date
     */
    public void updateOverdueStatus() {
        LocalDate today = LocalDate.now();
        boolean needSave = false;
        
        for (Transaction t : transactions.values()) {
            if ((t.getStatus() == Transaction.TransactionStatus.BORROWED) && 
                t.getDueDate().isBefore(today)) {
                t.setStatus(Transaction.TransactionStatus.OVERDUE);
                needSave = true;
            }
        }
        
        if (needSave) {
            saveTransactions();
        }
    }
}
