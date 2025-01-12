package Scenario1Login;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Scenario1Login {
    private static WebDriver webDriver;
    private static String baseUrl;

    // Before all code
    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.instagram.com/accounts/login/";
    }

    @Test
    @Order(1) // Test Case 2: Login with invalid email/username and password
    public void testWrongInformationLogin() throws InterruptedException {
        webDriver.get(baseUrl);

        // Locate and fill the login form
        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("invalid_email@example.com");
        passwordField.sendKeys("invalid_password");
        loginButton.click();

        // Wait for error message to appear
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Sorry, your password was incorrect')]") // Adjust XPath if needed
        ));

        // Assert error message is displayed
        assertTrue(errorElement.isDisplayed(), "Error message not displayed for invalid login.");
    }

    @Test
    @Order(2) // Test Case 3: Login with empty username and password fields
    public void testEmptyFieldsLogin() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(3000);

        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        Thread.sleep(2000);

        WebElement errorElement = webDriver.findElement(By.xpath("//div[contains(text(), 'Enter your username and password')]"));
        assertTrue(errorElement.isDisplayed(), "Error message not displayed for empty fields.");
    }

    @Test
    @Order(3) // Test Case 4: Login with an Invalid Username
    public void testInvalidUsername() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(3000);

        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("baba@dada.com");
        passwordField.sendKeys("11.ajdin.11Instagram");
        loginButton.click();

        Thread.sleep(5000);

        WebElement errorElement = webDriver.findElement(By.xpath("//p[contains(text(), 'The username you entered')]"));
        assertTrue(errorElement.isDisplayed(), "Error message not displayed for invalid username.");
    }

    @Test
    @Order(4) // Test Case 5: Login with an Invalid Password but valid Username
    public void testInvalidPassword() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(3000);

        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("daongernoodles@gmail.com");
        passwordField.sendKeys("123456789");
        loginButton.click();

        Thread.sleep(5000);

        WebElement errorElement = webDriver.findElement(By.xpath("//p[contains(text(), 'The password you entered')]"));
        assertTrue(errorElement.isDisplayed(), "Error message not displayed for invalid password.");
    }

    @Test
    @Order(5) // Test Case 1: Login with valid email/username and password
    public void testCorrectInformationLogin() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(3000);

        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("daongernoodles@gmail.com");
        passwordField.sendKeys("11.ajdin.11Instagram");
        loginButton.click();

        Thread.sleep(5000);

        boolean isLoggedIn = webDriver.getCurrentUrl().contains("instagram.com");
        assertTrue(isLoggedIn, "Login failed with valid credentials.");
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
