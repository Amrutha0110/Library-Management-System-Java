package com.library.exception;

/**
 * Exception thrown when a member is not found
 */
public class MemberNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public MemberNotFoundException(String message) {
        super(message);
    }
}
