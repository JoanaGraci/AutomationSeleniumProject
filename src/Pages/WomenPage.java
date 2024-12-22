package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WomenPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;


    public WomenPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
    }

    public void navigateToJackets() {
    	WebElement womenDropdown = waitUntilElementClickable(By.xpath("//span[text()='Women']"));
        womenDropdown.click();

        WebElement topsDropdown = waitUntilElementVisible(By.xpath("//a[contains(text(),'Tops')]"));
        actions.moveToElement(topsDropdown).perform();

        WebElement jacketsOption = waitUntilElementClickable(By.xpath("//a[contains(text(),'Jackets')]"));
        jacketsOption.click();
    }
    
    private WebElement waitUntilElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitUntilElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
