package Scenario5Messages;
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

public class Selenium5Messages {
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

        usernameField.sendKeys("veyitom421@suggets.com");
        passwordField.sendKeys("11.ajdin.11Instagram");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("instagram.com"));
        assertTrue(webDriver.getCurrentUrl().contains("instagram.com"), "Login failed with valid credentials.");
    }

    //Test Case for Opening Messenger
    @Test
    public void testOpenMessenger() {
        login();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Wait until the Messenger icon is clickable
        WebElement messengerIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/direct/inbox/')]")));

        // Re-locate the element if necessary before interacting with it
        messengerIcon = webDriver.findElement(By.xpath("//a[contains(@href, '/direct/inbox/')]"));

        messengerIcon.click();
        System.out.println("Messenger section clicked.");

        // Wait for the Messenger page to load (this can be adjusted based on how the page redirects)
        wait.until(ExpectedConditions.urlContains("/direct/inbox"));

        // Assert that the URL contains 'direct/inbox' to verify we are in the Messenger section
        assertTrue(webDriver.getCurrentUrl().contains("/direct/inbox/"), "Failed to navigate to the Messenger section.");

        System.out.println("Test passed: Messenger section opened successfully.");
    }


    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
