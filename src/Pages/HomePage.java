package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	
    private WebDriver driver;
    private WebDriverWait wait;
    
    //locators
    private By createAccountLink = By.linkText("Create an Account");
    private By signInLink = By.linkText("Sign In");
    
    //constructor
    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickCreateAccount() {
        driver.findElement(createAccountLink).click();
    }

    public void clickSignIn() {
        driver.findElement(signInLink).click();
    }
}