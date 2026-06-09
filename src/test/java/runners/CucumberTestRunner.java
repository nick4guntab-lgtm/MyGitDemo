package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", 
				 glue = "stepdefinations", 
				 monochrome = true, 
				 tags = "@ErrorValidation and Regression",
				 plugin = {"html:target/cucumber.html"}
				 
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

}
