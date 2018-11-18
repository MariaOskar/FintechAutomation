package ru.oskerko.pages;

import com.lazerycode.selenium.util.http.FileDownloader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.List;

import static com.lazerycode.selenium.util.http.CheckFileHash.getFileHash;
import static com.lazerycode.selenium.util.http.TypeOfHash.SHA1;
import static org.junit.Assert.assertEquals;

public class TinkoffDocsPage extends Page<TinkoffDocsPage> {

    private static final String TINKOFF_DOCS_LINK = "https://www.tinkoff.ru/mobile-operator/documents/";

    private static final String DOC_NAME = "Описание акции \"Удвоим минуты и гигабайты в первый месяц\"";
    private static final String DOC_HASH = "ad3b5363abaa8f2e51a909b67c96a920e61253c0";

    private static final By LINKS_SELECTOR = By.cssSelector("div.ui-grid__column.ui-grid__column_desktop_11 a");
    private static final By LINKS_WRAPPER_SELECTOR = By.cssSelector(".ui-grid__column.ui-grid__column_desktop_11 > div");

    public TinkoffDocsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public TinkoffDocsPage open(){
        open(TINKOFF_DOCS_LINK);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(LINKS_SELECTOR,1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LINKS_WRAPPER_SELECTOR));
        return this;
    }

    private List<WebElement> getLinks(){
        return driver.findElements(LINKS_SELECTOR);
    }

    public String findLinkByTitle(String title){
        for(WebElement link : getLinks()){
            if (link.getText().equals(title)){
                return link.getAttribute("href");
            }
        }
        throw new RuntimeException("Документ с таким именем не найден или не был загружен");
    }

    public void download(String href){
        try {
            FileDownloader fileDownloader = new FileDownloader(driver);
            fileDownloader.setURI(href);
            File secretFile = fileDownloader.downloadFile();
            int httpStatusCode = fileDownloader.getLastDownloadHTTPStatus();
            assertEquals(httpStatusCode, 200);
            assertEquals(getFileHash(secretFile, SHA1), DOC_HASH);
        } catch (Exception e) {
            throw new RuntimeException("Документ с таким именем не был загружен");
        }
    }
}
