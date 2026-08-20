package framework.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import mobile.MobileDriverFactory;

public class MobileHooks {

    @Before("@mobile")
    public void startMobileDriver() {
        MobileDriverFactory.createDriver();
    }

    @After("@mobile")
    public void tearDownMobile() {
        MobileDriverFactory.quitDriver();
    }
}