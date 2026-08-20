package pages;

import framework.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Comparator;
import java.util.List;

public class ProductsPage {
    private final WebDriver driver;

    private final By inventoryContainer = By.id("inventory_container");
    private final By inventoryItem = By.cssSelector(".inventory_item");
    private final By itemName = By.cssSelector(".inventory_item_name");
    private final By itemPrice = By.cssSelector(".inventory_item_price");
    private final By addButton = By.cssSelector("button");

    public ProductsPage() {
        this.driver = DriverFactory.getDriver();
    }

    public boolean isDisplayed() {
        return driver.findElement(inventoryContainer).isDisplayed();
    }

    public List<Product> getProducts() {
        return driver.findElements(inventoryItem).stream()
                .map(item -> new Product(
                        item.findElement(itemName).getText(),
                        parsePrice(item.findElement(itemPrice).getText()),
                        item.findElement(addButton)
                ))
                .toList();
    }

    public List<Product> getTwoMostExpensiveProducts() {
        return getProducts().stream()
                .sorted(Comparator.comparing(Product::price).reversed())
                .limit(2)
                .toList();
    }

    public List<Product> addTwoMostExpensiveProducts() {
        List<Product> selected = getTwoMostExpensiveProducts();
        selected.forEach(product -> product.addButton().click());
        return selected;
    }

    public CartPage openCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
        return new CartPage();
    }

    private double parsePrice(String value) {
        return Double.parseDouble(value.replace("$", ""));
    }

    public record Product(String name, double price, WebElement addButton) {}
}
