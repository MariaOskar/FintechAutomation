package ru.oskerko.elements;

import org.openqa.selenium.WebElement;

public class Button {
    WebElement element;

    public Button(WebElement element) {
        this.element = element;
    }

    public void submit(){
        element.click();
    }

    public boolean isEnabled(){
        return element.isEnabled();
    }

}
