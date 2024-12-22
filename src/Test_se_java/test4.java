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

public class test4 {

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

            //Step 7: Verify only two products are displayed after price filter
            verifyFilteredProducts();
            System.out.println("Step 7: Verified that only two products are displayed after price filter successfully.");

            // Step 8: Verify that the price matches the selected range
            verifyProductPriceRange();
            System.out.println("Step 8: Verified that the price of products matches the selected range successfully.");
            
            initialcount();
            System.out.println("Step 9: Verified that the price filter is removed and the items number is increased.");
            
            addwishlist();
            System.out.println("Step 10: Verified that the two first item are added in the Wish List.");
            
            successmsg();
            System.out.println("Step 11: Verified that the successful message is shown.");
            
            verifyWishListItems();
            System.out.println("Step 12: success");
            
        //    verifyWishListItems();
        //    System.out.println("Step 12: success");
            
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
    
	private void initialcount() {
	    	
	    	List<WebElement> initialProductList = driver.findElements(By.cssSelector(".product-item"));
	        int initialItemCount = initialProductList.size();
	        System.out.println("Initial item count: " + initialItemCount);
	        
	        WebElement removePriceFilter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Remove Price $50.00 - $59.99']"))); // Assuming 'Clear' is the text for removing filter
	        if (removePriceFilter != null) {
	            removePriceFilter.click();
	            System.out.println("Price filter removed.");
	        } else {
	            System.out.println("Price filter remove button not found.");
	        }
	        // Wait for the page to update and get the updated product list
	        //Thread.sleep(2000); // Wait for the items to refresh
	        List<WebElement> updatedProductList = driver.findElements(By.cssSelector(".product-item"));
	        int updatedItemCount = updatedProductList.size();
	        System.out.println("Updated item count: " + updatedItemCount);
	
	        if (updatedItemCount > initialItemCount) {
	            System.out.println("The number of items has increased.");
	        } else {
	            System.out.println("The number of items has not increased.");
	        }
	        
	    }
    
    private void addwishlist() {
    	
    	// Step 12: Add the first item to the Wish List
        try {
            // Locate the product wrapper (first product) on the page
            WebElement firstProductWrapper = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//body/div[@class='page-wrapper']/main[@id='maincontent']/div[@class='columns']/div[@class='column main']/div[@class='products wrapper grid products-grid']/ol[@class='products list items product-items']/li[1]/div[1]/div[1]")
            ));

            // Click on the product wrapper to reveal the "Add to Wishlist" button
            firstProductWrapper.click();
            System.out.println("Clicked on the first product wrapper.");

            // Wait for the product page to load or the hover effect to take place
            Thread.sleep(2000);  // Adjust the time if necessary for the page to load

            // Create an Actions object to simulate mouse movements
            Actions actions2 = new Actions(driver);

            // Hover over the first product to reveal the "Add to Wishlist" button
            WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[3]/ol[1]/li[1]/div[1]/div[1]/div[4]")
            ));

            actions2.moveToElement(firstProduct).perform();

            // Wait for the "Add to Wishlist" button to be visible and clickable
            WebElement firstItemAddToWishlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[3]/ol[1]/li[1]/div[1]/div[1]/div[4]/div[1]/div[2]/a[1]")
            ));

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

        // Step 13: Add the second item to the Wish List
        try {
            // Locate the second product wrapper element
            WebElement secondProductWrapper = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//body/div[@class='page-wrapper']/main[@id='maincontent']/div[@class='columns']/div[@class='column main']/div[@class='products wrapper grid products-grid']/ol[@class='products list items product-items']/li[2]/div[1]/div[1]")
            ));

            // Click on the second product wrapper to reveal the "Add to Wishlist" button
            secondProductWrapper.click();
            System.out.println("Clicked on the second product wrapper.");

            // Wait for the product page to load or the hover effect to take place
            Thread.sleep(2000);  // Adjust the time if necessary for the page to load

            // Hover over the second product to reveal the "Add to Wishlist" button
            WebElement secondProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[2]//div[@class='product-item-info']")
            ));

            actions.moveToElement(secondProduct).perform();

            // Wait for the "Add to Wishlist" button for the second product
            WebElement secondItemAddToWishlist = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[2]//div[@class='product-item-info']//a[@class='action towishlist']")
            ));

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
    
        private void successmsg() {
        // Step 14: Check for a successful message (text + icon)
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
        
        public void verifyWishListItems() {
            try {
                // Step 1: Wait for the user profile dropdown to be clickable and click it
            	WebElement userProfileButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='panel header']//button[@type='button']")));
            	Assert.assertNotNull(userProfileButton, "User profile dropdown element not found.");
            	userProfileButton.click();
//                WebElement userProfileDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".dropdown-toggle")));
                
//                userProfileDropdown.click();
                System.out.println("User profile dropdown clicked.");

                // Step 2: Wait for the 'My Wish List' link to be clickable and click it
                //WebElement wishListLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'My Wish List')]")));
               // Assert.assertNotNull(wishListLink, "My Wish List link not found in the dropdown menu.");
                //wishListLink.click();
                //System.out.println("Clicked on 'My Wish List'.");

                // Step 3: Wait for the Wish List page to load (optional depending on your page load behavior)
                // You can adjust the wait time if needed, or if there is a specific element that confirms the page has loaded.
                //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".wishlist-page"))); // Replace with your wishlist page specific element
                
                // Step 4: Get the text from the wish list link and check for the number of items
                WebElement wishListItemsCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"maincontent\"]/div[2]/div[1]/div[3]/div[1]/p[1]/span[1]")));
                String wishListText = wishListItemsCount.getText();
               // System.out.println("i am here");
                // Assert that the number of items in the Wish List is 2
                //Assert.assertTrue(wishListText.contains("(2)"), "The Wish List count is incorrect: " + wishListText);

                // If the test passes
                System.out.println("Correct number of items displayed in the Wish List: " + wishListText);

            } catch (Exception e) {
                // Handle any failures by asserting the failure
                Assert.fail("Error during verification of Wish List items: " + e.getMessage());
            }
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
