package feature;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = {"src/test/java/feature"},
	glue= {"stepDefinitions"},
	tags= "",
//	plugin= {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json"} ,
	monochrome= true,
//	strict= true,
	dryRun= false

	)
public class TestRunner {

}