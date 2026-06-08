@tag
Feature: Purchase the product from Ecommerce Website
	I want to use this template for my feature file

  Background:
    Given I landed on the Ecommerce Page

  @Regression
  Scenario Outline: Positive test of submitting the test
    Given Logged In with username <name> and password <password>
    When I add product <productName> to cart
    And Checkout <productName> and submit the order
    Then "Thankyou for the order." message is displayed on confirmation page

    Examples:
      |name                   |password |productName |
      |nsharma@gmail.com      |Nikhil@1234|ZARA COAT 3 |
