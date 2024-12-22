package Test_se_java;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class TestCase1Demo {

    private WebDriver driver;
    private WebDriverWait wait;
    
    
    
    @BeforeMethod
    public void setup() {
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\JARS-Sel\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
    }

    @Test
    public void testCreateAccount() {
        try {
            // Step 1: Navigate to Magento website
            driver.get("https://magento.softwaretestingboard.com/");

            // Step 2: Click on "Create an Account" link
            WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Create an Account")));
            createAccountLink.click();

            // Step 3: Verify the title of the page
            String expectedTitle = "Create New Customer Account";
            String actualTitle = driver.getTitle();
            Assert.assertEquals(actualTitle, expectedTitle, "Page title mismatch!");

            // Step 4: Fill in the form fields
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname"))).sendKeys("John");
            driver.findElement(By.id("lastname")).sendKeys("Doe");
            driver.findElement(By.id("email_address")).sendKeys("john.doe" + "test2828282" + "@example.com"); // Unique email
            driver.findElement(By.id("password")).sendKeys("Password123");
            driver.findElement(By.id("password-confirmation")).sendKeys("Password123");

            // Step 5: Click on "Create an Account" button
            WebElement createAccountButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Create an Account']")));
            createAccountButton.click();

            // Step 6: Verify successful account creation
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("main#maincontent > div > div:nth-of-type(2) > div > div > div")));
            Assert.assertTrue(successMessage.isDisplayed(), "Account creation failed or success message not displayed!");

            // Step 7: Sign out
            WebElement userProfileMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='panel header']//button[@type='button']")));
            userProfileMenu.click();

            WebElement signOutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='authorization-link']//a[contains(text(),'Sign Out')]")));
            signOutButton.click();
            
            WebDriverWait wait = new WebDriverWait(driver, 15);
            boolean isHomePageDisplayed = wait.until(ExpectedConditions.titleContains("Home Page"));


            // Verify successful sign-out
            //Assert.assertTrue(driver.getTitle().contains("Home Page"), "Sign-out unsuccessful!");
            Assert.assertTrue(isHomePageDisplayed, "Sign-out unsuccessful!");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test case failed due to exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Capture screenshot on failure
        if (!result.isSuccess()) {
            captureScreenshot(result.getName());
        }
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

    private void captureScreenshot(String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = "screenshots/" + testName + "_" + timestamp + ".png";
        try {
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("Screenshot saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}