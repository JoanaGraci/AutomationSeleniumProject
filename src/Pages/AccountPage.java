package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AccountPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By successMessage = By.cssSelector(".message-success");
    private By userProfileButton = By.xpath("//div[@class='panel header']//button[@type='button']");
    private By signOutLink = By.xpath("//li[@class='authorization-link']//a[contains(text(),'Sign Out')]");

    public AccountPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void verifySuccessMessage(String expectedMessage) {
        String actualMessage = driver.findElement(successMessage).getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Success message mismatch!");
    }

    public void signOut() {
        driver.findElement(userProfileButton).click();
        driver.findElement(signOutLink).click();
    }
    
    public void verifyUsernameDisplayed(String expectedUsername) {
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("html > body > div:nth-of-type(2) > header > div > div > ul > li > span")));
        Assert.assertTrue(username.isDisplayed(), "Login failed: Username not displayed.");
    }
}