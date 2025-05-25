package com.EcoMentor_backend.EcoMentor.Shared.utils;

/**
 * Utility class for string operations.
 */
public class StringUtils {

    /**
     * Checks if a string is a palindrome (reads the same forwards and backwards).
     * Case-insensitive and ignores non-alphanumeric characters.
     *
     * @param input the string to check
     * @return true if the string is a palindrome, false otherwise
     */
    public static boolean isPalindrome(String input) {
        if (input == null) {
            return false;
        }
        
        // Remove non-alphanumeric characters and convert to lowercase
        String cleanInput = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        
        if (cleanInput.isEmpty()) {
            return false;
        }
        
        int left = 0;
        int right = cleanInput.length() - 1;
        
        while (left < right) {
            if (cleanInput.charAt(left) != cleanInput.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
}