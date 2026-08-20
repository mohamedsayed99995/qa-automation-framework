package pages;

import framework.config.ConfigReader;
import framework.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By error = By.cssSelector("[data-test='error']");

    public LoginPage() {
        driver = DriverFactory.getDriver();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getInt("explicitWaitSeconds")
                )
        );
    }

    public LoginPage open() {
        driver.get(ConfigReader.get("baseUrl"));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(username)
        );

        return this;
    }

    public void login(String user, String pass) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(username)
        ).sendKeys(user);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(password)
        ).sendKeys(pass);

        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    public ProductsPage loginValid(String user, String pass) {
        login(user, pass);
        return new ProductsPage();
    }


    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(error)
        ).getText();
    }
}