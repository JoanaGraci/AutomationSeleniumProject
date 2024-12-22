package Test_se_java;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class test3 {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @BeforeClass
    public void setUp() {
        // Set up the driver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\JARS-Sel\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
        actions = new Actions(driver);
    }

    @Test
    public void testScenario() {
        try {
            // Step 1: Navigate to the website
            driver.get("https://magento.softwaretestingboard.com/");
            driver.manage().window().maximize();
            System.out.println("Step 1: Navigated to the website successfully.");

            // Step 2: Sign In to the account
            signIn();
            System.out.println("Step 2: Signed in successfully.");

            // Step 3: Navigate to Women's category and select Jackets
            selectJackets();
            System.out.println("Step 3: Selected Jackets under Women category successfully.");

            // Step 4: Select a color from the "Color" dropdown
            selectColor();
            System.out.println("Step 4: Selected color from the dropdown successfully.");

            // Step 5: Verify that all products have the selected color bordered in red
            verifyColorBorderRed();
            System.out.println("Step 5: Verified that all products have the selected color bordered in red successfully.");

            // Step 6: Select the price range $50.00 - $59.99
            selectPriceRange();
            System.out.println("Step 6: Selected the price range $50.00 - $59.99 successfully.");

            // Step 7: Verify only two products are displayed after price filter
            verifyFilteredProducts();
            System.out.println("Step 7: Verified that only two products are displayed after price filter successfully.");

            // Step 8: Verify that the price matches the selected range
            verifyProductPriceRange();
            System.out.println("Step 8: Verified that the price of products matches the selected range successfully.");

            // Step 9: Sign out (optional)
            //signOut();
            //System.out.println("Step 9: Signed out successfully.");

        } catch (Exception e) {
            captureScreenshot("failure.png");
            Assert.fail("Test failed due to: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        // Close the browser after the test
        driver.quit();
    }

    private void signIn() {
        WebElement signInLink = waitUntilElementClickable(By.linkText("Sign In"));
        Assert.assertNotNull(signInLink, "Sign In link not found.");
        signInLink.click();

        // Enter login credentials (replace with valid ones)
        WebElement emailField = waitUntilElementVisible(By.id("email"));
        emailField.sendKeys("john22222234242424.doe@example.com");
        WebElement passwordField = waitUntilElementVisible(By.id("pass"));
        passwordField.sendKeys("Password123");

        WebElement signInButton = waitUntilElementClickable(By.id("send2"));
        signInButton.click();
    }

    private void selectJackets() {
        WebElement womenDropdown = waitUntilElementClickable(By.xpath("//span[text()='Women']"));
        womenDropdown.click();

        WebElement topsDropdown = waitUntilElementVisible(By.xpath("//a[contains(text(),'Tops')]"));
        actions.moveToElement(topsDropdown).perform();

        WebElement jacketsOption = waitUntilElementClickable(By.xpath("//a[contains(text(),'Jackets')]"));
        jacketsOption.click();
    }

    private void selectColor() {
        WebElement colorDropdown = waitUntilElementClickable(By.xpath("//*[@id='narrow-by-list']/div[4]/div[1]"));
        colorDropdown.click();

        WebElement colorOption = waitUntilElementClickable(By.xpath("//a[@aria-label='Red']//div[@option-label='Red']"));
        colorOption.click();
    }

    private void verifyColorBorderRed() {
        List<WebElement> productImages = driver.findElements(By.cssSelector(".product-item .product-image"));
        boolean isColorRedBordered = true;
        for (WebElement image : productImages) {
            String borderColor = image.getCssValue("border-color");
            if (!borderColor.contains("rgb(255, 0, 0)")) {
                isColorRedBordered = false;
                break;
            }
        }
        Assert.assertTrue(isColorRedBordered, "Not all products have the selected color bordered in red.");
    }

    private void selectPriceRange() {
        WebElement priceDropdown = waitUntilElementClickable(By.xpath("//*[@id='narrow-by-list']/div[10]/div[1]"));
        priceDropdown.click();

        WebElement priceRangeOption = waitUntilElementClickable(By.xpath("//div[10]//div[2]//ol[1]//li[1]//a[1]"));
        priceRangeOption.click();
    }

    private void verifyFilteredProducts() {
        List<WebElement> filteredProducts = driver.findElements(By.cssSelector(".product-item"));
        Assert.assertEquals(filteredProducts.size(), 2, "The number of products displayed does not match the price range.");
    }

    private void verifyProductPriceRange() {
        List<WebElement> filteredProducts = driver.findElements(By.cssSelector(".product-item"));
        boolean priceMatches = true;

        for (WebElement product : filteredProducts) {
            WebElement priceElement = product.findElement(By.cssSelector(".price"));
            String priceText = priceElement.getText().replace("$", "").trim();
            double price = Double.parseDouble(priceText);

            if (price < 50.0 || price > 59.99) {
                priceMatches = false;
                break;
            }
        }
        Assert.assertTrue(priceMatches, "Some products do not match the selected price range.");
    }

    private WebElement waitUntilElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitUntilElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void captureScreenshot(String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(src.toPath(), Paths.get(fileName));
            System.out.println("Screenshot captured: " + fileName);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
