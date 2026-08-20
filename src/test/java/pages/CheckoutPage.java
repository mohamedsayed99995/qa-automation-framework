package pages;

import framework.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private final WebDriver driver;

    public CheckoutPage() {
        driver = DriverFactory.getDriver();
    }

    public boolean isDisplayed() {
        return driver.findElement(By.cssSelector(".title")).getText()
                .equals("Checkout: Your Information");
    }

    public void fillForm(String firstName, String lastName, String postalCode) {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(postalCode);
    }

    public OverviewPage continueToOverview() {
        driver.findElement(By.id("continue")).click();
        return new OverviewPage();
    }
}
