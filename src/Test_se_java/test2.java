package Test_se_java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;

public class test2 {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\JARS-Sel\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Capture screenshot on failure
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    private void takeScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        try {
            FileUtils.copyFile(source, new File("./Screenshots/" + testName + "_" + timestamp + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoginAndSignOut() {
        // Step 1: Navigate to the website
        driver.get("https://magento.softwaretestingboard.com/");

        // Step 2: Click on the "Sign In" link
        WebElement signInLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signInLink.click();

        // Step 3: Login with credentials
        String email = "john22222234242424.doe@example.com";
        String password = "Password123";

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pass")));
        passwordField.sendKeys(password);

        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("send2")));
        signInButton.click();

        // Step 4: Check if username is displayed in the top-right corner
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("html > body > div:nth-of-type(2) > header > div > div > ul > li > span")));
        Assert.assertTrue(username.isDisplayed(), "Login failed: Username not displayed.");

        // Step 5: Click on User profile and Sign Out
        WebElement userProfileButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='panel header']//button[@type='button']")));
        userProfileButton.click();

        WebElement signOutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='authorization-link']//a[contains(text(),'Sign Out')]")));
        signOutButton.click();
        
        WebDriverWait wait = new WebDriverWait(driver, 15);
        boolean isHomePageDisplayed = wait.until(ExpectedConditions.titleContains("Home Page"));
        
        // Step 6: Verify successful sign-out
//        WebElement homePageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//title[contains(text(),'Home Page')]")));
//        Assert.assertTrue(homePageTitle.getText().contains("Home Page"), "Sign-out failed: Home Page not loaded.");
        Assert.assertTrue(isHomePageDisplayed, "Sign-out unsuccessful!");
        
    }
}
