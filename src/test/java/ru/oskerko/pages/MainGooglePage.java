package ru.oskerko.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainGooglePage extends Page<MainGooglePage> {
    Logger logger = LoggerFactory.getLogger(MainGooglePage.class);
    private static final String GOOGLE_LINK = "https://www.google.ru/";
    private static final By AUTOCOMPLETE_HINTS_LIST_LOCATOR = By.cssSelector("ul > li[role='presentation']");

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
        logger.info("Вводим запрос:"+query);
        return this;
    }

    public void chooseHint(String text){
        logger.info("Выбираем подсказку:"+text);
        boolean hintExists = false;
        for(WebElement e: getHintsList()){
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
