package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/simple_books_api.feature",
        glue = "steps",
        tags = "@api",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api-report.html"
        },
        monochrome = true
)
public class ApiTestRunner extends AbstractTestNGCucumberTests {
}