package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/WebUserTable.feature",
        glue = {"StepDefinitions"}
        ,tags = "@WebTables"
        ,monochrome = true
        ,plugin = {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class Run extends AbstractTestNGCucumberTests {
}
