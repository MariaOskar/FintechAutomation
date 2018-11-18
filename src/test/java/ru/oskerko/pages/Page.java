package ru.oskerko.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Page<T extends Page> {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String windowHandle;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        this.windowHandle = driver.getWindowHandle();
    }

    public T open(String url){
        driver.get(url);
        windowHandle = driver.getWindowHandle();
        return (T)this;
    }

    public T closeCurrentTab(){
        driver.close();
        return (T)this;
        //logger.info("Закрыта активная вкладка");
    }

    public T assertTitle(String title){
        wait.until(ExpectedConditions.titleIs(title));
        assertTrue(driver.getTitle().contains(title));
        return (T)this;
    }


    public T assertUrl(String url){
        wait.until(ExpectedConditions.urlToBe(url));
        assertEquals(url, driver.getCurrentUrl());
        return (T)this;
    }

    public String getOldWindowHandle(){
        return driver.getWindowHandle();
    }

    public String getNewWindowHandle(Page page) {
        return getNewWindowHandle(page.windowHandle);
    }

    public String getNewWindowHandle(String oldWindowHandle) {
        return (String)wait.until((ExpectedCondition<String>) driver -> {
            Set<String> newWindowsSet = driver.getWindowHandles();
            newWindowsSet.remove(oldWindowHandle);
            return newWindowsSet.size()>0?
                    newWindowsSet.iterator().next(): null;
        });
    }

    public T switchToWindow(Page page){
        driver.switchTo().window(page.windowHandle);
        return (T)this;
    }

    public T switchToWindow(String handle){
        driver.switchTo().window(handle);
        return (T)this;
    }

    public T refresh(){
        driver.navigate().refresh();
        return (T)this;
    }

}
