# Playwright Java Automation Framework

## 1. Project Overview & Architecture

This project is an end-to-end (E2E) web test automation framework built using Playwright with Java, Maven, and JUnit 5[cite: 2, 5, 7]. It uses the Page Object Model (POM) and a JUnit 5 extension model to keep tests modular, readable, and easy to scale across domains and user journeys[cite: 2, 5, 7].

### Key Architectural Benefits

- **Page Object Model (POM):** Encapsulates page elements and actions into dedicated page classes (`AuthLoginPage.java`), separating test logic from UI selectors[cite: 2, 5, 7].
- **JUnit 5 Extension Lifecycle (`AutomizPlaywrightExtension.java`):** Uses `@ExtendWith(AutomizPlaywrightExtension.class)` and `ParameterResolver` instead of inheritance to manage `Playwright`, `Browser`, `BrowserContext`, and `Page` lifecycles and inject `Page` into tests.
- **Industry-Standard Package Naming (`com.automiz.*`):** Uses a clean project-prefix package hierarchy (`com.automiz.config`, `com.automiz.pages`, `com.automiz.core`, `com.automiz.flows`, `com.automiz.tests`)[cite: 2, 5, 7].
- **CI/CD Readiness:** Supports local Git and GitHub hosting, a local Jenkins declarative pipeline, and cloud-hosted GitHub Actions workflows.

## 2. Tech Stack & Tools Used

- **Playwright Java (v1.65.0):** Browser automation library with auto-waiting interactions and headless/headed support.
- **Java 21 & JUnit 5:** Modern Java runtime and testing framework with `@Test`, `@Tag`, extensions, and assertions[cite: 2].
- **Maven (v3.9.16):** Build and dependency management tool with `maven-compiler-plugin` and `maven-surefire-plugin`[cite: 4].
- **Eclipse IDE:** Local Java development environment[cite: 5, 6, 7].
- **Git & GitHub:** Source control and remote collaboration (`automiz/playwright-java-framework`).
- **Jenkins & GitHub Actions:** Local Windows Jenkins pipeline and GitHub Actions CI workflows (`ubuntu-24.04`).

## 3. Project Structure

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

## 4. Headed & Headless Execution (`AutomizPlaywrightExtension`)

`AutomizPlaywrightExtension` automatically resolves whether Chromium launches in **headed** or **headless** mode in `beforeAll`:

1. **System property:** `-Dheadless=true` or `-Dheadless=false`
2. **Environment variable:** `HEADLESS=true` or `HEADLESS=false`
3. **CI vs. Local auto-detection:** Defaults to **headless (`true`)** when running in CI (`CI=true` or `GITHUB_ACTIONS=true`), and **headed (`false`)** for local execution.

### Running Tests

- Local execution (headed by default): `mvn test`
- Force headless mode locally: `mvn test -Dheadless=true`
- Force headed mode: `mvn test -Dheadless=false`
- GitHub Actions / CI execution: `mvn -B test -Dheadless=true --no-transfer-progress`

## 5. Git Workflow & Pushing from Eclipse IDE

1. **Ensure `.gitignore` is clean UTF-8:** Keep `.gitignore` saved in **UTF-8** (without BOM) with `target/`, `.settings/`, `.classpath`, and `.project` so build artifacts and IDE metadata are ignored[cite: 3].
2. **Untrack previously committed build/IDE files (if needed):** Run `git rm -r --cached --ignore-unmatch target .settings` in the project directory.
3. **Refresh project & Git Staging in Eclipse:** Right-click `playwright-java-framework` in **Package Explorer** -> **Refresh** (or press `F5`), then open the **Git Staging** tab[cite: 1, 5, 6, 7]. Click the **circular refresh arrow** on the Git Staging toolbar if needed so **Unstaged Changes** and **Staged Changes** reflect your latest disk and index state[cite: 2, 4, 5, 6, 7].
4. **Stage changes:** In **Git Staging**, click **`+`** to stage selected files or **`++`** to stage all unstaged changes into **Staged Changes**[cite: 5, 6, 7].
5. **Set Author, Committer & Commit Message:** In **Author** and **Committer**, enter your real `Name <email@example.com>` (replacing any placeholder like `Vincent <your-email@example.com>`), and type a clear message in **Commit Message**[cite: 5, 6, 7].
6. **Commit and Push to GitHub:**
   - **Direct push:** Click **Commit and Push** in **Git Staging**[cite: 5].
   - **Commit first, then push:** Click **Commit** to record the commit locally, then click **Push HEAD...** at the bottom of **Git Staging** (or right-click the project -> **Team** -> **Push to Upstream** / **Push Branch 'main'...**), verify `main -> main`, and click **Push** (providing your GitHub username and Personal Access Token if prompted)[cite: 5, 6, 7].