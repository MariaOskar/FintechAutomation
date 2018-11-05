package ru.oskerko.imported;

import com.lazerycode.selenium.util.http.FileDownloader;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.oskerko.BaseRunner;
import ru.oskerko.elements.Button;
import ru.oskerko.elements.CheckBox;
import ru.oskerko.elements.Select;

import java.io.File;
import java.util.*;


import static com.lazerycode.selenium.util.http.CheckFileHash.getFileHash;
import static com.lazerycode.selenium.util.http.TypeOfHash.SHA1;
import static org.junit.Assert.*;

public class SecondTaskTests extends BaseRunner {

    private static final String MOSCOW = "Москва";
    private static final String KRASNODAR = "Краснодарский край";

    private static final String GOOGLE_LINK = "https://www.google.ru/";
    private static final String TINKOFF_MOBILE_TARIFFS_LINK = "https://www.tinkoff.ru/mobile-operator/tariffs/";
    private static final String TINKOFF_DOCS_LINK = "https://www.tinkoff.ru/mobile-operator/documents/";

    private static final String DOC_NAME = "Описание акции \"Удвоим минуты и гигабайты в первый месяц\"";
    private static final String DOC_HASH = "ad3b5363abaa8f2e51a909b67c96a920e61253c0";

    private static final String REGION_REJECTION = ".MvnoRegionConfirmation__optionRejection_2yo5M";
    private static final String MOSCOW_REGION_LINK = "//*[@class='MobileOperatorRegionsPopup__listParts_16aoL']/div/div[contains(text(),'Москва')]";
    private static final String CURRENT_REGION = ".MvnoRegionConfirmation__title_3WFCP";
    private static final String TOTAL_PRICE_WRAPPER = "h3.mobileOperatorProductCalculatorSchema__amountTitle_6kgKn";
    private static final String KRASNODAR_REGION_LINK = "//*[@class='MobileOperatorRegionsPopup__listParts_16aoL']/div/div[contains(text(),'Краснодар')]";
    private static final String INTERNET_SELECTOR = ".ui-form__fieldset_column-mob .ui-form__row_select:nth-child(1)";
    private static final String CALLS_SELECTOR = ".ui-form__fieldset_column-mob .ui-form__row_select:nth-child(2)";

