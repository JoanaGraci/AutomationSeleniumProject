package tests;

import org.testng.annotations.Test;

import Pages.CustomerLoginPage;
import Pages.HomePage;
import Pages.JacketsPage;
import Pages.WishlistPage;
import Pages.WomenPage;
import Utilities.BaseTest;

public class Case4_WishListTest extends BaseTest {
	
    @Test
    public void testWishList() {
    	
        driver.get("https://magento.softwaretestingboard.com/");
        
        WishlistPage wishlistPage = new WishlistPage(driver, wait);
        JacketsPage jacketsPage = new JacketsPage(driver, wait);
        CustomerLoginPage loginPage = new CustomerLoginPage(driver, wait);
        HomePage homePage = new HomePage(driver, wait);
        WomenPage womenPage = new WomenPage(driver, wait);
        
        
        
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
        
        jacketsPage.initialCount();
        System.out.println("Step 8: Verified that the price filter is removed and the items number is increased.");
        
        wishlistPage.addItemsToWishlist();
        System.out.println("Step 9: Verified that the two first item are added in the Wish List.");
        
        wishlistPage.verifySuccessMesagge();
        System.out.println("Step 10: Verified that the successful message is shown.");
        
        wishlistPage.verifyWishlistCount();
        System.out.println("Step 11: Verified that the correct number of items is displayed");
    }
}