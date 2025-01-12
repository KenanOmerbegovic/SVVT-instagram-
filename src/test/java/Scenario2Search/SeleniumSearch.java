package Scenario2Search;

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

public class SeleniumSearch {
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

        usernameField.sendKeys("daongernoodles@gmail.com");
        passwordField.sendKeys("11.ajdin.11Instagram");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("instagram.com"));
        assertTrue(webDriver.getCurrentUrl().contains("instagram.com"), "Login failed with valid credentials.");
    }

    //Test One
    @Test
    public void testSearchExistingUser() {
        login();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Click on the search icon to activate the search bar
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("svg[aria-label='Search']")));
        searchIcon.click();
        System.out.println("Search icon clicked.");

        // Wait for the search bar to appear
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Search']")));
        System.out.println("Search bar is visible.");

        // Enter the username to search
        String usernameToSearch = "ajdinomeragic";
        searchBar.sendKeys(usernameToSearch);

        // Wait for the first search result to appear
        WebElement firstResult = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='/ajdinomeragic/']")));
        System.out.println("First search result located.");

        // Extract the username from the first result
        String resultUsername = firstResult.findElement(By.cssSelector("span[dir='auto']")).getText();

        // Click on the first result
        firstResult.click();

        // Wait for the profile page to load
        WebElement profileUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("header h2")));

        // Assert that the profile username matches the searched username
        assertTrue(resultUsername.equalsIgnoreCase(usernameToSearch),
                "The searched username was not found in the results. Expected: " + usernameToSearch + ", Found: " + resultUsername);
        assertTrue(profileUsername.getText().equalsIgnoreCase(usernameToSearch),
                "The profile page username does not match the searched username. Expected: " + usernameToSearch + ", Found: " + profileUsername.getText());

        System.out.println("Test passed: User exists.");
    }



    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
