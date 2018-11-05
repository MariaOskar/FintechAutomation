package ru.oskerko.elements;

import org.openqa.selenium.WebElement;

public class TextInput {

    private WebElement input;

    public TextInput(WebElement input) {
        this.input = input;
    }

    public String getValue(){
        return input.getAttribute("value");
    }

    public void setValue(String value){
        input.clear();
        input.sendKeys(value);
    }

    public void click(){
        input.click();
    }

}
