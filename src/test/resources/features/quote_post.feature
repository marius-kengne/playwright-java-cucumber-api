Feature: Quote API tests

  Scenario: Post a new quote
    Given the API server is running
    When I post a new quote with id "10", author "takam joslin", and quote "the best cooker"
    Then the response status should be 200
    And the response should contain "takam joslin"
