package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import mobile.MobileDriverFactory;
import mobile.MobileLoginPage;
import org.testng.Assert;

public class MobileLoginStepDefs {

    private MobileLoginPage loginPage;

    @Given("the SauceDemo mobile app is launched")
    public void AppIsLaunched() {
        loginPage = new MobileLoginPage();
    }


    @When("I login in the mobile app with username {string} and password {string}")
    public void ProvideUsernameAndPassword(
            String username,
            String password) {

        loginPage.login(username, password);
    }

    @Then("I should be logged in successfully on mobile")
    public void LoggedInSuccessfully() {

        Assert.assertTrue(
                loginPage.isLoggedIn(),
                "User was not logged in successfully"
        );
    }

    @Then("I should see a mobile login error")
    public void ShowError() {

        Assert.assertTrue(
                loginPage.hasLoginError(),
                "Expected mobile login error was not displayed"
        );
    }
}