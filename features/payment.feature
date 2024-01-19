Feature: Payment
  Scenario: Successful Payment
    Given a customer with a bank account with balance 1000
    And that the customer is registered with DTU Pay
    Given a merchant with a bank account with balance 2000
    And that the merchant is registered with DTU Pay
    Given a customer has at least 1 token
    When the merchant initiates a payment for 100 kr with the customer token
    Then the payment is successful
    And the balance of the customer at the bank is 900 kr
    And the balance of the merchant at the bank is 2100 kr

  Scenario: Multiple successful payments
    Given a customer with a bank account with balance 2000
    And that the customer is registered with DTU Pay
    Given a merchant with a bank account with balance 1000
    And that the merchant is registered with DTU Pay
    Given a customer has at least 5 token
    When the merchant initiates 5 payments sequentially for 100 kr with a customer token
    Then all payments are successful
    And the balance of the customer at the bank is 1500 kr
    And the balance of the merchant at the bank is 1500 kr

  Scenario: Multiple successful concurrent payments
    Given a customer with a bank account with balance 2000
    And that the customer is registered with DTU Pay
    Given a merchant with a bank account with balance 1000
    And that the merchant is registered with DTU Pay
    Given a customer has at least 5 token
    When the merchant initiates 5 payments concurrently for 100 kr with a customer token
    Then all payments are successful
    And the balance of the customer at the bank is 1500 kr
    And the balance of the merchant at the bank is 1500 kr

