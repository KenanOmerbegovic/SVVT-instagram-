package Scenario4Reels;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Selenium4Reels {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        // Set up ChromeDriver properties
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize(); // Maximize the browser window
        baseUrl = "https://www.instagram.com/accounts/login/";
    }

    private void login() {
        webDriver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20)); // Increased timeout

        // Wait for the login form to be visible (check for a different reliable element like the login button)
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        assertTrue(loginButton.isDisplayed(), "Login button is not displayed. Page might not have loaded correctly.");

        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));

        usernameField.sendKeys("sacida7066@sfxeur.com");
        passwordField.sendKeys("11.ajdin.11Instagram");
        loginButton.click();

        // Wait for the Instagram home page to load
        wait.until(ExpectedConditions.urlContains("instagram.com"));
        assertTrue(webDriver.getCurrentUrl().contains("instagram.com"), "Login failed with valid credentials.");
    }

    @Test
    public void testOpenReels() {
        login();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Wait until the Reels link is clickable
        WebElement reelsIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/reels/')]")));
        reelsIcon.click();
        System.out.println("Reels section clicked.");

        // Wait for the Reels page to load
        wait.until(ExpectedConditions.urlContains("reels"));

        // Assert that the URL contains 'reels' to verify we are on the Reels page
        assertTrue(webDriver.getCurrentUrl().contains("/reels/"), "Failed to navigate to the Reels section.");

        System.out.println("Test passed: Reels section opened successfully.");
    }

    @Test
    public void testScrollReels() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Navigate to the Reels section
        WebElement reelsIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/reels/')]")));
        reelsIcon.click();
        System.out.println("Reels section clicked.");

        // Wait for the Reels page to load
        wait.until(ExpectedConditions.urlContains("reels"));
        assertTrue(webDriver.getCurrentUrl().contains("/reels/"), "Failed to navigate to the Reels section.");

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        int scrollAttempts = 0;
        int maxScrollAttempts = 10; // Adjust the max attempts as needed
        boolean elementFound = false;

        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

        while (scrollAttempts < maxScrollAttempts) {
            try {
                WebElement nextElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class, 'reel')]"))); // Adjust this XPath based on the actual element
                if (nextElement.isDisplayed()) {
                    elementFound = true;
                    System.out.println("Target element found.");
                    break;
                }
            } catch (TimeoutException e) {
                System.out.println("Element not found, scrolling... Attempt: " + (scrollAttempts + 1));
            }

            js.executeScript("window.scrollBy(0, 1000);");
            Thread.sleep(1000);

            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) {
                System.out.println("Reached the bottom of the page, no new content loaded.");
                break;
            }
            lastHeight = newHeight;
            scrollAttempts++;
        }

        assertTrue(elementFound, "Failed to scroll to new content after " + scrollAttempts + " attempts.");
        System.out.println("Test passed: Scrolled successfully and found the target element.");
    }





    private void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        // Scroll down by a certain amount (e.g., 500px down)
        js.executeScript("window.scrollBy(0, 500);");

        // You can also scroll to the bottom of the page
        // js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
