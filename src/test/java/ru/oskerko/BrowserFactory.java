package ru.oskerko;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import ru.oskerko.util.TestHelper;

import java.io.File;

public enum BrowserFactory {
    CHROME{
        public WebDriver create(){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            WebDriver driver = new ChromeDriver(options);
            return driver;
        }
    },

    CHROME_HEADLESS{
        public WebDriver create(){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--headless");
            WebDriver driver = new ChromeDriver(options);
            return driver;
        }
    },

    FIREFOX{
        public WebDriver create(){
            String bin = TestHelper.readProperties(PROPERTIES_FILENAME).getProperty(FIREFOX_BIN);

            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "target"+File.separator +"firefox.log");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_BINARY, bin);

            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("dom.webnotifications.enabled", false);
            WebDriver driver = new FirefoxDriver(options);
            return driver;
        }
    },

    OPERA{
        public WebDriver create(){
            String bin = TestHelper.readProperties(PROPERTIES_FILENAME).getProperty(OPERA_BIN);

            OperaOptions options = new OperaOptions();
            options.setBinary(bin);
            options.addArguments("--disable-notifications");

            WebDriver driver = new OperaDriver(options);
            return driver;
        }
    };

    public static final String PROPERTIES_FILENAME = "application.properties";
    public static final String OPERA_BIN = "driver.opera.bin";
    public static final String FIREFOX_BIN = "driver.firefox.bin";
    public static final String DEFAULT_BROWSER = "CHROME";

    public WebDriver create(){
        return null;
    }

}
