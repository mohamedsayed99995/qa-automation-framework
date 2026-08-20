package framework.hooks;

import framework.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CucumberHooks {

    @After(value = "@ui", order = 1)
    public void attachScreenshotOnFailure(Scenario scenario) {

        if (!scenario.isFailed()) {
            return;
        }

        try {
            WebDriver driver = DriverFactory.getDriver();

            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            scenario.attach(
                    screenshot,
                    "image/png",
                    "Failure Screenshot"
            );

        } catch (Exception e) {
            System.out.println(
                    "Could not capture failure screenshot: "
                            + e.getMessage()
            );
        }
    }
}