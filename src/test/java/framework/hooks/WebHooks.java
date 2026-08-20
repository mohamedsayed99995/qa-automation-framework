package framework.hooks;

import framework.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class WebHooks {

    @Before("@ui")
    public void startBrowser() {
        DriverFactory.createDriver();
    }

    @After("@ui")
    public void stopBrowser() {
        DriverFactory.quitDriver();
    }
}