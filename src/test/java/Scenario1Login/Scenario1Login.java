package Scenario1Login;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Scenario1Login {
    private static WebDriver webDriver;
    private static String baseUrl;

    //Before all code
    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.instagram.com/accounts/login/";
    }

    //1. User Authentication - Login Functionality 1-5
    @Test //Test Case 1: Login with valid email/username and password.
    public void testCorrectInformationLogin() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(3000);

        // Locate and fill the login form
        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("daongernoodles@gmail.com");
        passwordField.sendKeys("11.ajdin.11Instagram");
        loginButton.click();

        Thread.sleep(5000);

        // Verify successful login by checking for the presence of a unique element on the homepage
        boolean isLoggedIn = webDriver.getCurrentUrl().contains("instagram.com");
        assertTrue(isLoggedIn, "Login failed with valid credentials.");
    }


    @Test //Test Case 2: Login with invalid email/username and password.
    public void testWrongInformationLogin() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(3000);

        // Locate and fill the login form
        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("invalid_email@example.com");
        passwordField.sendKeys("invalid_password");
        loginButton.click();

        Thread.sleep(5000); // Allow time for login to process

        // Verify failure by checking for an error message or still being on the login page
        WebElement errorElement = webDriver.findElement(By.id("slfErrorAlert")); // Change selector based on error element
        assertTrue(errorElement.isDisplayed(), "Error message not displayed for invalid login.");
    }

    @Test //Test Case 3: Login with empty username and password fields.
    public void testEmptyFieldsLogin() throws InterruptedException {
        // Step 1: Open Instagram login page
        webDriver.get(baseUrl);
        Thread.sleep(3000); // Allow page to load fully

        // Step 2: Attempt to log in without entering credentials
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        Thread.sleep(2000); // Allow time for validation to process

        // Step 3: Verify failure - Check for an error message or validation feedback
        WebElement errorElement = webDriver.findElement(By.xpath("//div[contains(text(), 'Enter your username and password')]"));
        assertTrue(errorElement.isDisplayed(), "Error message not displayed for empty fields.");
    }


    @Test //Test Case 4: Login with an Invalid Username.
    public void testInvalidUsername() throws InterruptedException {
        // Step 1: Open Instagram login page
        webDriver.get(baseUrl);
        Thread.sleep(3000); // Allow page to load fully

        // Step 2: Enter invalid username and valid password
        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("baba@dada.com");
        passwordField.sendKeys("11.ajdin.11Instagram");
        loginButton.click();

        Thread.sleep(5000); // Allow time for the login process

        // Step 3: Verify failure - Check for error message or validation feedback
        WebElement errorElement = webDriver.findElement(By.xpath("//p[contains(text(), 'The username you entered')]")); // Adjust selector if needed
        assertTrue(errorElement.isDisplayed(), "Error message not displayed for invalid username.");
    }

    @Test //Test Case 5: Login with an Invalid Password but valid Username.
    public void testInvalidPassword() throws InterruptedException {
        // Step 1: Open Instagram login page
        webDriver.get(baseUrl);
        Thread.sleep(3000); // Allow page to load fully

        // Step 2: Enter valid username and invalid password
        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("daongernoodles@gmail.com");
        passwordField.sendKeys("123456789");
        loginButton.click();

        Thread.sleep(5000); // Allow time for the login process

        // Step 3: Verify failure - Check for error message or validation feedback
        WebElement errorElement = webDriver.findElement(By.xpath("//p[contains(text(), 'The password you entered')]")); // Adjust selector if needed
        assertTrue(errorElement.isDisplayed(), "Error message not displayed for invalid password.");
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}