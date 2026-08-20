package api;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class SimpleBooksApiTest {

    @Test
    public void getBooks() {

        Response response = new SimpleBooksApi().getBooks();

        response.then().statusCode(200);

        Assert.assertFalse(
                response.jsonPath().getList("$").isEmpty()
        );
    }


    @Test
    public void getSpecificBook() {

        Response response = new SimpleBooksApi().getBook(1);

        response.then().statusCode(200);

        Assert.assertEquals(
                response.jsonPath().getInt("id"),
                1
        );
    }


    @Test
    public void createOrder() {

        SimpleBooksApi api = new SimpleBooksApi();

        String token = api.createApiClientToken();

        String orderId = api.createOrder(
                token,
                1,
                "QA Candidate"
        );

        assertNotNull(
                orderId,
                "Order ID should not be null"
        );
    }


    @Test
    public void updateOrder() {

        SimpleBooksApi api = new SimpleBooksApi();

        String token = api.createApiClientToken();

        String orderId = api.createOrder(
                token,
                1,
                "QA Candidate"
        );

        Response response = api.updateOrder(
                token,
                orderId,
                "Updated QA Candidate"
        );

        response.then().statusCode(204);

        Response getResponse = api.getOrder(
                token,
                orderId
        );

        getResponse
                .then()
                .statusCode(200)
                .body(
                        "customerName",
                        Matchers.equalTo("Updated QA Candidate")
                );
    }


    @Test
    public void deleteOrder() {

        SimpleBooksApi api = new SimpleBooksApi();

        String token = api.createApiClientToken();

        String orderId = api.createOrder(
                token,
                1,
                "QA Candidate"
        );

        Response response = api.deleteOrder(
                token,
                orderId
        );

        response.then().statusCode(204);

        Response getResponse = api.getOrder(
                token,
                orderId
        );

        getResponse.then().statusCode(404);
    }
}