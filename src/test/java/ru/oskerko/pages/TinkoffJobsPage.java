package ru.oskerko.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.oskerko.elements.Select;
import ru.oskerko.elements.TextInput;


public class TinkoffJobsPage extends Page<TinkoffJobsPage> {
    Logger logger = LoggerFactory.getLogger(TinkoffJobsPage.class);
    private static final String TINKOF_JOBS_URL = "https://moscow-job.tinkoff.ru/";
    private static final By FIO_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(1)  .Error__errorMessage_q8BBY");
    private static final By EMAIL_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(2) div:nth-child(1) .Error__errorMessage_q8BBY");
    private static final By PHONE_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(2) div:nth-child(2) .Error__errorMessage_q8BBY");
    private static final By CITY_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(3)  .Error__errorMessage_q8BBY");
    private static final By JOB_SELECT_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(4)  .Error__errorMessage_q8BBY");
    private static final By JOB_SELECT_LOCATOR = By.className("SelectItem__contentWrapper_3eEeN");

    @FindBy(xpath = "//*[@class='Scene__form_3ubwk']")
    public WebElement form;

    @FindBy(name = "fio")
    public WebElement fioElement;

    @FindBy(name = "email")
    public WebElement emailElement;

    @FindBy(name = "phone")
    public WebElement phoneElement;

    @FindBy(name = "city")
    public WebElement cityElement;

    private TextInput fioField;
    private TextInput emailField;
    private TextInput phoneField;
    private TextInput cityField;
    private Select jobsField;


    public TinkoffJobsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);

        fioField = new TextInput(fioElement);
        emailField = new TextInput(emailElement);
        phoneField = new TextInput(phoneElement);
        cityField = new TextInput(cityElement);
        jobsField = new Select(driver, JOB_SELECT_LOCATOR);
    }

    public TinkoffJobsPage open(){
        return open(TINKOF_JOBS_URL);
    }

    public String getFioErrorText(){
        return driver.findElement(FIO_ERROR_ELEMENT).getText();
    }

    public String getEmailErrorText(){
        return driver.findElement(EMAIL_ERROR_ELEMENT).getText();
    }

    public String getPhoneErrorText(){
        return driver.findElement(PHONE_ERROR_ELEMENT).getText();
    }

    public String getCityErrorText(){
        return driver.findElement(CITY_ERROR_ELEMENT).getText();
    }

    public String getJobErrorText(){
        return driver.findElement(JOB_SELECT_ERROR_ELEMENT).getText();
    }





    public TinkoffJobsPage fillFIO(String str){
        logger.info("Заполняем поле `Ф.И.О.`");
        fioField.setValue(str);
        return this;
    }
    public TinkoffJobsPage fillEmail(String str){
        logger.info("Заполняем поле `Email`");
        emailField.setValue(str);
        return this;
    }
    public TinkoffJobsPage fillPhone(String str){
        logger.info("Заполняем поле `Телефон`");
        phoneField.setValue(str);
        return this;
    }
    public TinkoffJobsPage fillCity(String str){
        logger.info("Заполняем поле `Город`");
        cityField.setValue(str);
        return this;
    }

    public TinkoffJobsPage touchFIO(){
        logger.info("Кликаем по полю `Ф.И.О.`");
        fioField.click();
        return this;
    }
    public TinkoffJobsPage touchEmail(){
        logger.info("Кликаем по полю `Email`");
        emailField.click();
        return this;
    }
    public TinkoffJobsPage touchPhone(){
        logger.info("Кликаем по полю `Телефон`");
        phoneField.click();
        return this;
    }
    public TinkoffJobsPage touchCity(){
        logger.info("Кликаем по полю `Город`");
        cityField.click();
        return this;
    }

    public TinkoffJobsPage touchJobsField(){
        logger.info("Кликаем по полю `Вакансия`");
        jobsField.click();
        return this;
    }

    public TinkoffJobsPage touchForm(){
        logger.info("Кликаем по форме");
        form.click();
        return this;
    }
}
