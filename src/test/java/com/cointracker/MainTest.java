package com.cointracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for Main class.
 * Tests end-to-end flow including CSV file creation.
 */
public class MainTest {
    
    private static final String TEST_ADDRESS = "0xa39b189482f984388a34460636fea9eb181ad1a6";
    private String generatedCsvFile = null;
    
    @AfterEach
    void cleanup() {
        // Clean up generated CSV file after test
        if (generatedCsvFile != null) {
            try {
                Files.deleteIfExists(Paths.get(generatedCsvFile));
                System.out.println("🧹 Cleaned up test file: " + generatedCsvFile);
            } catch (Exception e) {
                System.err.println("Failed to cleanup: " + e.getMessage());
            }
        }
    }
    
    @Test
    void testMainExecutionAndCsvCreation() throws Exception {
        // Arrange
        String[] args = {TEST_ADDRESS};
        
        // Act - Call run() which returns the filename
        generatedCsvFile = Main.run(args);
        
        // Wait a bit for file to be fully written
        Thread.sleep(500);
        
        // Assert - Check if CSV file was created
        assertNotNull(generatedCsvFile, "Filename should be returned");
        Path path = Paths.get(generatedCsvFile);
        assertTrue(Files.exists(path), "CSV file should exist: " + generatedCsvFile);
        
        // Verify file has content
        long fileSize = Files.size(path);
        assertTrue(fileSize > 100, "CSV file should have content (size > 100 bytes)");
        
        // Verify CSV has header
        String firstLine = Files.readAllLines(path).get(0);
        assertTrue(firstLine.contains("Transaction Hash"), "CSV should have proper header");
        assertTrue(firstLine.contains("Date & Time"), "CSV should have Date & Time column");
        assertTrue(firstLine.contains("Transaction Type"), "CSV should have Transaction Type column");
        
        System.out.println("✅ CSV file created: " + generatedCsvFile);
        System.out.println("✅ File size: " + fileSize + " bytes");
        System.out.println("✅ Header validated");
    }
    
    @Test
    void testMainWithInvalidAddress() {
        // Arrange
        String[] args = {"invalid-address"};
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Main.run(args);
        }, "Should throw exception for invalid address");
        
        assertTrue(exception.getMessage().contains("Invalid Ethereum address"),
                "Exception message should mention invalid address");
        System.out.println("✅ Properly rejected invalid address");
    }
    
    @Test
    void testMainWithNoArguments() {
        // Arrange
        String[] args = {};
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Main.run(args);
        }, "Should throw exception when no arguments provided");
        
        assertTrue(exception.getMessage().contains("No Ethereum address"),
                "Exception message should mention missing address");
        System.out.println("✅ Properly rejected missing arguments");
    }
}
