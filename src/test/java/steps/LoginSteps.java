package steps;

import framework.data.LoginCsvReader;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.ProductsPage;

public class LoginSteps {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private LoginCsvReader.LoginData currentData;

    @Given("I am on the SauceDemo login page")
    public void openLoginPage() {
        loginPage = new LoginPage().open();
    }

    @When("I login with username {string} and password {string}")
    public void login(String username, String password) {
        loginPage.login(username, password);
    }

    @When("I login using invalid login data row {int}")
    public void loginUsingDataRow(int row) {
        currentData = LoginCsvReader.read().get(row - 1);

        loginPage.login(
                currentData.username(),
                currentData.password()
        );
    }

    @Then("I should see the login error from data row")
    public void verifyErrorFromDataRow() {
        Assert.assertEquals(
                loginPage.getErrorMessage(),
                currentData.expectedMessage()
        );
    }

    @Then("I should see the login error {string}")
    public void verifyError(String expected) {
        Assert.assertEquals(
                loginPage.getErrorMessage(),
                expected
        );
    }

    @Then("I should be navigated to the Products page")
    public void verifyProducts() {
        productsPage = new ProductsPage();

        Assert.assertTrue(
                productsPage.isDisplayed(),
                "Products page was not displayed"
        );
    }
}