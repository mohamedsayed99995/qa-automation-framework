package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps", "framework.hooks"},
        tags = "@mobile",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/mobile-report.html"
        },
        monochrome = true
)
public class MobileTestRunner extends AbstractTestNGCucumberTests {
}