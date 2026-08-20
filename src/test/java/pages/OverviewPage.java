package pages;

import framework.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OverviewPage {
    private final WebDriver driver;

    public OverviewPage() {
        driver = DriverFactory.getDriver();
    }

    public boolean isDisplayed() {
        return driver.findElement(By.cssSelector(".title")).getText()
                .equals("Checkout: Overview");
    }

    public double getItemTotal() {
        String value = driver.findElement(
                By.xpath("//*[contains(text(),'Item total:')]")
        ).getText();

        return Double.parseDouble(
                value.replace("Item total: $", "").trim()
        );
    }

    public void finish() {
        driver.findElement(By.id("finish")).click();
    }

    public String getCompleteHeader() {
        return driver.findElement(By.cssSelector(".complete-header")).getText();
    }

    public String getCompleteText() {
        return driver.findElement(By.cssSelector(".complete-text")).getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}