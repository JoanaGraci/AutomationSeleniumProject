package tests;

import org.testng.annotations.Test;
import Pages.JacketsPage;
import Pages.WomenPage;
import Utilities.BaseTest;
import Pages.CustomerLoginPage;
import Pages.HomePage;

public class Case3_PageFiltersTest extends BaseTest {
	
    @Test
    public void testPageFilters() {
        driver.get("https://magento.softwaretestingboard.com/");
        
        WomenPage womenPage = new WomenPage(driver, wait);
        JacketsPage jacketsPage = new JacketsPage(driver, wait);
        CustomerLoginPage loginPage = new CustomerLoginPage(driver, wait);
        HomePage homePage = new HomePage(driver, wait);
        
        
        homePage.clickSignIn();
        loginPage.login("joana.test@example.com", "Password123");
        System.out.println("Step 1: Signed in successfully.");
        
        womenPage.navigateToJackets();
        System.out.println("Step 2: Selected Jackets under Women category successfully.");
        
        jacketsPage.applyColorFilter();
        System.out.println("Step 3: Selected color from the dropdown successfully.");
        
        jacketsPage.verifyColorFilterApplied();
        System.out.println("Step 4: Verified that all products have the selected color bordered in red successfully.");
        
        jacketsPage.applyPriceFilter();
        System.out.println("Step 5: Selected the price range $50.00 - $59.99 successfully.");
        
        jacketsPage.verifyFilteredProducts();
        System.out.println("Step 6: Verified that only two products are displayed after price filter successfully.");

        jacketsPage.verifyProductPriceRange();
        System.out.println("Step 7: Verified that the price of products matches the selected range successfully.");
    }
}