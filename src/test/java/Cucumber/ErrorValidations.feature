@tag
Feature: Error validation

  @ErrorValidation
  Scenario Outline: Login Error Validation
    Given I landed on the Ecommerce Page
    When Logged In with username <name> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples: 
      | name                  | password     |
      | rahulshetty@gmail.com | WrongPass123 |