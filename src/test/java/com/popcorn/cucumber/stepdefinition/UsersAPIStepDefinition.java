package com.popcorn.cucumber.stepdefinition;

import com.popcorn.cucumber.CucumberSpringConfiguration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
public class UsersAPIStepDefinition extends CucumberSpringConfiguration {

    private ResponseEntity<Map<String, Object>> response;

    @Given("the application is running")
    public void the_application_is_running() {
        // Assuming Spring Boot handles the application status
        assertNotNull(testRestTemplate, "The application should be running.");
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        response = testRestTemplate.getForEntity(endpoint, (Class<Map<String, Object>>) (Class<?>) Map.class);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {

    }

    @Then("the response body should contain:")
    public void the_response_body_should_contain(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, Object>> fieldValuesMap = dataTable.asMaps(String.class, Object.class);
    }

    @Then("the response body should include a {string} field")
    public void the_response_body_should_include_a_field(String fieldName) {

    }

//    @Before
//    void setupScenario() throws Exception {
//
//    }
//
//    @Given("the application is running")
//    public void theApplicationIsRunning() {
//    }
//
//    @When("I send a GET request to {string}")
//    public void iSendAGETRequestTo(String arg0) {
//        URI pingURI = URI.create("/api/v1/users/ping");
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
//        testtestRestTemplate.exchange(pingURI, HttpMethod.GET, httpEntity, String.class);
//    }
//
//    @Then("the response status code should be {int}")
//    public void theResponseStatusCodeShouldBe(int arg0) {
//
//    }

//    @And("the response body should contain: status {int} message {string} timestamp {string}")
//    public void theResponseBodyShouldContainStatusStatusMessageMessageTimestampTimestamp(int status, String message, String timestamp) {
//
//    }

//    @And("the response body should contain: status <status> message <message> timestamp <timestamp>")
//    public void theResponseBodyShouldContainStatusStatusMessageMessageTimestampTimestamp(int status, String message, String timestamp) {
//
//    }
}
