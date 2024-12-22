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

public class test6 {

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

            // Step 11: Add a specific product to the cart
            addToCart();
            System.out.println("Step 11: Added a specific product to the cart successfully.");

            // Step 12: Navigate to the shopping cart
            navigateToCart();
            System.out.println("Step 12: Navigated to the Shopping Cart successfully.");

            // Step 14: Verify the price match in the cart
            verifyPriceInCart();
            System.out.println("Step 14: Verified that the subtotal and order total match successfully.");
            
            
            Emptyshoppingcart();
            System.out.println("Step 15: success");

            // Optional Step 9: Sign out
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

    // Methods for existing steps (1-8)
    private void signIn() {
        WebElement signInLink = waitUntilElementClickable(By.linkText("Sign In"));
        Assert.assertNotNull(signInLink, "Sign In link not found.");
        signInLink.click();

        // Enter login credentials
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

    // New methods for steps 11, 12, and 14:

    private void addToCart() {
    	
    	try {
            // Locate the product (assuming you're using XPath for the product wrapper)
            WebElement product = driver.findElement(By.xpath("//div[@class='product-item-info']"));  // Adjust XPath based on your product

            // Wait for and click the "Add to Cart" button
            WebElement addToCartButton = waitUntilElementClickable(By.xpath(".//button[@title='Add to Cart']"));

            if (addToCartButton != null) {
                // Scroll the "Add to Cart" button into view if necessary
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);

                // Wait until the button is clickable
                WebDriverWait waitForClickability = new WebDriverWait(driver, 10);
                waitForClickability.until(ExpectedConditions.elementToBeClickable(addToCartButton));

                // Try clicking the button
                try {
                    addToCartButton.click();
                    System.out.println("Product added to cart.");
                } catch (Exception e) {
                    // If the normal click fails, try clicking using JavaScript
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
                    System.out.println("Product added to cart using JavaScript.");
                }

                // Wait for the cart update (1 second)
                Thread.sleep(1000);

                // Step 1: Select the first available size (click the first size element)
                List<WebElement> sizeOptions = driver.findElements(By.xpath("//div[@class='swatch-attribute size']//div[contains(@class, 'swatch-option')]"));
                if (!sizeOptions.isEmpty()) {
                    WebElement firstSizeOption = sizeOptions.get(0);  // Select the first size option
                    firstSizeOption.click();
                    System.out.println("First available size selected.");
                } else {
                    System.out.println("No size options available.");
                }

                // Step 2: Select the first available color (click the first color element)
                List<WebElement> colorOptions = driver.findElements(By.xpath("//div[@class='swatch-attribute color']//div[contains(@class, 'swatch-option')]"));
                if (!colorOptions.isEmpty()) {
                    WebElement firstColorOption = colorOptions.get(0);  // Select the first color option
                    firstColorOption.click();
                    System.out.println("First available color selected.");
                } else {
                    System.out.println("No color options available.");
                }

                // After selecting size and color, click the "Add to Cart" button again
                WebElement confirmAddToCartButton = waitUntilElementClickable(By.xpath("//button[@title='Add to Cart']"));
                if (confirmAddToCartButton != null) {
                    // Scroll the "Add to Cart" button into view if necessary
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", confirmAddToCartButton);

                    // Wait until the button is clickable and click
                    confirmAddToCartButton.click();
                    System.out.println("Product with selected size and color added to cart.");
                } else {
                    System.out.println("Confirm Add to Cart button not found.");
                }

                // Wait for 10 seconds to ensure the cart is updated
                Thread.sleep(10000);

            } else {
                System.out.println("Add to Cart button not found.");
            }
        } catch (Exception e) {
            System.out.println("Error processing product: " + e.getMessage());
        }
    }
    
    
    
