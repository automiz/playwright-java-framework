package com.automiz.tests;

import com.automiz.core.AutomizPlaywrightExtension;
import com.automiz.pages.AuthLoginPage;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@ExtendWith(AutomizPlaywrightExtension.class)
public class AuthJourneyTest {

    @Test
    @Tag("smoke")
    void testSuccessfulLogin(Page page) {
        AuthLoginPage loginPage = new AuthLoginPage(page);
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");

        assertThat(page).hasURL(Pattern.compile(".*inventory\\.html"));
        assertThat(page.locator(".title")).hasText("Products");
    }

    @Test
    @Tag("regression")
    void testLockedOutUser(Page page) {
        AuthLoginPage loginPage = new AuthLoginPage(page);
        loginPage.navigate();
        loginPage.login("locked_out_user", "secret_sauce");

        assertThat(loginPage.getErrorMessage()).isVisible();
        assertThat(loginPage.getErrorMessage()).containsText("Sorry, this user has been locked out");
    }
}