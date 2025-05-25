package com.EcoMentor_backend.EcoMentor.Shared.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    @Test
    public void testIsPalindrome_WithValidPalindromes_ReturnsTrue() {
        // Simple palindromes
        assertTrue(StringUtils.isPalindrome("racecar"));
        assertTrue(StringUtils.isPalindrome("level"));
        assertTrue(StringUtils.isPalindrome("deified"));
        
        // Palindromes with mixed case
        assertTrue(StringUtils.isPalindrome("Racecar"));
        assertTrue(StringUtils.isPalindrome("Level"));
        
        // Palindromes with spaces and punctuation
        assertTrue(StringUtils.isPalindrome("A man, a plan, a canal: Panama"));
        assertTrue(StringUtils.isPalindrome("No 'x' in Nixon"));
    }
    
    @Test
    public void testIsPalindrome_WithNonPalindromes_ReturnsFalse() {
        assertFalse(StringUtils.isPalindrome("hello"));
        assertFalse(StringUtils.isPalindrome("world"));
        assertFalse(StringUtils.isPalindrome("Java"));
        assertFalse(StringUtils.isPalindrome("Spring Boot"));
    }
    
    @Test
    public void testIsPalindrome_WithEdgeCases_HandlesCorrectly() {
        // Null and empty strings
        assertFalse(StringUtils.isPalindrome(null));
        assertFalse(StringUtils.isPalindrome(""));
        assertFalse(StringUtils.isPalindrome("   "));
        
        // Single character (technically a palindrome)
        assertTrue(StringUtils.isPalindrome("a"));
        
        // Two identical characters
        assertTrue(StringUtils.isPalindrome("aa"));
        
        // String with only non-alphanumeric characters
        assertFalse(StringUtils.isPalindrome("!@#$%"));
    }
}