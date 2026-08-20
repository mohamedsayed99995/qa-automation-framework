package framework.driver;

import framework.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static void createDriver() {
        String browser = ConfigReader.get("browser").trim().toLowerCase();

        WebDriver driver;
        switch (browser) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (ConfigReader.getBoolean("headless")) {
                    options.addArguments("--headless=new");
                }
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (ConfigReader.getBoolean("headless")) {
                    options.addArguments("-headless");
                }
                driver = new FirefoxDriver(options);
                driver.manage().window().maximize();
            }
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser + ". Use chrome or firefox.");
        }

        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver was not created for this thread");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
