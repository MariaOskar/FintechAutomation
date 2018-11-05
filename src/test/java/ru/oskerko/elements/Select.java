package ru.oskerko.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Select {

    WebDriver driver;

    private By rootSelector;
    private By itemsSelector = By.cssSelector(".ui-dropdown-field-list > div");
    private By itemValueSelector = By.cssSelector("div > div > span.ui-dropdown-field-list__item-text");
    private By currentValueSelector = By.cssSelector(".ui-select__title-flex-text");


    private WebElement select;
    public Select(WebElement select) {
        this.select = select;
    }

    public Select(WebDriver driver, By rootSelector) {
        this.driver = driver;
        this.rootSelector = rootSelector;
    }

    public Select(WebDriver driver, By rootSelector, By itemsSelector) {
        this.driver = driver;
        this.rootSelector = rootSelector;
        this.itemsSelector = itemsSelector;
    }

    public WebElement getRoot(){
        return driver.findElement(rootSelector);
    }

    public List<WebElement> getItems(){
        return getRoot().findElements(itemsSelector);
    }

    public List<String> getOptions(){
        getRoot().click();
        List<String> options = new ArrayList<>();
        for (WebElement item: getItems()){
            options.add(item.findElement(itemValueSelector).getText());
        }
        return options;
    }

    public void select(String value){
        getRoot().click();
        for (WebElement item: getItems()){
            if(item.findElement(itemValueSelector).getText().equals(value)){
                item.click();
            }
        }
    }

    public void selectFirst(){
        getRoot().click();
        List<WebElement> items = getItems();
        if(items.size()>0)
            items.get(0).click();
    }

    public void selectLast(){
        getRoot().click();
        List<WebElement> items = getItems();
        if(items.size() > 0)
            items.get(items.size()-1).click();
    }

    public String getValue(){
        return getRoot().findElement(currentValueSelector).getText();
    }

    private String getTextFromHiddenElement(WebDriver driver, WebElement element){
        return (String)((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", element);
    }
}