    @Test
    public void changeTabsTest() {
        driver.get(GOOGLE_LINK);
        driver.findElement(By.name("q")).sendKeys("мобайл тинькофф");

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan( By.cssSelector("li[role='presentation']"),1));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy( By.cssSelector("li[role='presentation'] div[role=option]")));

        List<WebElement> list = driver.findElements(By.cssSelector("li[role='presentation']"));
        boolean hintExists = false;
        for(WebElement e: list){
            if (e.getText().contains("мобайл тинькофф тарифы")){
                hintExists = true;
                e.click();
                break;
            }
        }
        if(!hintExists) {
            throw new RuntimeException("Подсказка не найдена");
        }

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan( By.cssSelector("div.srg .g .rc .r > a"),1));
        String oldWindowHandle = driver.getWindowHandle();
        List<WebElement> links = driver.findElements(By.cssSelector("div.srg .g .rc .r > a"));
        boolean urlExists = false;
        for (WebElement l: links){
            if (l.getAttribute("href").contains(TINKOFF_MOBILE_TARIFFS_LINK)){
                urlExists = true;
                l.click();
                break;
            }
        }
        if(!urlExists) {
            throw new RuntimeException("Ссылка \""+TINKOFF_MOBILE_TARIFFS_LINK+"\" в результатах поиска не обнаружена");
        }

        String newWindowHandle = wait.until((ExpectedCondition<String>) driver -> {
            Set<String> newWindowsSet = driver.getWindowHandles();
            newWindowsSet.remove(oldWindowHandle);
            return newWindowsSet.size()>0?
                    newWindowsSet.iterator().next(): null;
        });

        driver.switchTo().window(newWindowHandle);
        wait.until(ExpectedConditions.titleIs("Тарифы Тинькофф Мобайл"));
        assertTrue(driver.getTitle().contains("Тарифы Тинькофф Мобайл"));
        driver.switchTo().window(oldWindowHandle);
        driver.close();
        driver.switchTo().window(newWindowHandle);
        wait.until(ExpectedConditions.urlToBe(TINKOFF_MOBILE_TARIFFS_LINK));
        assertEquals(TINKOFF_MOBILE_TARIFFS_LINK, driver.getCurrentUrl());
    }

    @Test
    public void changeRegionTest(){
        driver.get(TINKOFF_MOBILE_TARIFFS_LINK);
        driver.findElement(By.cssSelector(REGION_REJECTION)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ui-form-app-popup-wrapper_theme_mvno-regions")));
        driver.findElement(By.xpath(MOSCOW_REGION_LINK)).click();
        checkRegion(MOSCOW);
        driver.navigate().refresh();
        checkRegion(MOSCOW);

        Select internet = new Select(driver, By.cssSelector(INTERNET_SELECTOR));
        Select calls = new Select(driver, By.cssSelector(CALLS_SELECTOR));

        String moscowPriceDefault = getTotalPrice();
        changeRegion(KRASNODAR_REGION_LINK, KRASNODAR);

        wait.until( (driver) -> (!moscowPriceDefault.equals(getTotalPrice())));

        String krasnodarPriceDefault = getTotalPrice();

        assertNotEquals(moscowPriceDefault, krasnodarPriceDefault);

        internet.selectLast();
        calls.selectLast();

        CheckBox modemModeCheckBox = new CheckBox(driver, By.id("2058"));
        CheckBox unlimSmsCheckBox = new CheckBox(driver, By.id("2048"));

        modemModeCheckBox.click();
        unlimSmsCheckBox.click();

        String krasnodarPriceMax = getTotalPrice();

        changeRegion(MOSCOW_REGION_LINK, MOSCOW);

        internet.selectLast();
        calls.selectLast();

        modemModeCheckBox.click();
        unlimSmsCheckBox.click();

        String moscowPriceMax = getTotalPrice();

        assertEquals(krasnodarPriceMax,moscowPriceMax);
    }

    @Test
    public void disabledButtonTest(){
        driver.get(TINKOFF_MOBILE_TARIFFS_LINK);

        CheckBox messengersCheckBox = new CheckBox(driver, By.id("2050"));
        CheckBox socialMediaCheckBox = new CheckBox(driver, By.id("2053"));
        Button button = new Button(driver.findElement(By.cssSelector("button[type='button']")));
        Select internet = new Select( driver, By.cssSelector(INTERNET_SELECTOR));
        Select calls = new Select( driver, By.cssSelector(CALLS_SELECTOR));

        internet.selectFirst();
        calls.selectFirst();

        messengersCheckBox.click();
        socialMediaCheckBox.click();

        assertFalse(button.isEnabled());
    }

    @Test
    public void checkFileDownload() {
        driver.get(TINKOFF_DOCS_LINK);
        List<WebElement> links = driver.findElements(By.cssSelector("div.ui-grid__column.ui-grid__column_desktop_11 a"));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.ui-grid__column.ui-grid__column_desktop_11 a"),1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ui-grid__column.ui-grid__column_desktop_11 > div")));
        boolean findedAndDownloaded = false;
        for (WebElement link:links) {
            if(link.getText().equals(DOC_NAME)){
                try{
                    FileDownloader fileDownloader = new FileDownloader(driver);
                    fileDownloader.setURI(link.getAttribute("href"));
                    File secretFile = fileDownloader.downloadFile();
                    int httpStatusCode = fileDownloader.getLastDownloadHTTPStatus();
                    assertEquals(httpStatusCode, 200);
                    assertEquals(getFileHash(secretFile, SHA1), DOC_HASH);
                    findedAndDownloaded = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(!findedAndDownloaded){
            throw new RuntimeException("Документ с таким именем не найден или не был загружен");
        }
    }

    private String getTotalPrice(){
        String priceString = driver.findElement(By.cssSelector(TOTAL_PRICE_WRAPPER)).getText();
        return priceString.split(": ")[1].substring(0, priceString.split(": ")[1].length() - 2);
    }

    private void changeRegion(String regionLinkSelector, String expectedRegion){
        driver.findElement(By.cssSelector(CURRENT_REGION)).click();
        driver.findElement(By.xpath(regionLinkSelector)).click();
        checkRegion(expectedRegion);
    }

    private void checkRegion(String region){
        wait.until((driver) -> driver.findElement(By.cssSelector(CURRENT_REGION)).getText().contains(region));
        assertTrue(driver.findElement(By.cssSelector(CURRENT_REGION)).getText().contains(region));
    }
}
