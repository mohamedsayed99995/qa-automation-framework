@api
Feature: Simple Books API

  @getBooks
  Scenario: Get all books
    When I send a GET request to get all books
    Then the response status code should be 200
    And the books list should not be empty

  @getBook
  Scenario: Get a specific book
    When I send a GET request to get book with id 1
    Then the response status code should be 200
    And the book id should be 1

  @createOrder
  Scenario: Create a new order
    Given I have a valid API client token
    When I create an order for book id 1 with customer name "QA Candidate"
    Then the order should be created successfully
    And the order id should not be null

  @updateOrder
  Scenario: Update an existing order
    Given I have a valid API client token
    And I have an existing order for book id 1 with customer name "QA Candidate"
    When I update the order customer name to "Updated QA Candidate"
    Then the response status code should be 204
    When I get the updated order
    Then the response status code should be 200
    And the customer name should be "Updated QA Candidate"

  @deleteOrder
  Scenario: Delete an existing order
    Given I have a valid API client token
    And I have an existing order for book id 1 with customer name "QA Candidate"
    When I delete the order
    Then the response status code should be 204
    When I get the deleted order
    Then the response status code should be 404
