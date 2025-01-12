package Scenario8User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

public class Selenium8User {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize(); // Maximize the browser window
        baseUrl = "https://www.instagram.com/accounts/login/";
    }

    private void login() {
        webDriver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("wonumity@polkaroad.net");
        passwordField.sendKeys("11.ajdin.11Ig");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("instagram.com"));
        assertTrue(webDriver.getCurrentUrl().contains("instagram.com"), "Login failed with valid credentials.");
    }

    // Test Case for Clicking on Profile Link
    @Test
    public void testClickProfileLink() {
        login();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Wait until the profile link is clickable
        WebElement profileLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/testitestforsvvt/']")));

        // Scroll into view if necessary
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", profileLink);

        // Click the profile link
        profileLink.click();
        System.out.println("Profile link clicked.");

        // Wait for the profile page to load
        wait.until(ExpectedConditions.urlContains("/testitestforsvvt/"));

        // Assert that the profile URL is correct
        assertTrue(webDriver.getCurrentUrl().contains("/testitestforsvvt/"), "Failed to navigate to the correct profile page.");

        System.out.println("Test passed: Navigated to the correct profile page.");
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
