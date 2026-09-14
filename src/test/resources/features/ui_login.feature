Feature: SauceDemo login

  @ui @invalidLogin
  Scenario Outline: Login with invalid credentials from external data
    Given I am on the SauceDemo login page
    When I login using invalid login data row <row>
    Then I should see the login error from data row

    Examples:
      | row |
      | 1   |
      | 2   |
      | 3   |
      | 4   |


  @ui @validLogin @checkout
  Scenario: Complete purchase using the two most expensive products
    Given I am on the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be navigated to the Products page
    When I add the two most expensive products to the cart
    And I open the cart
    Then I should be on the Cart page and see the two selected products
    When I checkout
    Then I should be on the Checkout page
    When I fill the checkout form with:
      | firstName | John   |
      | lastName  | Tester |
      | postalCode | 12345 |
    And I continue to the overview
    Then I should be on the Overview page
    And the items total should equal the selected products total
    And the URL should be "https://www.saucedemo.com/checkout-step-two.html"
    When I finish the order
    Then I should see the order confirmation messages



  @ui @validLogin
    Scenario:Sort the products with Price (low to high)
    Given I am on the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be navigated to the Products page
    When I sort products by price from low to high
    Then the products should be displayed from lowest to highest price

