package Scenario9Threads;
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
import java.util.Set;

public class Selenium9Threads {
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

    //Test Case Two Opening Specific Element (Threads Link)
    @Test
    public void testOpenThreadsLink() {
        login();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        // Wait until the Threads link is clickable
        WebElement threadsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'x4k7w5x')]/div/a[contains(@href, 'https://www.threads.net')]")));

        // Open the link in a new tab using JavaScript
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.open(arguments[0], '_blank');", threadsLink.getAttribute("href"));
        System.out.println("Threads link opened in a new tab.");

        // Switch to the new tab
        Set<String> allWindows = webDriver.getWindowHandles();
        String originalWindow = webDriver.getWindowHandle();

        // Switch to the new tab
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                webDriver.switchTo().window(windowHandle);
                break;
            }
        }

        // Wait for the Threads page to load
        wait.until(ExpectedConditions.urlContains("threads.net"));

        // Assert that the URL contains 'threads' to verify we are on the Threads page
        assertTrue(webDriver.getCurrentUrl().contains("threads.net"), "Failed to navigate to the Threads page.");

        System.out.println("Test passed: Threads link opened successfully in a new tab.");
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
