package tests;

import org.testng.annotations.Test;
import Pages.AccountPage;
import Pages.CustomerLoginPage;
import Pages.HomePage;
import Utilities.BaseTest;

public class Case2_SignInTest extends BaseTest {
	
    @Test
    public void testSignIn() {
        driver.get("https://magento.softwaretestingboard.com/");
        HomePage homePage = new HomePage(driver, wait);
        CustomerLoginPage loginPage = new CustomerLoginPage(driver, wait);
        AccountPage accountPage = new AccountPage(driver, wait);

        homePage.clickSignIn();
        loginPage.login("joana.test@example.com", "Password123");
        System.out.println("Step 1: Signed in successfully.");
        
        accountPage.verifyUsernameDisplayed("Joana Test");
        System.out.println("Step 2: Username is displayed on right corner of the page successfully.");
        
        accountPage.signOut();
        System.out.println("Step 3: Signed out successfully.");
    }
}