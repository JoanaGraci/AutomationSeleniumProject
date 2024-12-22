package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingCartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;


    public ShoppingCartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
    }

    public void addAllItemsToCart() {
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

    public void navigateToCart() {
    	WebElement successMessage = waitUntilElementVisible(By.xpath("//div[@class='message-success success message']"));  // Update the XPath if needed
        if (successMessage != null) {
            System.out.println("Product successfully added to the cart.");

            // find and click the "Shopping Cart" link
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
    
    public void verifyCartSummary() {
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
                    By.xpath("//tr[contains(@class, 'grand totals')]//td[contains(@class, 'amount')]//span[contains(@class, 'price')]")));

                // Extract the text and clean it
                String orderTotalText = orderTotalElement.getText().replace("$", "").trim();  // Remove "$" and trim spaces
                double orderTotal = Double.parseDouble(orderTotalText);

                // Verify if the order total matches the subtotal
                if (Math.abs(expectedSubtotal - orderTotal) < 0.01) {
                    System.out.println("The Order Total matches the subtotal.");
                   
                      
                    
                    
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

    public void emptyCart_verify() {
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
    
    private WebElement waitUntilElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitUntilElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
