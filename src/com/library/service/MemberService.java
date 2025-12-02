package com.library.service;

import com.library.model.Member;
import com.library.exception.MemberNotFoundException;
import com.library.util.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Service for managing library members
 */
public class MemberService {
    private Map<String, Member> members;
    private FileHandler fileHandler;
    private static final String MEMBERS_FILE = "members.dat";
    
    public MemberService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        loadMembers();
    }
    
    /**
     * Loads members from file or initializes empty map if file doesn't exist
     */
    @SuppressWarnings("unchecked")
    private void loadMembers() {
        Object data = fileHandler.readFromFile(MEMBERS_FILE);
        if (data != null) {
            members = (Map<String, Member>) data;
        } else {
            members = new HashMap<>();
        }
    }
    
    /**
     * Saves members to file
     */
    private void saveMembers() {
        fileHandler.writeToFile(MEMBERS_FILE, members);
    }
    
    /**
     * Registers a new member
     * @param member Member to register
     * @return Registered member with generated ID
     */
    public Member registerMember(Member member) {
        members.put(member.getId(), member);
        saveMembers();
        return member;
    }
    
    /**
     * Updates an existing member
     * @param member Member with updated information
     * @return Updated member
     * @throws MemberNotFoundException if member doesn't exist
     */
    public Member updateMember(Member member) throws MemberNotFoundException {
        if (!members.containsKey(member.getId())) {
            throw new MemberNotFoundException("Member with ID " + member.getId() + " not found");
        }
        members.put(member.getId(), member);
        saveMembers();
        return member;
    }
    
    /**
     * Removes a member
     * @param memberId ID of member to remove
     * @return true if member was removed, false otherwise
     */
    public boolean removeMember(String memberId) {
        if (members.containsKey(memberId)) {
            members.remove(memberId);
            saveMembers();
            return true;
        }
        return false;
    }
    
    /**
     * Gets a member by their ID
     * @param memberId ID of member to get
     * @return Member if found
     * @throws MemberNotFoundException if member doesn't exist
     */
    public Member getMemberById(String memberId) throws MemberNotFoundException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new MemberNotFoundException("Member with ID " + memberId + " not found");
        }
        return member;
    }
    
    /**
     * Gets all members
     * @return List of all members
     */
    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }
    
    /**
     * Searches for members by name
     * @param name Name to search for
     * @return List of matching members
     */
    public List<Member> searchByName(String name) {
        return members.values().stream()
                     .filter(member -> member.getName().toLowerCase().contains(name.toLowerCase()))
                     .collect(Collectors.toList());
    }
    
    /**
     * Searches for members by email
     * @param email Email to search for
     * @return List of matching members
     */
    public List<Member> searchByEmail(String email) {
        return members.values().stream()
                     .filter(member -> member.getEmail().toLowerCase().contains(email.toLowerCase()))
                     .collect(Collectors.toList());
    }
    
    /**
     * Gets all active members
     * @return List of active members
     */
    public List<Member> getActiveMembers() {
        return members.values().stream()
                     .filter(Member::isActive)
                     .collect(Collectors.toList());
    }
    
    /**
     * Gets members by membership type
     * @param membershipType Type to filter by
     * @return List of members with the given membership type
     */
    public List<Member> getMembersByType(Member.MembershipType membershipType) {
        return members.values().stream()
                     .filter(member -> member.getMembershipType() == membershipType)
                     .collect(Collectors.toList());
    }
    
    /**
     * Updates a member's active status
     * @param memberId ID of member to update
     * @param active New active status
     * @throws MemberNotFoundException if member doesn't exist
     */
    public void updateMemberActiveStatus(String memberId, boolean active) throws MemberNotFoundException {
        Member member = getMemberById(memberId);
        member.setActive(active);
        saveMembers();
    }
}
