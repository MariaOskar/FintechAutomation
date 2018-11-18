package ru.oskerko.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ru.oskerko.elements.Button;
import ru.oskerko.elements.CheckBox;
import ru.oskerko.elements.Select;

import static org.junit.Assert.assertTrue;

public class TinkoffMobilePage extends Page<TinkoffMobilePage> {
    private static final String TINKOFF_MOBILE_TARIFFS_LINK = "https://www.tinkoff.ru/mobile-operator/tariffs/";
    private static final String MOSCOW = "Москва";
    private static final String KRASNODAR = "Краснодар";
    private static final By REGION_REJECTION_SELECTOR = By.cssSelector(".MvnoRegionConfirmation__optionRejection_2yo5M");
    private static final By MOSCOW_REGION_LINK_SELECTOR = By.xpath("//*[@class='MobileOperatorRegionsPopup__listParts_16aoL']//div[contains(text(),'Москва')]");
    private static final By CURRENT_REGION_SELECTOR = By.cssSelector(".MvnoRegionConfirmation__title_3WFCP");
    private static final By INTERNET_SELECTOR = By.cssSelector(".ui-form__fieldset_column-mob .ui-form__row_select:nth-child(1)");
    private static final By CALLS_SELECTOR = By.cssSelector(".ui-form__fieldset_column-mob .ui-form__row_select:nth-child(2)");
    private static final By TOTAL_PRICE_WRAPPER = By.cssSelector("h3.mobileOperatorProductCalculatorSchema__amountTitle_6kgKn");
    private static final String KRASNODAR_REGION_LINK = "//*[@class='MobileOperatorRegionsPopup__listParts_16aoL']//div[contains(text(),'Краснодар')]";
    private static final String MOSCOW_REGION_LINK = "//*[@class='MobileOperatorRegionsPopup__listParts_16aoL']//div[contains(text(),'Москва')]";
    private static final By MODEM_MODE_CHECKBOX_SELECTOR = By.id("2058");
    private static final By UNLIM_SMS_CHECKBOX_SELECTOR = By.id("2048");
    private static final By MESSENGER_CHECKBOX_SELECTOR = By.id("2050");
    private static final By SOCIALMEDIA_CHECKBOX_SELECTOR = By.id("2053");
    private static final By BUTTON_SELECTOR = By.cssSelector("button[type='button']");

    public TinkoffMobilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public TinkoffMobilePage open(){
        return open(TINKOFF_MOBILE_TARIFFS_LINK);
    }

    public WebElement getRegionRejectionElement(){
        return driver.findElement(REGION_REJECTION_SELECTOR);
    }

    private WebElement getMoscowLinkElement(){
        return driver.findElement(MOSCOW_REGION_LINK_SELECTOR);
    }

    private WebElement getCurrentRegionElement(){
        return driver.findElement(CURRENT_REGION_SELECTOR);
    }

    public TinkoffMobilePage rejectRegionChoice(){
        getRegionRejectionElement().click();
        return this;
    }

    public TinkoffMobilePage selectMoscowRegion(){
        getMoscowLinkElement().click();
        return this;
    }

    private Select getInternetSelect(){
        return new Select(driver, INTERNET_SELECTOR);
    }

    private Select getCallsSelect(){
        return new Select(driver, CALLS_SELECTOR);
    }

    private WebElement getTotalPriceWrapper(){
        return driver.findElement(TOTAL_PRICE_WRAPPER);
    }

    public String getTotalPrice(){
        String priceString = getTotalPriceWrapper().getText();
        return priceString.split(": ")[1].substring(0, priceString.split(": ")[1].length() - 2);
    }

    public TinkoffMobilePage checkRegion(String region){
        wait.until((driver) -> getCurrentRegionElement().getText().contains(region));
        assertTrue(getCurrentRegionElement().getText().contains(region));
        return this;
    }

    public TinkoffMobilePage changeRegion(String regionLinkSelector, String expectedRegion){
        String previousPrice = getTotalPrice();

        getCurrentRegionElement().click();
        driver.findElement(By.xpath(regionLinkSelector)).click();
        checkRegion(expectedRegion);

        wait.until( (driver) -> (!previousPrice.equals(getTotalPrice())));

        return this;
    }

    public TinkoffMobilePage chooseMoscow(){
        changeRegion(MOSCOW_REGION_LINK, MOSCOW);
        return this;
    }
    public TinkoffMobilePage chooseKrasnodar(){
        changeRegion(KRASNODAR_REGION_LINK, KRASNODAR);
        return this;
    }

    public TinkoffMobilePage selectMaxOptionInternet(){
        getInternetSelect().selectLast();
        return this;
    }

    public TinkoffMobilePage selectMaxOptionCalls(){
        getCallsSelect().selectLast();
        return this;
    }

    public CheckBox getModemModeCheckBox(){
        return new CheckBox(driver, MODEM_MODE_CHECKBOX_SELECTOR);
    }

    public CheckBox getUnlimSmsCheckBox(){
        return new CheckBox(driver,UNLIM_SMS_CHECKBOX_SELECTOR);
    }

    public TinkoffMobilePage chooseModemMode(){
        getModemModeCheckBox().click();
        return this;
    }

    public TinkoffMobilePage chooseUnlimSms(){
        getUnlimSmsCheckBox().click();
        return this;
    }

    public TinkoffMobilePage chooseMaxPackage(){
        return this
                .selectMaxOptionCalls()
                .selectMaxOptionInternet()
                .chooseModemMode()
                .chooseUnlimSms();
    }

    private CheckBox getMessengerCeckBox(){
        return new CheckBox(driver, MESSENGER_CHECKBOX_SELECTOR );
    }

    private CheckBox getSocialMediaCheckBox(){
        return new CheckBox(driver, SOCIALMEDIA_CHECKBOX_SELECTOR);
    }

    private Button getButton(){
        return new Button(driver.findElement(BUTTON_SELECTOR));
    }

    public TinkoffMobilePage selectFirstInternetOption(){
        getInternetSelect().selectFirst();
        return this;
    }

    public TinkoffMobilePage selectFirstCallsOption(){
        getCallsSelect().selectFirst();
        return this;
    }

    public TinkoffMobilePage checkMessengerCheckbox(){
        getMessengerCeckBox().click();
        return this;
    }

    public TinkoffMobilePage checkSocialMediaCheckBox(){
        getSocialMediaCheckBox().click();
        return this;
    }

    public TinkoffMobilePage chooseEmptyPackage(){
        return this
                .selectFirstCallsOption()
                .selectFirstInternetOption()
                .checkSocialMediaCheckBox()
                .checkMessengerCheckbox();
    }

    public boolean isButtonEnabled(){
        return getButton().isEnabled();
    }



}
