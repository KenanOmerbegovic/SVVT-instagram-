package Scenario10Settings;
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

public class Selenium10Settings {
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

    // Test Case: Clicking on Settings Icon
    @Test
    public void testClickSettingsIcon() {
        login();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Wait until the Settings icon is clickable
        WebElement settingsIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'x4k7w5x')]/div/a[contains(@href, '#')]")));

        // Click the Settings icon
        settingsIcon.click();
        System.out.println("Settings icon clicked successfully.");
    }

    // Test Case: Clicking on Settings Link and Choosing an Option
    @Test
    public void testClickSettingsLinkAndChooseOption() {
        testClickSettingsIcon();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Wait until the new Settings link is clickable
        WebElement settingsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/accounts/edit/'][contains(@class, 'x1i10hfl')]")));

        // Click the Settings link
        settingsLink.click();
        System.out.println("Settings link clicked successfully.");

        // Wait for the Settings page to load, and confirm the URL
        wait.until(ExpectedConditions.urlContains("accounts/edit"));
        assertTrue(webDriver.getCurrentUrl().contains("accounts/edit"), "Failed to open the Settings page.");

        // Choose another option like Logout (if available)
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/accounts/logout/']"))); // Adjust the XPath if needed
        logoutLink.click();
        System.out.println("Logout option clicked successfully.");

        // Output a notification that the element was clicked
        System.out.println("Test passed: The Settings and Logout options were clickable.");
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
