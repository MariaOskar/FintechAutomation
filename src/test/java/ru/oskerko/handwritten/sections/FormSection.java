package ru.oskerko.handwritten.sections;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FormSection {

    private static final By FIO_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(1)  .Error__errorMessage_q8BBY");
    private static final By EMAIL_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(2) div:nth-child(1) .Error__errorMessage_q8BBY");
    private static final By PHONE_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(2) div:nth-child(2) .Error__errorMessage_q8BBY");
    private static final By CITY_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(3)  .Error__errorMessage_q8BBY");
    private static final By JOB_SELECT_ERROR_ELEMENT = By.cssSelector(".Row__row_AjrJL:nth-child(4)  .Error__errorMessage_q8BBY");

    private WebDriver driver;

    @FindBy(name = "fio")
    public WebElement fioField;

    @FindBy(name = "email")
    public WebElement emailField;

    @FindBy(name = "phone")
    public WebElement phoneField;

    @FindBy(name = "city")
    public WebElement cityField;

    @FindBy(className = "SelectItem__contentWrapper_3eEeN")
    public WebElement jobSelect;


    public FormSection(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
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

    public void fillField(WebElement element, String str){
        element.click();
        element.clear();
        element.sendKeys(str,Keys.ENTER);
    }

    public FormSection fillFIO(String str){
        fillField(fioField,str);
        return this;
    }
    public FormSection fillEmail(String str){
        fillField(emailField,str);
        return this;
    }
    public FormSection fillPhone(String str){
        fillField(phoneField,str);
        return this;
    }
    public FormSection fillCity(String str){
        fillField(cityField,str);
        return this;
    }

}
