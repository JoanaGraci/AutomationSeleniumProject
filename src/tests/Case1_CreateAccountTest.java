package tests;

import org.testng.annotations.Test;
import Pages.AccountPage;
import Pages.CreateAccountPage;
import Pages.HomePage;
import Utilities.BaseTest;

public class Case1_CreateAccountTest extends BaseTest {
	
    @Test
    public void testCreateAccount() {
    	
        driver.get("https://magento.softwaretestingboard.com/"); //navigate to the website
        
        //create object for different page classes
        HomePage homePage = new HomePage(driver, wait);
        CreateAccountPage createAccountPage = new CreateAccountPage(driver, wait);
        AccountPage accountPage = new AccountPage(driver, wait);

        homePage.clickCreateAccount();
        createAccountPage.checkTitle();
        createAccountPage.fillPersonalInformation("Joana", "Test", "joana.test@example.com", "Password123");
        createAccountPage.clickCreateAccount();
        accountPage.verifySuccessMessage("Thank you for registering");
        accountPage.signOut();
        
    }
}