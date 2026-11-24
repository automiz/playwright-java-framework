# Playwright Java Automation Framework

## 1. Project Overview & Architecture

This project is an end-to-end (E2E) web test automation framework built using Playwright with Java, Maven, and JUnit 5. It uses the Page Object Model (POM) and a JUnit 5 extension model to keep tests modular, readable, and easy to scale across domains and user journeys.

### Key Architectural Benefits

- **Page Object Model (POM):** Encapsulates page elements and actions into dedicated page classes (`AuthLoginPage.java`), separating test logic from UI selectors.
- **JUnit 5 Extension Lifecycle (`AutomizPlaywrightExtension.java`):** Uses `@ExtendWith(AutomizPlaywrightExtension.class)` and `ParameterResolver` instead of inheritance to manage `Playwright`, `Browser`, `BrowserContext`, and `Page` lifecycles and inject `Page` into tests.
- **Industry-Standard Package Naming (`com.automiz.*`):** Uses a clean project-prefix package hierarchy (`com.automiz.config`, `com.automiz.pages`, `com.automiz.core`, `com.automiz.flows`, `com.automiz.tests`).
- **CI/CD Readiness:** Supports local Git and GitHub hosting, a local Jenkins declarative pipeline, and cloud-hosted GitHub Actions workflows.

## 2. Tech Stack & Tools Used

- **Playwright Java (v1.65.0):** Browser automation library with auto-waiting interactions and headless/headed support.
- **Java 21 & JUnit 5:** Modern Java runtime and testing framework with `@Test`, `@Tag`, extensions, and assertions.
- **Maven (v3.9.16):** Build and dependency management tool with `maven-compiler-plugin` and `maven-surefire-plugin`.
- **Eclipse IDE:** Local Java development environment.
- **Git & GitHub:** Source control and remote collaboration (`automiz/playwright-java-framework`).
- **Jenkins & GitHub Actions:** Local Windows Jenkins pipeline and GitHub Actions CI workflows (`ubuntu-24.04`).

## 3. Project Structure

```text
playwright-java-framework/
├── .github/
│   └── workflows/
│       └── playwright-ci.yml
├── .gitignore
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/automiz/
    │   ├── config/
    │   └── pages/
    │       └── AuthLoginPage.java
    └── test/
        ├── java/com/automiz/
        │   ├── core/
        │   │   └── AutomizPlaywrightExtension.java
        │   ├── flows/
        │   └── tests/
        │       └── AuthJourneyTest.java
        └── resources/
            └── testdata/