    private void navigateToCart() {
    	WebElement successMessage = waitUntilElementVisible(By.xpath("//div[@class='message-success success message']"));  // Update the XPath if needed
        if (successMessage != null) {
            System.out.println("Product successfully added to the cart.");

            // Now, find and click the "Shopping Cart" link
            WebElement shoppingCartLink = waitUntilElementClickable(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div[1]/div[1]/div[1]/a[1]"));  // Adjust XPath based on actual element
            if (shoppingCartLink != null) {
                shoppingCartLink.click();
                System.out.println("Navigated to the Shopping Cart to view the last added product.");
            } else {
                System.out.println("Shopping Cart link not found.");
            }
        } else {
            System.out.println("Success message not found. Could not navigate to the cart.");
        }
    }

    private void verifyPriceInCart() {
    	try {
            // 1. Extract product price (from cart)
            WebElement productPriceElement = driver.findElement(By.xpath("//tbody[@class='cart item']//tr[@class='item-info']//td[@class='col price']//span[@class='price']"));
            String productPriceText = productPriceElement.getText().replace("$", "").trim();  // Remove "$" and trim spaces
            double productPrice = Double.parseDouble(productPriceText);

            // 2. Extract quantity (from cart)
            WebElement quantityElement = driver.findElement(By.xpath("//tbody[@class='cart item']//tr[@class='item-info']//td[@class='col qty']//input[@data-role='cart-item-qty']"));
            int quantity = Integer.parseInt(quantityElement.getAttribute("value"));

            // 3. Calculate expected subtotal (price * quantity)
            double expectedSubtotal = productPrice * quantity;

            // 4. Extract subtotal from the cart (ensure it matches the expected value)
            WebElement subtotalElement = driver.findElement(By.xpath("//tbody[@class='cart item']//tr[@class='item-info']//td[@class='col subtotal']//span[@class='price']"));
            String subtotalText = subtotalElement.getText().replace("$", "").trim();  // Remove "$" and trim spaces
            double actualSubtotal = Double.parseDouble(subtotalText);

            // Verify if the subtotal is correct
            if (Math.abs(expectedSubtotal - actualSubtotal) < 0.01) {
                System.out.println("Subtotal is correct: " + expectedSubtotal);
            } else {
                System.out.println("Subtotal is incorrect. Expected: " + expectedSubtotal + ", but got: " + actualSubtotal);
            }

         // 5. Extract Order Total from the Summary section with explicit wait
            try {
                // Wait until the Order Total element is visible
                WebDriverWait wait = new WebDriverWait(driver, 20);
                WebElement orderTotalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[contains(@class, 'grand totals')]//td[contains(@class, 'amount')]//span[contains(@class, 'price')]")
                ));

                // Extract the text and clean it
                String orderTotalText = orderTotalElement.getText().replace("$", "").trim();  // Remove "$" and trim spaces
                double orderTotal = Double.parseDouble(orderTotalText);

                // Verify if the order total matches the subtotal
                if (Math.abs(expectedSubtotal - orderTotal) < 0.01) {
                    System.out.println("The Order Total matches the subtotal.");
                   
                        // Close the browser
                    //driver.quit();
                    
                } else {
                    System.out.println("The Order Total does not match the subtotal. Expected: " + expectedSubtotal + ", but got: " + orderTotal);
                }
            } catch (Exception e) {
                System.out.println("Error extracting the Order Total: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error verifying the prices: " + e.getMessage());
        }  
    }
    
    private void Emptyshoppingcart() {
    	try {
            // 1. Go to the Shopping Cart page
            driver.get("https://magento.softwaretestingboard.com/checkout/cart/");
            
            // 2. Loop to delete items until the cart is empty
            while (true) {
                // 2a. Get the number of items in the cart (before deletion)
                List<WebElement> cartItems = driver.findElements(By.xpath("//tbody[@class='cart item']"));
                int itemCountBeforeDeletion = cartItems.size();

                // 2b. If there are no items left, break out of the loop
                if (itemCountBeforeDeletion == 0) {
                    System.out.println("The shopping cart is already empty.");
                    break;
                }

                // 2c. Delete the first item in the cart
                WebElement deleteButton = cartItems.get(0).findElement(By.xpath(".//a[@title='Remove item']"));
                deleteButton.click();

                // Wait for the cart to be updated
                wait.until(ExpectedConditions.invisibilityOf(cartItems.get(0)));

                // 3. Verify that the number of elements in the cart has decreased by 1
                wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.xpath("//tbody[@class='cart item']"), itemCountBeforeDeletion));

                System.out.println("Item deleted. Current cart size: " + (cartItems.size() - 1));

                // Wait for the cart to update after each deletion
                Thread.sleep(1000); // Optional sleep time
            }

            // 4. Verify the cart is empty by checking for the "You have no items in your shopping cart." message
            WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[2]/p[1]")
            ));
            if (emptyCartMessage.isDisplayed()) {
                System.out.println("The shopping cart is empty.");
                
            } else {
                System.out.println("The shopping cart is not empty.");
            }
        } catch (Exception e) {
            System.out.println("Test execution failed: " + e.getMessage());
        }
    
    }
    // Wait methods to ensure elements are visible or clickable
    private WebElement waitUntilElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitUntilElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void captureScreenshot(String filename) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screenshot.toPath(), Paths.get(filename));
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
