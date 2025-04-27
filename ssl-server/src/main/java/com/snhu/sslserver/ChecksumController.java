package com.snhu.sslserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Annotates this class as a REST controller
@RestController
public class ChecksumController {

    // Maps the /hash route to this method, which returns a SHA-256 checksum of a static string
    @GetMapping("/hash")
    public String getChecksum() {
        // Static string to be hashed
        String data = "Hello World Check Sum!";
        // Call method to generate SHA-256 hash
        String checksum = getSHA256Checksum(data);
        // Return the original data and its corresponding hash
        return "Data: " + data + "\nChecksum (SHA-256): " + checksum;
    }

    // Helper method to generate SHA-256 hash from input string
    private String getSHA256Checksum(String input) {
        try {
            // Use Java's MessageDigest to apply SHA-256 hashing algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            
            // Convert byte array to hexadecimal format
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0'); // Pad with 0 if needed
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Return error message if SHA-256 is not supported (unlikely)
            return "Error generating checksum: " + e.getMessage();
        }
    }
}
