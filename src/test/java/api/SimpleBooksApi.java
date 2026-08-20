package api;

import framework.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class SimpleBooksApi {

    public SimpleBooksApi() {
        RestAssured.baseURI = ConfigReader.get("api.baseUrl");
    }

    public Response getBooks() {
        return given()
                .when()
                .get("/books");
    }

    public Response getBook(int bookId) {
        return given()
                .pathParam("bookId", bookId)
                .when()
                .get("/books/{bookId}");
    }

    public String createApiClientToken() {
        String body = """
                {
                  "clientName": "%s",
                  "clientEmail": "%s"
                }
                """.formatted(
                ConfigReader.get("api.clientName"),
                "qa.candidate." + UUID.randomUUID() + "@example.com"
        );

        Response response = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api-clients");

        response.then().statusCode(201);
        return response.jsonPath().getString("accessToken");
    }

    public String createOrder(String token, int bookId, String customerName) {
        String body = """
                {
                  "bookId": %d,
                  "customerName": "%s"
                }
                """.formatted(bookId, customerName);

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/orders");

        response.then().statusCode(201);
        return response.jsonPath().getString("orderId");
    }

    public Response getOrder(String token, String orderId) {
        return given()
                .header("Authorization", "Bearer " + token)
                .pathParam("orderId", orderId)
                .when()
                .get("/orders/{orderId}");
    }

    public Response updateOrder(String token, String orderId, String customerName) {
        String body = """
                {
                  "customerName": "%s"
                }
                """.formatted(customerName);

        return given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .pathParam("orderId", orderId)
                .body(body)
                .when()
                .patch("/orders/{orderId}");
    }

    public Response deleteOrder(String token, String orderId) {
        return given()
                .header("Authorization", "Bearer " + token)
                .pathParam("orderId", orderId)
                .when()
                .delete("/orders/{orderId}");
    }
}
