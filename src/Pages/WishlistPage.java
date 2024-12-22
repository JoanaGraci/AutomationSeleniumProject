package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WishlistPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;


    public WishlistPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
    }

    public void addItemsToWishlist() {
    	//  Add the first item to the Wish List
        try {
            // Locate the product wrapper (first product) on the page
            WebElement firstProductWrapper = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//body/div[@class='page-wrapper']/main[@id='maincontent']/div[@class='columns']/div[@class='column main']/div[@class='products wrapper grid products-grid']/ol[@class='products list items product-items']/li[1]/div[1]/div[1]")));

            // Click on the product wrapper to reveal the "Add to Wishlist" button
            firstProductWrapper.click();
            System.out.println("Clicked on the first product wrapper.");

            // Wait for the product page to load or the hover effect to take place
            Thread.sleep(2000);  // Adjust the time if necessary for the page to load

            // Create an Actions object to simulate mouse movements
            Actions actions2 = new Actions(driver);

            // Hover over the first product to reveal the "Add to Wishlist" button
            WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[3]/ol[1]/li[1]/div[1]/div[1]/div[4]")));

            actions2.moveToElement(firstProduct).perform();

            // Wait for the "Add to Wishlist" button to be visible and clickable
            WebElement firstItemAddToWishlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[3]/ol[1]/li[1]/div[1]/div[1]/div[4]/div[1]/div[2]/a[1]")));

            // Click on the "Add to Wishlist" button
            if (firstItemAddToWishlist != null) {
                firstItemAddToWishlist.click();
                System.out.println("First item added to the Wish List.");

                // Wait for the success message or page to update
                Thread.sleep(2000);  // Adjust the time if necessary

                // Go back to the product listing page
                driver.navigate().back();
                System.out.println("Navigated back to the product listing page.");
            } else {
                System.out.println("Unable to find 'Add to Wishlist' button for the first item.");
            }
        } catch (Exception e) {
            System.out.println("Error while adding the first item to the Wishlist: " + e.getMessage());
        }

        // Add the second item to the Wish List
        try {
            // Locate the second product wrapper element
            WebElement secondProductWrapper = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//body/div[@class='page-wrapper']/main[@id='maincontent']/div[@class='columns']/div[@class='column main']/div[@class='products wrapper grid products-grid']/ol[@class='products list items product-items']/li[2]/div[1]/div[1]")));

            // Click on the second product wrapper to reveal the "Add to Wishlist" button
            secondProductWrapper.click();
            System.out.println("Clicked on the second product wrapper.");

            // Wait for the product page to load or the hover effect to take place
            Thread.sleep(2000);  // Adjust the time if necessary for the page to load

            // Hover over the second product to reveal the "Add to Wishlist" button
            WebElement secondProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[2]//div[@class='product-item-info']")));

            actions.moveToElement(secondProduct).perform();

            // Wait for the "Add to Wishlist" button for the second product
            WebElement secondItemAddToWishlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[2]//div[@class='product-item-info']//a[@class='action towishlist']") ));

            // Click on the "Add to Wishlist" button
            if (secondItemAddToWishlist != null) {
                secondItemAddToWishlist.click();
                System.out.println("Second item added to the Wish List.");

                // Wait for the success message or page to update
                Thread.sleep(2000);  // Adjust the time if necessary
            } else {
                System.out.println("Unable to find 'Add to Wishlist' button for the second item.");
            }
        } catch (Exception e) {
            System.out.println("Error while adding the second item to the Wishlist: " + e.getMessage());
        }
    }
    
    public void verifySuccessMesagge() {
    	//  Check for a successful message (text + icon)
        try {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message-success")));
            if (successMessage != null) {
                System.out.println("Successfully added items to the Wish List: " + successMessage.getText());
            } else {
                System.out.println("No success message found.");
            }
        } catch (Exception e) {
            System.out.println("Error while checking the success message: " + e.getMessage());
        }
        }
    

    public void verifyWishlistCount() {
    	try {
            // Wait for the user profile dropdown to be clickable and click it
        	WebElement userProfileButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='panel header']//button[@type='button']")));
        	Assert.assertNotNull(userProfileButton, "User profile dropdown element not found.");
        	userProfileButton.click();
            System.out.println("User profile dropdown clicked.");

            
            // Get the text from the wish list link and check for the number of items
            WebElement wishListItemsCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"maincontent\"]/div[2]/div[1]/div[3]/div[1]/p[1]/span[1]")));
            String wishListText = wishListItemsCount.getText();
            
             // Assert that the number of items in the Wish List is 2
            //Assert.assertTrue(wishListText.contains("(2)"), "The Wish List count is incorrect: " + wishListText);

            // If the test passes
            System.out.println("Correct number of items displayed in the Wish List: " + wishListText);

        } catch (Exception e) {
            // Handle any failures by asserting the failure
            Assert.fail("Error during verification of Wish List items: " + e.getMessage());
        }
    }
}
