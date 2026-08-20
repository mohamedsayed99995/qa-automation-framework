package mobile;

import framework.config.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URI;
import java.time.Duration;

public final class MobileDriverFactory {
    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    private MobileDriverFactory() {}

    public static void createDriver() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();

            caps.setCapability("platformName", ConfigReader.get("mobile.platformName"));
            caps.setCapability("appium:automationName", ConfigReader.get("mobile.automationName"));
            caps.setCapability("appium:deviceName", ConfigReader.get("mobile.deviceName"));
            caps.setCapability("appium:udid", ConfigReader.get("mobile.udid"));
            caps.setCapability("appium:app", ConfigReader.get("mobile.apkPath"));

            caps.setCapability("appium:appPackage", "com.swaglabsmobileapp");
            caps.setCapability("appium:appActivity", "com.swaglabsmobileapp.SplashActivity");
            caps.setCapability("appium:appWaitActivity",
                    "com.swaglabsmobileapp.SplashActivity,com.swaglabsmobileapp.MainActivity");

            caps.setCapability("appium:autoGrantPermissions", true);

            AndroidDriver driver = new AndroidDriver(
                    URI.create(ConfigReader.get("mobile.appiumUrl")).toURL(),
                    caps
            );
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            DRIVER.set(driver);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create Appium driver", e);
        }
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) throw new IllegalStateException("Mobile driver not created");
        return driver;
    }

    public static void quitDriver() {
        if (DRIVER.get() != null) {
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}
