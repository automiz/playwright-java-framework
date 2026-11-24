package com.automiz.core;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.extension.*;

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
        context = browser.newContext();
        page = context.newPage();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        if (context != null) {
            context.close();
        }
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        if (playwright != null) {
            playwright.close();
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