package com.example.filmapp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    private final AccountService accountService = new AccountService();

    // -------- isAnyFieldEmpty Tests --------

    @Test
    void isAnyFieldEmpty_returnsTrue_whenAnyFieldIsEmpty() {
        assertTrue(accountService.isAnyFieldEmpty("user", "", "pass"));
    }

    @Test
    void isAnyFieldEmpty_returnsTrue_whenAnyFieldIsNull() {
        assertTrue(accountService.isAnyFieldEmpty("user", null, "pass"));
    }

    @Test
    void isAnyFieldEmpty_returnsFalse_whenAllFieldsFilled() {
        assertFalse(accountService.isAnyFieldEmpty("user", "email@example.com", "pass"));
    }

    // -------- doPasswordsMatch Tests --------

    @Test
    void doPasswordsMatch_returnsTrue_whenPasswordsMatch() {
        assertTrue(accountService.doPasswordsMatch("password", "password"));
    }

    @Test
    void doPasswordsMatch_returnsFalse_whenPasswordsDiffer() {
        assertFalse(accountService.doPasswordsMatch("abc123", "def456"));
    }

    @Test
    void doPasswordsMatch_returnsFalse_whenPasswordsAreCloseButNotEqual() {
        assertFalse(accountService.doPasswordsMatch("password", "Password"));
    }

    // -------- isValidEmailFormat Tests --------

    @Test
    void isValidEmailFormat_returnsTrue_forValidEmail() {
        assertTrue(accountService.isValidEmailFormat("user@example.com"));
    }

    @Test
    void isValidEmailFormat_returnsFalse_whenMissingAtSymbol() {
        assertFalse(accountService.isValidEmailFormat("userexample.com"));
    }

    @Test
    void isValidEmailFormat_returnsFalse_whenMissingDomain() {
        assertFalse(accountService.isValidEmailFormat("user@"));
    }

    @Test
    void isValidEmailFormat_returnsFalse_whenEmailIsNull() {
        assertFalse(accountService.isValidEmailFormat(null));
    }

    // -------- Password Hashing & Verification Tests --------

    @Test
    void hashPassword_returnsNonNullAndDifferentFromPlaintext() {
        String hash = accountService.hashPassword("secure123");
        assertNotNull(hash);
        assertNotEquals("secure123", hash);
    }

    @Test
    void verifyPassword_returnsTrue_whenCorrectPasswordUsed() {
        String password = "secure123";
        String hash = accountService.hashPassword(password);
        assertTrue(accountService.verifyPassword(password, hash));
    }

    @Test
    void verifyPassword_returnsFalse_whenWrongPasswordUsed() {
        String hash = accountService.hashPassword("correct");
        assertFalse(accountService.verifyPassword("wrong", hash));
    }
}