package steps;

import api.SimpleBooksApi;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;

public class SimpleBooksApiSteps {

    private SimpleBooksApi api;
    private Response response;
    private String token;
    private String orderId;

    @Given("I have a valid API client token")
    public void createApiClientToken() {
        api = new SimpleBooksApi();
        token = api.createApiClientToken();

        Assert.assertNotNull(token, "API client token should not be null");
    }

    @When("I send a GET request to get all books")
    public void getBooks() {
        api = new SimpleBooksApi();
        response = api.getBooks();
    }

    @Then("the books list should not be empty")
    public void verifyBooksListIsNotEmpty() {
        Assert.assertFalse(
                response.jsonPath().getList("$").isEmpty(),
                "Books list should not be empty"
        );
    }

    @When("I send a GET request to get book with id {int}")
    public void getSpecificBook(int bookId) {
        api = new SimpleBooksApi();
        response = api.getBook(bookId);
    }

    @Then("the book id should be {int}")
    public void verifyBookId(int expectedId) {
        Assert.assertEquals(
                response.jsonPath().getInt("id"),
                expectedId
        );
    }

    @When("I create an order for book id {int} with customer name {string}")
    public void createOrder(int bookId, String customerName) {
        orderId = api.createOrder(
                token,
                bookId,
                customerName
        );
    }

    @Then("the order should be created successfully")
    public void verifyOrderCreated() {
        Assert.assertNotNull(
                orderId,
                "Order ID should not be null"
        );
    }

    @Then("the order id should not be null")
    public void verifyOrderId() {
        Assert.assertNotNull(
                orderId,
                "Order ID should not be null"
        );
    }

    @Given("I have an existing order for book id {int} with customer name {string}")
    public void createExistingOrder(int bookId, String customerName) {
        orderId = api.createOrder(
                token,
                bookId,
                customerName
        );

        Assert.assertNotNull(orderId, "Order ID should not be null");
    }

    @When("I update the order customer name to {string}")
    public void updateOrder(String customerName) {
        response = api.updateOrder(
                token,
                orderId,
                customerName
        );
    }

    @When("I get the updated order")
    public void getUpdatedOrder() {
        response = api.getOrder(
                token,
                orderId
        );
    }

    @Then("the customer name should be {string}")
    public void verifyCustomerName(String expectedName) {
        response.then()
                .body(
                        "customerName",
                        Matchers.equalTo(expectedName)
                );
    }

    @When("I delete the order")
    public void deleteOrder() {
        response = api.deleteOrder(
                token,
                orderId
        );
    }

    @When("I get the deleted order")
    public void getDeletedOrder() {
        response = api.getOrder(
                token,
                orderId
        );
    }


    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.statusCode(), expectedStatusCode);
    }
}