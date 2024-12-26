Feature: Ping API
  As a user of the system
  I want to verify the health of the application
  So that I know the application is running as expected

  Scenario: Verify the health status of the application
    Given the application is running
    When I send a GET request to "/api/v1/users/ping"
    Then the response status code should be 200
    And the response body should contain:
      | field      | value            |
      | status     | 200              |
      | message    | Up and Healthy   |
    And the response body should include a "timestamp" field



#Feature: Ping API
#  As a user of the system
#  I want to verify the health of the application
#  So that I know the application is running as expected
#
#  Scenario: Verify the health status of the application
#    Given the application is running
#    When I send a GET request to "/api/v1/users/ping"
#    Then the response status code should be 200
#    And the response body should contain: status <status> message <message> timestamp <timestamp>
#      | status      | message            | timestamp                       |
#      | 200         | "success"          | "2024-12-20T22:18:18.693+05:30" |
