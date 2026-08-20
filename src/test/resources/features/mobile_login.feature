Feature: SauceDemo mobile login

  @mobile @positive
  Scenario: Login with valid mobile credentials
    Given the SauceDemo mobile app is launched
    When I login in the mobile app with username "standard_user" and password "secret_sauce"
    Then I should be logged in successfully on mobile

  @mobile @negative
  Scenario: Login with invalid mobile credentials
    Given the SauceDemo mobile app is launched
    When I login in the mobile app with username "invalid_user" and password "invalid_password"
    Then I should see a mobile login error
