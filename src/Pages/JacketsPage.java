package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class JacketsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;


    public JacketsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
    }

    public void applyColorFilter() {
    	WebElement colorDropdown = waitUntilElementClickable(By.xpath("//*[@id='narrow-by-list']/div[4]/div[1]"));
        colorDropdown.click();

        WebElement colorOption = waitUntilElementClickable(By.xpath("//a[@aria-label='Red']//div[@option-label='Red']"));
        colorOption.click();
    }

    public void verifyColorFilterApplied() {
    	
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

    public void applyPriceFilter() {
    	WebElement priceDropdown = waitUntilElementClickable(By.xpath("//*[@id='narrow-by-list']/div[10]/div[1]"));
        priceDropdown.click();

        WebElement priceRangeOption = waitUntilElementClickable(By.xpath("//div[10]//div[2]//ol[1]//li[1]//a[1]"));
        priceRangeOption.click();
    }

    public void verifyFilteredProducts() {
    	List<WebElement> filteredProducts = driver.findElements(By.cssSelector(".product-item"));
        Assert.assertEquals(filteredProducts.size(), 2, "The number of products displayed does not match the price range.");
        
    }
    
    public void verifyProductPriceRange() {
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
    
    public void initialCount() {
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
    
    
    
    
    
    
    private WebElement waitUntilElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitUntilElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
