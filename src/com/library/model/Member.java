package com.library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a library member
 */
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate memberSince;
    private MembershipType membershipType;
    private boolean active;
    
    public Member() {
        this.id = UUID.randomUUID().toString();
        this.memberSince = LocalDate.now();
        this.active = true;
    }
    
    public Member(String name, String email, String phone, String address, MembershipType membershipType) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.membershipType = membershipType;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public LocalDate getMemberSince() {
        return memberSince;
    }
    
    public MembershipType getMembershipType() {
        return membershipType;
    }
    
    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return String.format("Member [ID: %s, Name: %s, Email: %s, Type: %s, Status: %s]", 
                            id, name, email, membershipType, active ? "Active" : "Inactive");
    }
    
    // Member type enum
    public enum MembershipType {
        STANDARD(2),
        PREMIUM(5),
        STUDENT(3),
        SENIOR(2);
        
        private final int maxBorrowItems;
        
        MembershipType(int maxBorrowItems) {
            this.maxBorrowItems = maxBorrowItems;
        }
        
        public int getMaxBorrowItems() {
            return maxBorrowItems;
        }
    }
}
