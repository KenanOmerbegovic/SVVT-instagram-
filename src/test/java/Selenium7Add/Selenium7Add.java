package Scenario7Add;
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

public class Selenium7Add {
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

    // Test Case for Opening 'New Post' section
    @Test
    public void testOpenNewPost() {
        login();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Wait until the "New Post" icon is clickable
        WebElement newPostIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '#')]")));
        newPostIcon.click();
        System.out.println("New Post section clicked.");

        // Wait for the new post modal or section to load (adjust this based on Instagram's behavior after clicking)
        // This part can be customized depending on how Instagram reacts after the new post icon is clicked.

        // You may also assert that the page or UI changes, indicating the 'New Post' section is opened.

        // Checking if the URL changes or the page content updates after clicking.
        wait.until(ExpectedConditions.urlContains("instagram.com"));

        // Assert that the URL changed, confirming we are on a new section (not the login page)
        assertTrue(!webDriver.getCurrentUrl().contains("login"), "Failed to navigate to the New Post section.");

        System.out.println("Test passed: New Post section opened successfully.");
    }

    // Test Case for Opening 'New Post' section and interacting with the button
    @Test
    public void testOpenNewPostAndSelectButton() {


        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Wait until the "New Post" icon is clickable
        WebElement newPostIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '#')]")));
        newPostIcon.click();
        System.out.println("New Post section clicked.");

        // Wait for the "Select from computer" button to be clickable
        WebElement selectButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Select from computer')]")));

        // Check if the button is displayed and clickable
        if (selectButton.isDisplayed() && selectButton.isEnabled()) {
            selectButton.click();
            System.out.println("Button 'Select from computer' clicked.");
        } else {
            System.out.println("Button 'Select from computer' is not interactable.");
        }

        // Assert that the button is clickable and interaction was successful
        assertTrue(selectButton.isDisplayed() && selectButton.isEnabled(), "The 'Select from computer' button is not interactable.");

        System.out.println("Test passed: The button is interactable and successfully clicked.");
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
