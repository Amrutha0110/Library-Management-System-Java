package com.library.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * Utility class for handling file operations
 */
public class FileHandler {
    private String dataDirectory;
    
    public FileHandler(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        createDirectoryIfNotExists();
    }
    
    /**
     * Creates data directory if it doesn't exist
     */
    private void createDirectoryIfNotExists() {
        File directory = new File(dataDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    /**
     * Writes object to file
     * @param filename Filename to write to
     * @param object Object to write
     * @return true if successful, false otherwise
     */
    public boolean writeToFile(String filename, Object object) {
        String filePath = dataDirectory + File.separator + filename;
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Reads object from file
     * @param filename Filename to read from
     * @return Object read from file, or null if file doesn't exist or error occurs
     */
    public Object readFromFile(String filename) {
        String filePath = dataDirectory + File.separator + filename;
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Checks if a file exists
     * @param filename Filename to check
     * @return true if file exists, false otherwise
     */
    public boolean fileExists(String filename) {
        String filePath = dataDirectory + File.separator + filename;
        File file = new File(filePath);
        return file.exists();
    }
    
    /**
     * Deletes a file
     * @param filename Filename to delete
     * @return true if file was deleted, false otherwise
     */
    public boolean deleteFile(String filename) {
        String filePath = dataDirectory + File.separator + filename;
        File file = new File(filePath);
        return file.delete();
    }
}
