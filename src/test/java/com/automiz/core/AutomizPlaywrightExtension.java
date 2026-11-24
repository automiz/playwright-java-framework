package com.automiz.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

public class AutomizPlaywrightExtension implements
        BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback, ParameterResolver {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        boolean headless = Boolean.parseBoolean(
            System.getProperty("headless", System.getenv().getOrDefault("HEADLESS", "true"))
        );
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(headless)
        );
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        if (browser == null) {
            throw new IllegalStateException("Browser is not initialized. Ensure AutomizPlaywrightExtension.beforeAll ran.");
        }
        context = browser.newContext();
        page = context.newPage();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        if (page != null) {
            page.close();
            page = null;
        }
        if (context != null) {
            context.close();
            context = null;
        }
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();
        return type == Page.class || type == BrowserContext.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Class<?> type = parameterContext.getParameter().getType();
        if (type == Page.class) {
            return page;
        }
        if (type == BrowserContext.class) {
            return context;
        }
        return null;
    }
}