package tests;

import org.testng.annotations.Test;

import Pages.CustomerLoginPage;
import Pages.HomePage;
import Pages.JacketsPage;
import Pages.ShoppingCartPage;
import Pages.WomenPage;
import Utilities.BaseTest;

public class Case5_ShoppingCartTest extends BaseTest {
	
    @Test
    public void testShoppingCart() {
    	
        driver.get("https://magento.softwaretestingboard.com/");
        
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver, wait);
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
        
        shoppingCartPage.addAllItemsToCart();
        System.out.println("Step 8: Added a specific product to the cart successfully.");
        
        shoppingCartPage.navigateToCart();
        System.out.println("Step 9: Navigated to the Shopping Cart successfully.");
        
        shoppingCartPage.verifyCartSummary();
        System.out.println("Step 10: Verified that the subtotal and order total match successfully.");
    }
}