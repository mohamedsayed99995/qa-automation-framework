package steps;

import io.cucumber.java.en.*;

import pages.ProductsPage;

import java.util.List;

import static org.testng.Assert.*;

public class ProductSteps {
    private ProductsPage productsPage;

    @When("I sort products by price from low to high")
    public void iSortProductsByPriceFromLowToHigh() {
        productsPage = new ProductsPage();
        productsPage.sortProductsLowToHigh();
    }

    @Then("the products should be displayed from lowest to highest price")
    public void theProductsShouldBeDisplayedFromLowestToHighestPrice() {

        List<ProductsPage.Product> products = productsPage.getProducts();

        for (int i = 0; i < products.size() - 1; i++) {
            assertTrue(
                    products.get(i).price() <= products.get(i + 1).price(),
                    "Products are not sorted from lowest to highest price"
            );
        }
    }

}
