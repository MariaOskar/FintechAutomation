package ru.oskerko.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MainGooglePage extends Page<MainGooglePage> {
    private static final String GOOGLE_LINK = "https://www.google.ru/";
    private static final By AUTOCOMPLETE_HINTS_LIST_LOCATOR = By.cssSelector("ul > li[role='presentation']");
    //private static final By AUTOCOMPLETE_OPTIONS_LOCATOR = By.cssSelector("li[role='presentation'] div[role=option]");

    @FindBy(name = "q")
    WebElement searchField;

    public MainGooglePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainGooglePage open(){
        return open(GOOGLE_LINK);
    }

    public List<WebElement> getHintsList(){
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(AUTOCOMPLETE_HINTS_LIST_LOCATOR,1));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AUTOCOMPLETE_HINTS_LIST_LOCATOR));
        return driver.findElements(AUTOCOMPLETE_HINTS_LIST_LOCATOR);
    }

    public MainGooglePage enterQuery(String query){
        searchField.clear();
        searchField.sendKeys(query);
        return this;
    }

    public void chooseHint(String text){
        boolean hintExists = false;
        for(WebElement e: getHintsList()){
            System.out.println(e.getText());
            if (e.getText().contains(text)){
                hintExists = true;
                e.click();
                break;
            }
        }
        if(!hintExists) {
            throw new RuntimeException("Подсказка не найдена");
        }

    }




}
