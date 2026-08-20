package mobile;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MobileTest {

    @BeforeMethod
    public void setUp() {
        MobileDriverFactory.createDriver();
    }

    @AfterMethod
    public void tearDown() {
        MobileDriverFactory.quitDriver();
    }

    @Test
    public void validLogin() {
        MobileLoginPage page = new MobileLoginPage();
        page.login("standard_user", "secret_sauce");
        Assert.assertTrue(page.isLoggedIn(), "User was not logged in successfully");
    }

    @Test
    public void invalidLogin() {
        MobileLoginPage page = new MobileLoginPage();
        page.login("invalid_user", "invalid_password");
        Assert.assertTrue(page.hasLoginError(), "Expected mobile login error was not displayed");
    }
}
