package ru.oskerko.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckBox {
    private WebDriver driver;
    private By selector;
    private By labelSelector = By.xpath("./parent::*/parent::*/label");
    private By descriptionSelector = By.cssSelector(".ui-checkbox__description-wrapper");

    public CheckBox(WebDriver driver, By selector) {
        this.driver = driver;
        this.selector = selector;
    }
    private WebElement getInput(){
        return driver.findElement(selector);
    }

    public WebElement getLabel(){
        return getInput().findElement(labelSelector);
    }

    public void click(){
        getLabel().click();
    }

    public boolean isSelected(){
        return getLabel().getAttribute("class").contains("ui-checkbox_checked");
    }

    public String getText(){
        String resultText = getDescription().getText();
        for(WebElement child : getDescription().findElements(By.xpath("./*"))){
            resultText = resultText.replaceFirst(child.getText().replaceAll("\\?","\\\\?"),"").trim();
        }
        return resultText;
    }

    private WebElement getDescription(){
        return getLabel().findElement(descriptionSelector);
    }
}
