package com.popcorn.cucumber;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = "GLUE_PROPERTY_NAME",
        value = "com.popcorn.stepdefinition"
)
public class CucumberTest {
    @Test
    void setup() {

    }
}
