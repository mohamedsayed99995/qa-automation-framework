package steps;

import framework.driver.DriverFactory;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CartPage;
import pages.CheckoutPage;
import pages.OverviewPage;
import pages.ProductsPage;

import java.util.List;

public class CheckoutSteps {
    private ProductsPage productsPage;
    private List<ProductsPage.Product> selectedProducts;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OverviewPage overviewPage;

    @When("I add the two most expensive products to the cart")
    public void addProducts() {
        productsPage = new ProductsPage();
        selectedProducts = productsPage.addTwoMostExpensiveProducts();
    }

    @When("I open the cart")
    public void openCart() {
        cartPage = productsPage.openCart();
    }

    @Then("I should be on the Cart page and see the two selected products")
    public void verifyCart() {
        Assert.assertTrue(cartPage.isDisplayed());
        List<String> actual = cartPage.getItemNames();
        Assert.assertEquals(actual.size(), 2);
        for (ProductsPage.Product product : selectedProducts) {
            Assert.assertTrue(actual.contains(product.name()),
                    "Missing product: " + product.name());
        }
    }

    @When("I checkout")
    public void checkout() {
        checkoutPage = cartPage.checkout();
    }

    @Then("I should be on the Checkout page")
    public void verifyCheckout() {
        Assert.assertTrue(checkoutPage.isDisplayed());
    }

    @When("I fill the checkout form with:")
    public void fillForm(io.cucumber.datatable.DataTable table) {
        var data = table.asMap(String.class, String.class);
        checkoutPage.fillForm(
                data.get("firstName"),
                data.get("lastName"),
                data.get("postalCode")
        );
    }

    @When("I continue to the overview")
    public void continueToOverview() {
        overviewPage = checkoutPage.continueToOverview();
    }

    @Then("I should be on the Overview page")
    public void verifyOverview() {
        Assert.assertTrue(overviewPage.isDisplayed());
    }

    @Then("the items total should equal the selected products total")
    public void verifyTotal() {

        double expected = selectedProducts.stream()
                .mapToDouble(ProductsPage.Product::price)
                .sum();
        double actual = overviewPage.getItemTotal();
        Assert.assertEquals(actual, expected, 0.001);
    }

    @Then("the URL should be {string}")
    public void verifyUrl(String expectedUrl) {
        Assert.assertEquals(overviewPage.getCurrentUrl(), expectedUrl);
    }

    @When("I finish the order")
    public void finish() {
        overviewPage.finish();
    }

    @Then("I should see the order confirmation messages")
    public void verifyConfirmation() {
        Assert.assertEquals(overviewPage.getCompleteHeader(), "Thank you for your order!");
        Assert.assertTrue(
                overviewPage.getCompleteText().toLowerCase().contains("dispatched"),
                "Dispatch confirmation was not displayed"
        );
    }
}
