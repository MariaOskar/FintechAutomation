package ru.oskerko;

import org.junit.Before;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BaseRunner {
    private static ThreadLocal<WebDriver> driverContainer = new ThreadLocal<WebDriver>();

    protected WebDriver driver;
    protected String baseUrl;

    @Before
    public void setUp(){
       if(driverContainer.get()!= null){
           driver = driverContainer.get();
       } else {
           driver = getDriver();
           driverContainer.set(driver);

           Runtime.getRuntime().addShutdownHook(new Thread(()->{
               driver.quit();
               driver = null;
           }));
       }
        driver.manage().window().maximize();
        baseUrl = "https://moscow-job.tinkoff.ru/";;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    private WebDriver getDriver(){
        String browserName = System.getProperty("driver").toUpperCase();
        try {
            BrowserFactory.valueOf(browserName);
        }catch (NullPointerException | IllegalArgumentException e){
            browserName = BrowserFactory.DEFAULT_BROWSER;
            System.setProperty("driver", browserName);
        }
        return BrowserFactory.valueOf(browserName).create();
    }
}
