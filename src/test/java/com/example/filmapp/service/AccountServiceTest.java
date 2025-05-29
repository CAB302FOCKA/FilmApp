package com.example.filmapp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    private final AccountService accountService = new AccountService();

    @Test
    void testIsAnyFieldEmpty_withEmptyField_returnsTrue() {
        assertTrue(accountService.isAnyFieldEmpty("user", "", "pass"));
    }

    @Test
    void testIsAnyFieldEmpty_withAllFilled_returnsFalse() {
        assertFalse(accountService.isAnyFieldEmpty("user", "email@example.com", "pass"));
    }

    @Test
    void testDoPasswordsMatch_same_returnsTrue() {
        assertTrue(accountService.doPasswordsMatch("abc123", "abc123"));
    }

    @Test
    void testDoPasswordsMatch_different_returnsFalse() {
        assertFalse(accountService.doPasswordsMatch("abc123", "def456"));
    }

    @Test
    void testIsValidEmailFormat_validEmail_returnsTrue() {
        assertTrue(accountService.isValidEmailFormat("user@example.com"));
    }

    @Test
    void testIsValidEmailFormat_invalidEmail_returnsFalse() {
        assertFalse(accountService.isValidEmailFormat("notanemail"));
    }

    @Test
    void testHashAndVerifyPassword_roundtripSuccess() {
        String password = "secure123";
        String hash = accountService.hashPassword(password);
        assertTrue(accountService.verifyPassword(password, hash));
    }

    @Test
    void testVerifyPassword_wrongPassword_returnsFalse() {
        String password = "correct";
        String hash = accountService.hashPassword(password);
        assertFalse(accountService.verifyPassword("wrong", hash));
    }
}