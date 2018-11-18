package ru.oskerko.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ResultsGooglePage extends Page<ResultsGooglePage> {

    public static final By SEARCH_RESULTS = By.cssSelector("div.srg .g .rc .r > a");

    public ResultsGooglePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public List<WebElement> getResultsList(){
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(SEARCH_RESULTS,1));
        return driver.findElements(SEARCH_RESULTS);
    }

    public TinkoffMobilePage chooseResultByLink(String link){

        boolean urlExists = false;
        for (WebElement l: getResultsList()){
            if (l.getAttribute("href").contains(link)){
                urlExists = true;
                l.click();
                break;
            }
        }
        if(!urlExists) {
            throw new RuntimeException("Ссылка \""+link+"\" в результатах поиска не обнаружена");
        }

        TinkoffMobilePage tinkoffMobilePage = new TinkoffMobilePage(driver);
        tinkoffMobilePage.windowHandle = getNewWindowHandle(this);
        return tinkoffMobilePage;
    }
}
