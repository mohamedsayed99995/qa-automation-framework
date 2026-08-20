package mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MobileLoginPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public MobileLoginPage() {
        driver = MobileDriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private final By username =
            AppiumBy.accessibilityId("test-Username");

    private final By password =
            AppiumBy.accessibilityId("test-Password");

    private final By loginButton =
            AppiumBy.accessibilityId("test-LOGIN");

    public void login(String user, String pass) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(username))
                .sendKeys(user);

        wait.until(ExpectedConditions.visibilityOfElementLocated(password))
                .sendKeys(pass);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }

    public boolean isLoggedIn() {
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        AppiumBy.accessibilityId("test-PRODUCTS")
                )
        ).isDisplayed();
    }

    public boolean hasLoginError() {
        String source = driver.getPageSource().toLowerCase();

        return source.contains("invalid")
                || source.contains("error");
    }
}