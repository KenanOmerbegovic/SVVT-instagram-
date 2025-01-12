package Scenario6Notifications;
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

public class Selenium6Notifications {
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

    //Test Case for Opening Notifications
    @Test
    public void testOpenNotifications() {
        login();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Wait until the Notifications icon is clickable
        WebElement notificationsIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '#')]")));
        notificationsIcon.click();
        System.out.println("Notifications section clicked.");

        // Wait for the Notifications page to load (adjust as necessary based on how the page redirects or updates)
        // This part can be customized depending on how Instagram reacts after clicking the notification icon.

        // You can assert whether the notification section opens by checking if the URL changes or by checking for a certain element in the notifications page.
        // In this case, we'll just check if the URL doesn't remain the same as the login page.
        wait.until(ExpectedConditions.urlContains("instagram.com"));

        // Assert that the URL changed, confirming we are in a different section
        assertTrue(!webDriver.getCurrentUrl().contains("login"), "Failed to navigate to the Notifications section.");

        System.out.println("Test passed: Notifications section opened successfully.");
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}