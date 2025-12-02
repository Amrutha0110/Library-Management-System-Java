package com.library;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.service.BookService;
import com.library.service.MemberService;
import com.library.service.TransactionService;
import com.library.exception.BookNotFoundException;
import com.library.exception.MemberNotFoundException;
import com.library.util.FileHandler;
import com.library.util.DateUtil;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

/**
 * Main class for Library Management System
 */
public class LibraryManagementSystem {
    private BookService bookService;
    private MemberService memberService;
    private TransactionService transactionService;
    private Scanner scanner;
    
    public LibraryManagementSystem() {
        // Initialize file handler with data directory
        FileHandler fileHandler = new FileHandler("./library_data");
        
        // Initialize services
        this.bookService = new BookService(fileHandler);
        this.memberService = new MemberService(fileHandler);
        this.transactionService = new TransactionService(bookService, memberService, fileHandler);
        
        // Initialize scanner for user input
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Display main menu
     */
    public void displayMainMenu() {
        System.out.println("\n==== LIBRARY MANAGEMENT SYSTEM ====");
        System.out.println("1. Book Management");
        System.out.println("2. Member Management");
        System.out.println("3. Transaction Management");
        System.out.println("4. Reports");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Display book management menu
     */
    public void displayBookMenu() {
        System.out.println("\n==== BOOK MANAGEMENT ====");
        System.out.println("1. Add New Book");
        System.out.println("2. Update Book");
        System.out.println("3. Remove Book");
        System.out.println("4. Search Books");
        System.out.println("5. List All Books");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Display member management menu
     */
    public void displayMemberMenu() {
        System.out.println("\n==== MEMBER MANAGEMENT ====");
        System.out.println("1. Register New Member");
        System.out.println("2. Update Member");
        System.out.println("3. Remove Member");
        System.out.println("4. Search Members");
        System.out.println("5. List All Members");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Display transaction management menu
     */
    public void displayTransactionMenu() {
        System.out.println("\n==== TRANSACTION MANAGEMENT ====");
        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.println("3. Renew Book");
        System.out.println("4. List Member Borrows");
        System.out.println("5. List Overdue Books");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Display reports menu
     */
    public void displayReportsMenu() {
        System.out.println("\n==== REPORTS ====");
        System.out.println("1. Books by Category");
        System.out.println("2. Available Books");
        System.out.println("3. Members by Type");
        System.out.println("4. Overdue Books Report");
        System.out.println("5. Book Transaction History");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Add a new book
     */
    public void addBook() {
        try {
            System.out.println("\n==== ADD NEW BOOK ====");
            
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            
            System.out.print("Enter author: ");
            String author = scanner.nextLine();
            
            System.out.print("Enter ISBN: ");
            String isbn = scanner.nextLine();
            
            System.out.print("Enter publisher: ");
            String publisher = scanner.nextLine();
            
            System.out.print("Enter publication year: ");
            int publicationYear = Integer.parseInt(scanner.nextLine());
            
            System.out.println("\nBook Categories:");
            Book.BookCategory[] categories = Book.BookCategory.values();
            for (int i = 0; i < categories.length; i++) {
                System.out.printf("%d. %s%n", i + 1, categories[i]);
            }
            
            System.out.print("Select category (1-" + categories.length + "): ");
            int categoryChoice = Integer.parseInt(scanner.nextLine());
            Book.BookCategory category = categories[categoryChoice - 1];
            
            Book book = new Book(title, author, isbn, publisher, publicationYear, category);
            bookService.addBook(book);
            
            System.out.println("\nBook added successfully!");
            System.out.println("Book ID: " + book.getId());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Update an existing book
     */
    public void updateBook() {
        try {
            System.out.println("\n==== UPDATE BOOK ====");
            
            System.out.print("Enter Book ID: ");
            String id = scanner.nextLine();
            
            Book book = bookService.getBookById(id);
            
            System.out.println("\nCurrent Book Details:");
            System.out.println(book);
            
            System.out.println("\nEnter new details (leave blank to keep current):");
            
            System.out.print("Title [" + book.getTitle() + "]: ");
            String title = scanner.nextLine();
            if (!title.isEmpty()) {
                book.setTitle(title);
            }
            
            System.out.print("Author [" + book.getAuthor() + "]: ");
            String author = scanner.nextLine();
            if (!author.isEmpty()) {
                book.setAuthor(author);
            }
            
            System.out.print("ISBN [" + book.getIsbn() + "]: ");
            String isbn = scanner.nextLine();
            if (!isbn.isEmpty()) {
                book.setIsbn(isbn);
            }
            
            System.out.print("Publisher [" + book.getPublisher() + "]: ");
            String publisher = scanner.nextLine();
            if (!publisher.isEmpty()) {
                book.setPublisher(publisher);
            }
            
            System.out.print("Publication Year [" + book.getPublicationYear() + "]: ");
            String yearStr = scanner.nextLine();
            if (!yearStr.isEmpty()) {
                book.setPublicationYear(Integer.parseInt(yearStr));
            }
            
            System.out.println("\nBook Categories:");
            Book.BookCategory[] categories = Book.BookCategory.values();
            for (int i = 0; i < categories.length; i++) {
                System.out.printf("%d. %s%n", i + 1, categories[i]);
            }
            
            System.out.print("Select category [" + book.getCategory() + "] (1-" + categories.length + "): ");
            String categoryStr = scanner.nextLine();
            if (!categoryStr.isEmpty()) {
                int categoryChoice = Integer.parseInt(categoryStr);
                book.setCategory(categories[categoryChoice - 1]);
            }
            
            System.out.println("\nBook Status:");
            Book.BookStatus[] statuses = Book.BookStatus.values();
            for (int i = 0; i < statuses.length; i++) {
                System.out.printf("%d. %s%n", i + 1, statuses[i]);
            }
            
            System.out.print("Select status [" + book.getStatus() + "] (1-" + statuses.length + "): ");
            String statusStr = scanner.nextLine();
            if (!statusStr.isEmpty()) {
                int statusChoice = Integer.parseInt(statusStr);
                book.setStatus(statuses[statusChoice - 1]);
            }
            
            bookService.updateBook(book);
            System.out.println("\nBook updated successfully!");
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Remove a book
     */
    public void removeBook() {
        try {
            System.out.println("\n==== REMOVE BOOK ====");
            
            System.out.print("Enter Book ID: ");
            String id = scanner.nextLine();
            
            // Get book details for confirmation
            Book book = bookService.getBookById(id);
            System.out.println("\nBook to remove:");
            System.out.println(book);
            
            System.out.print("\nAre you sure you want to remove this book? (y/n): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("y")) {
                boolean removed = bookService.removeBook(id);
                if (removed) {
                    System.out.println("Book removed successfully!");
                } else {
                    System.out.println("Failed to remove book.");
                }
            } else {
                System.out.println("Operation cancelled.");
            }
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Search for books
     */
    public void searchBooks() {
        try {
            System.out.println("\n==== SEARCH BOOKS ====");
            System.out.println("1. Search by Title");
            System.out.println("2. Search by Author");
            System.out.println("3. Search by ISBN");
            System.out.print("Enter your choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            List<Book> results = null;
            
            switch (choice) {
                case 1:
                    System.out.print("\nEnter title to search: ");
                    String title = scanner.nextLine();
                    results = bookService.searchByTitle(title);
                    break;
                case 2:
                    System.out.print("\nEnter author to search: ");
                    String author = scanner.nextLine();
                    results = bookService.searchByAuthor(author);
                    break;
                case 3:
                    System.out.print("\nEnter ISBN to search: ");
                    String isbn = scanner.nextLine();
                    results = bookService.searchByISBN(isbn);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            
            if (results != null && !results.isEmpty()) {
                System.out.println("\nSearch Results:");
                displayBooks(results);
            } else {
                System.out.println("\nNo books found matching your criteria.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * List all books
     */
    public void listAllBooks() {
        List<Book> books = bookService.getAllBooks();
        
        System.out.println("\n==== ALL BOOKS ====");
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            displayBooks(books);
        }
    }
    
    /**
     * Display a list of books
     * @param books List of books to display
     */
    private void displayBooks(List<Book> books) {
        System.out.println("\n--------------------------------------------------------------------------------------------");
        System.out.printf("%-36s %-20s %-20s %-10s %-10s%n", "ID", "TITLE", "AUTHOR", "STATUS", "CATEGORY");
        System.out.println("--------------------------------------------------------------------------------------------");
        
        for (Book book : books) {
            System.out.printf("%-36s %-20s %-20s %-10s %-10s%n", 
                            book.getId(), 
                            limitString(book.getTitle(), 18), 
                            limitString(book.getAuthor(), 18),
                            book.getStatus(),
                            book.getCategory());
        }
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Total Books: " + books.size());
    }
    
    /**
     * Register a new member
     */
    public void registerMember() {
        try {
            System.out.println("\n==== REGISTER NEW MEMBER ====");
            
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            
            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            
            System.out.println("\nMembership Types:");
            Member.MembershipType[] types = Member.MembershipType.values();
            for (int i = 0; i < types.length; i++) {
                System.out.printf("%d. %s (Max books: %d)%n", i + 1, types[i], types[i].getMaxBorrowItems());
            }
            
            System.out.print("Select membership type (1-" + types.length + "): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());
            Member.MembershipType membershipType = types[typeChoice - 1];
            
            Member member = new Member(name, email, phone, address, membershipType);
            memberService.registerMember(member);
            
            System.out.println("\nMember registered successfully!");
            System.out.println("Member ID: " + member.getId());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Update an existing member
     */
    public void updateMember() {
        try {
            System.out.println("\n==== UPDATE MEMBER ====");
            
            System.out.print("Enter Member ID: ");
            String id = scanner.nextLine();
            
            Member member = memberService.getMemberById(id);
            
            System.out.println("\nCurrent Member Details:");
            System.out.println(member);
            
            System.out.println("\nEnter new details (leave blank to keep current):");
            
            System.out.print("Name [" + member.getName() + "]: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                member.setName(name);
            }
            
            System.out.print("Email [" + member.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                member.setEmail(email);
            }
            
            System.out.print("Phone [" + member.getPhone() + "]: ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                member.setPhone(phone);
            }
            
            System.out.print("Address [" + member.getAddress() + "]: ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                member.setAddress(address);
            }
            
            System.out.println("\nMembership Types:");
            Member.MembershipType[] types = Member.MembershipType.values();
            for (int i = 0; i < types.length; i++) {
                System.out.printf("%d. %s (Max books: %d)%n", i + 1, types[i], types[i].getMaxBorrowItems());
            }
            
            System.out.print("Select membership type [" + member.getMembershipType() + "] (1-" + types.length + "): ");
            String typeStr = scanner.nextLine();
            if (!typeStr.isEmpty()) {
                int typeChoice = Integer.parseInt(typeStr);
                member.setMembershipType(types[typeChoice - 1]);
            }
            
            System.out.print("Active Status [" + (member.isActive() ? "Active" : "Inactive") + "] (active/inactive): ");
            String statusStr = scanner.nextLine();
            if (!statusStr.isEmpty()) {
                member.setActive(statusStr.equalsIgnoreCase("active"));
            }
            
            memberService.updateMember(member);
            System.out.println("\nMember updated successfully!");
        } catch (MemberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Remove a member
     */
    public void removeMember() {
        try {
            System.out.println("\n==== REMOVE MEMBER ====");
            
            System.out.print("Enter Member ID: ");
            String id = scanner.nextLine();
            
            // Get member details for confirmation
            Member member = memberService.getMemberById(id);
            System.out.println("\nMember to remove:");
            System.out.println(member);
            
            // Check if member has active borrows
            List<Transaction> activeTransactions = transactionService.getMemberActiveTransactions(id);
            if (!activeTransactions.isEmpty()) {
                System.out.println("\nWarning: This member has " + activeTransactions.size() + " active borrows.");
                System.out.println("Please return all books before removing the member.");
                return;
            }
            
            System.out.print("\nAre you sure you want to remove this member? (y/n): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("y")) {
                boolean removed = memberService.removeMember(id);
                if (removed) {
                    System.out.println("Member removed successfully!");
                } else {
                    System.out.println("Failed to remove member.");
                }
            } else {
                System.out.println("Operation cancelled.");
            }
        } catch (MemberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Search for members
     */
    public void searchMembers() {
        try {
            System.out.println("\n==== SEARCH MEMBERS ====");
            System.out.println("1. Search by Name");
            System.out.println("2. Search by Email");
            System.out.print("Enter your choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            List<Member> results = null;
            
            switch (choice) {
                case 1:
                    System.out.print("\nEnter name to search: ");
                    String name = scanner.nextLine();
                    results = memberService.searchByName(name);
                    break;
                case 2:
                    System.out.print("\nEnter email to search: ");
                    String email = scanner.nextLine();
                    results = memberService.searchByEmail(email);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            
            if (results != null && !results.isEmpty()) {
                System.out.println("\nSearch Results:");
                displayMembers(results);
            } else {
                System.out.println("\nNo members found matching your criteria.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * List all members
     */
    public void listAllMembers() {
        List<Member> members = memberService.getAllMembers();
        
        System.out.println("\n==== ALL MEMBERS ====");
        if (members.isEmpty()) {
            System.out.println("No members in the library.");
        } else {
            displayMembers(members);
        }
    }
    
    /**
     * Display a list of members
     * @param members List of members to display
     */
    private void displayMembers(List<Member> members) {
        System.out.println("\n-------------------------------------------------------------------------------------------");
        System.out.printf("%-36s %-20s %-20s %-15s %-10s%n", "ID", "NAME", "EMAIL", "MEMBERSHIP", "STATUS");
        System.out.println("-------------------------------------------------------------------------------------------");
        
        for (Member member : members) {
            System.out.printf("%-36s %-20s %-20s %-15s %-10s%n", 
                             member.getId(), 
                             limitString(member.getName(), 18), 
                             limitString(member.getEmail(), 18),
                             member.getMembershipType(),
                             member.isActive() ? "Active" : "Inactive");
        }
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Total Members: " + members.size());
    }
    
    /**
     * Borrow a book
     */
    public void borrowBook() {
        try {
            System.out.println("\n==== BORROW BOOK ====");
            
            System.out.print("Enter Member ID: ");
            String memberId = scanner.nextLine();
            
            Member member = memberService.getMemberById(memberId);
            System.out.println("\nMember Details:");
            System.out.println(member);
            
            // Show active borrows for this member
            List<Transaction> activeTransactions = transactionService.getMemberActiveTransactions(memberId);
            System.out.println("\nCurrent Borrows: " + activeTransactions.size() + " / " + 
                              member.getMembershipType().getMaxBorrowItems());
            
            if (!activeTransactions.isEmpty()) {
                System.out.println("\nActive Borrows:");
                displayTransactions(activeTransactions);
            }
            
            System.out.print("\nEnter Book ID: ");
            String bookId = scanner.nextLine();
            
            Book book = bookService.getBookById(bookId);
            System.out.println("\nBook Details:");
            System.out.println(book);
            
            if (book.getStatus() != Book.BookStatus.AVAILABLE) {
                System.out.println("\nThis book is not available for borrowing.");
                return;
            }
            
            Transaction transaction = transactionService.borrowBook(bookId, memberId);
            
            System.out.println("\nBook borrowed successfully!");
            System.out.println("Transaction ID: " + transaction.getId());
            System.out.println("Due Date: " + DateUtil.formatDate(transaction.getDueDate()));
        } catch (BookNotFoundException | MemberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Return a book
     */
    public void returnBook() {
        try {
            System.out.println("\n==== RETURN BOOK ====");
            
            System.out.print("Enter Member ID: ");
            String memberId = scanner.nextLine();
            
            // Show active borrows for this member
            List<Transaction> activeTransactions = transactionService.getMemberActiveTransactions(memberId);
            
            if (activeTransactions.isEmpty()) {
                System.out.println("\nThis member has no active borrows.");
                return;
            }
            
            System.out.println("\nActive Borrows:");
            displayTransactions(activeTransactions);
            
            System.out.print("\nEnter Transaction ID to return: ");
            String transactionId = scanner.nextLine();
            
            Transaction transaction = transactionService.returnBook(transactionId);
            
            System.out.println("\nBook returned successfully!");
            if (transaction.getFine() > 0) {
                System.out.println("Fine charged: $" + transaction.getFine());
            }
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Renew a borrowed book
     */
    public void renewBook() {
        try {
            System.out.println("\n==== RENEW BOOK ====");
            
            System.out.print("Enter Member ID: ");
            String memberId = scanner.nextLine();
            
            // Show active borrows for this member
            List<Transaction> activeTransactions = transactionService.getMemberActiveTransactions(memberId);
            
            if (activeTransactions.isEmpty()) {
                System.out.println("\nThis member has no active borrows.");
                return;
            }
            
            System.out.println("\nActive Borrows:");
            displayTransactions(activeTransactions);
            
            System.out.print("\nEnter Transaction ID to renew: ");
            String transactionId = scanner.nextLine();
            
            System.out.print("Enter number of days to extend: ");
            int days = Integer.parseInt(scanner.nextLine());
            
            Transaction transaction = transactionService.renewBook(transactionId, days);
            
            System.out.println("\nBook renewed successfully!");
            System.out.println("New Due Date: " + DateUtil.formatDate(transaction.getDueDate()));
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * List member borrows
     */
    public void listMemberBorrows() {
        try {
            System.out.println("\n==== MEMBER BORROWS ====");
            
            System.out.print("Enter Member ID: ");
            String memberId = scanner.nextLine();
            
            Member member = memberService.getMemberById(memberId);
            System.out.println("\nMember Details:");
            System.out.println(member);
            
            System.out.println("\n1. Active Borrows");
            System.out.println("2. Borrow History");
            System.out.print("Enter your choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            List<Transaction> transactions = null;
            
            switch (choice) {
                case 1:
                    transactions = transactionService.getMemberActiveTransactions(memberId);
                    System.out.println("\nActive Borrows:");
                    break;
                case 2:
                    transactions = transactionService.getMemberTransactionHistory(memberId);
                    System.out.println("\nBorrow History:");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            
            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                displayTransactions(transactions);
            }
        } catch (MemberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * List overdue books
     */
    public void listOverdueBooks() {
        try {
            System.out.println("\n==== OVERDUE BOOKS ====");
            
            // Update overdue status
            transactionService.updateOverdueStatus();
            
            List<Transaction> overdueTransactions = transactionService.getOverdueTransactions();
            
            if (overdueTransactions.isEmpty()) {
                System.out.println("No overdue books.");
            } else {
                displayTransactions(overdueTransactions);
                
                // Calculate total fines
                double totalFines = 0;
                LocalDate today = LocalDate.now();
                
                for (Transaction t : overdueTransactions) {
                    long daysLate = DateUtil.daysBetween(t.getDueDate(), today);
                    double fine = daysLate * 1.0; // $1 per day
                    totalFines += fine;
                }
                
                System.out.println("\nTotal Potential Fines: $" + totalFines);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Display books by category
     */
    public void booksByCategory() {
        try {
            System.out.println("\n==== BOOKS BY CATEGORY ====");
            
            System.out.println("Categories:");
            Book.BookCategory[] categories = Book.BookCategory.values();
            for (int i = 0; i < categories.length; i++) {
                System.out.printf("%d. %s%n", i + 1, categories[i]);
            }
            
            System.out.print("Select category (1-" + categories.length + "): ");
            int categoryChoice = Integer.parseInt(scanner.nextLine());
            Book.BookCategory category = categories[categoryChoice - 1];
            
            List<Book> books = bookService.getBooksByCategory(category);
            
            System.out.println("\nBooks in category " + category + ":");
            if (books.isEmpty()) {
                System.out.println("No books found in this category.");
            } else {
                displayBooks(books);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Display available books
     */
    public void availableBooks() {
        System.out.println("\n==== AVAILABLE BOOKS ====");
        
        List<Book> books = bookService.getAvailableBooks();
        
        if (books.isEmpty()) {
            System.out.println("No available books.");
        } else {
            displayBooks(books);
        }
    }
    
    /**
     * Display members by type
     */
    public void membersByType() {
        try {
            System.out.println("\n==== MEMBERS BY TYPE ====");
            
            System.out.println("Membership Types:");
            Member.MembershipType[] types = Member.MembershipType.values();
            for (int i = 0; i < types.length; i++) {
                System.out.printf("%d. %s%n", i + 1, types[i]);
            }
            
            System.out.print("Select membership type (1-" + types.length + "): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());
            Member.MembershipType membershipType = types[typeChoice - 1];
            
            List<Member> members = memberService.getMembersByType(membershipType);
            
            System.out.println("\nMembers with " + membershipType + " membership:");
            if (members.isEmpty()) {
                System.out.println("No members found with this membership type.");
            } else {
                displayMembers(members);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Display book transaction history
     */
    public void bookTransactionHistory() {
        try {
            System.out.println("\n==== BOOK TRANSACTION HISTORY ====");
            
            System.out.print("Enter Book ID: ");
            String bookId = scanner.nextLine();
            
            Book book = bookService.getBookById(bookId);
            System.out.println("\nBook Details:");
            System.out.println(book);
            
            List<Transaction> transactions = transactionService.getBookTransactionHistory(bookId);
            
            System.out.println("\nTransaction History:");
            if (transactions.isEmpty()) {
                System.out.println("No transaction history for this book.");
            } else {
                displayTransactions(transactions);
            }
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Display a list of transactions
     * @param transactions List of transactions to display
     */
    private void displayTransactions(List<Transaction> transactions) {
        try {
            System.out.println("\n-------------------------------------------------------------------------------------------");
            System.out.printf("%-20s %-20s %-15s %-12s %-12s %-10s%n", 
                             "TRANSACTION ID", "BOOK TITLE", "MEMBER NAME", "BORROW DATE", "DUE DATE", "STATUS");
            System.out.println("-------------------------------------------------------------------------------------------");
            
            for (Transaction transaction : transactions) {
                Book book = bookService.getBookById(transaction.getBookId());
                Member member = memberService.getMemberById(transaction.getMemberId());
                
                System.out.printf("%-20s %-20s %-15s %-12s %-12s %-10s%n", 
                                limitString(transaction.getId(), 18), 
                                limitString(book.getTitle(), 18), 
                                limitString(member.getName(), 13),
                                DateUtil.formatDate(transaction.getBorrowDate()),
                                DateUtil.formatDate(transaction.getDueDate()),
                                transaction.getStatus());
            }
            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.println("Total Transactions: " + transactions.size());
        } catch (BookNotFoundException | MemberNotFoundException e) {
            System.out.println("Error retrieving details: " + e.getMessage());
        }
    }
    
    /**
     * Limits a string to a maximum length and adds ellipsis if needed
     * @param str String to limit
     * @param maxLength Maximum length
     * @return Limited string
     */
    private String limitString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
    
    /**
     * Populate sample data for testing
     */
    public void populateSampleData() {
        try {
            System.out.println("Adding sample books...");
            
            Book book1 = new Book("Java Programming", "John Smith", "978-0-13-376131-3", "Pearson", 2020, Book.BookCategory.TEXTBOOK);
            Book book2 = new Book("Introduction to Algorithms", "Thomas Cormen", "978-0-262-03384-8", "MIT Press", 2009, Book.BookCategory.TEXTBOOK);
            Book book3 = new Book("To Kill a Mockingbird", "Harper Lee", "978-0-446-31078-9", "Grand Central", 1960, Book.BookCategory.FICTION);
            Book book4 = new Book("1984", "George Orwell", "978-0-451-52493-5", "Signet Classics", 1949, Book.BookCategory.FICTION);
            Book book5 = new Book("The Elements of Style", "William Strunk", "978-0-205-30902-3", "Longman", 1999, Book.BookCategory.REFERENCE);
            
            bookService.addBook(book1);
            bookService.addBook(book2);
            bookService.addBook(book3);
            bookService.addBook(book4);
            bookService.addBook(book5);
            
            System.out.println("Adding sample members...");
            
            Member member1 = new Member("Alice Johnson", "alice@example.com", "555-1234", "123 Main St", Member.MembershipType.PREMIUM);
            Member member2 = new Member("Bob Smith", "bob@example.com", "555-5678", "456 Oak Ave", Member.MembershipType.STANDARD);
            Member member3 = new Member("Charlie Brown", "charlie@example.com", "555-9012", "789 Pine Rd", Member.MembershipType.STUDENT);
            
            memberService.registerMember(member1);
            memberService.registerMember(member2);
            memberService.registerMember(member3);
            
            System.out.println("Creating sample transactions...");
            
            // Borrow some books
            transactionService.borrowBook(book1.getId(), member1.getId());
            transactionService.borrowBook(book3.getId(), member2.getId());
            
            System.out.println("Sample data created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating sample data: " + e.getMessage());
        }
    }
    
    /**
     * Run the application
     */
    public void run() {
        boolean exit = false;
        
        // Ask if user wants to load sample data
        System.out.print("Would you like to load sample data? (y/n): ");
        String loadSample = scanner.nextLine();
        if (loadSample.equalsIgnoreCase("y")) {
            populateSampleData();
        }
        
        while (!exit) {
            displayMainMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1: // Book Management
                        boolean backToMain = false;
                        while (!backToMain) {
                            displayBookMenu();
                            try {
                                int bookChoice = Integer.parseInt(scanner.nextLine());
                                switch (bookChoice) {
                                    case 1: addBook(); break;
                                    case 2: updateBook(); break;
                                    case 3: removeBook(); break;
                                    case 4: searchBooks(); break;
                                    case 5: listAllBooks(); break;
                                    case 6: backToMain = true; break;
                                    default: System.out.println("Invalid choice. Please try again.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }
                        break;
                        
                    case 2: // Member Management
                        backToMain = false;
                        while (!backToMain) {
                            displayMemberMenu();
                            try {
                                int memberChoice = Integer.parseInt(scanner.nextLine());
                                switch (memberChoice) {
                                    case 1: registerMember(); break;
                                    case 2: updateMember(); break;
                                    case 3: removeMember(); break;
                                    case 4: searchMembers(); break;
                                    case 5: listAllMembers(); break;
                                    case 6: backToMain = true; break;
                                    default: System.out.println("Invalid choice. Please try again.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }
                        break;
                        
                    case 3: // Transaction Management
                        backToMain = false;
                        while (!backToMain) {
                            displayTransactionMenu();
                            try {
                                int transChoice = Integer.parseInt(scanner.nextLine());
                                switch (transChoice) {
                                    case 1: borrowBook(); break;
                                    case 2: returnBook(); break;
                                    case 3: renewBook(); break;
                                    case 4: listMemberBorrows(); break;
                                    case 5: listOverdueBooks(); break;
                                    case 6: backToMain = true; break;
                                    default: System.out.println("Invalid choice. Please try again.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }
                        break;
                        
                    case 4: // Reports
                        backToMain = false;
                        while (!backToMain) {
                            displayReportsMenu();
                            try {
                                int reportChoice = Integer.parseInt(scanner.nextLine());
                                switch (reportChoice) {
                                    case 1: booksByCategory(); break;
                                    case 2: availableBooks(); break;
                                    case 3: membersByType(); break;
                                    case 4: listOverdueBooks(); break;
                                    case 5: bookTransactionHistory(); break;
                                    case 6: backToMain = true; break;
                                    default: System.out.println("Invalid choice. Please try again.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }
                        break;
                        
                    case 5: // Exit
                        System.out.println("Thank you for using the Library Management System!");
                        exit = true;
                        break;
                        
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Main method
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        LibraryManagementSystem lms = new LibraryManagementSystem();
        lms.run();
    }
}
