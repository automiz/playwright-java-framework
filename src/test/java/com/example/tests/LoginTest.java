package com.example.tests;

import com.example.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    @Test
    void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");

        assertTrue(page.url().contains("inventory.html"), "URL should transition to inventory page");
        assertEquals("Products", page.locator(".title").textContent(), "Product page header should match");
    }

    @Test
    void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login("locked_out_user", "secret_sauce");

        assertTrue(loginPage.getErrorMessage().isVisible(), "Error message should be visible");
        assertTrue(loginPage.getErrorMessage().textContent().contains("Sorry, this user has been locked out"), 
                   "Error text should indicate user is locked out");
    }
}
