package pages;

import framework.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CartPage {
    private final WebDriver driver;
    private final By title = By.cssSelector(".title");
    private final By cartItems = By.cssSelector(".cart_item .inventory_item_name");
    private final By checkoutButton = By.id("checkout");

    public CartPage() {
        driver = DriverFactory.getDriver();
    }

    public boolean isDisplayed() {
        return driver.findElement(title).getText().equals("Your Cart");
    }

    public List<String> getItemNames() {
        return driver.findElements(cartItems).stream()
                .map(e -> e.getText())
                .toList();
    }

    public CheckoutPage checkout() {
        driver.findElement(checkoutButton).click();
        return new CheckoutPage();
    }
}
