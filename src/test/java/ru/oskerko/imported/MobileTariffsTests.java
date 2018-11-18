package ru.oskerko.imported;

import org.junit.Test;
import ru.oskerko.BaseRunner;
import ru.oskerko.pages.MainGooglePage;
import ru.oskerko.pages.ResultsGooglePage;
import ru.oskerko.pages.TinkoffDocsPage;
import ru.oskerko.pages.TinkoffMobilePage;

import static org.junit.Assert.*;

public class MobileTariffsTests extends BaseRunner {

    private static final String MOSCOW = "Москва";
    private static final String TINKOFF_MOBILE_TARIFFS_LINK = "https://www.tinkoff.ru/mobile-operator/tariffs/";
    private static final String DOC_NAME = "Описание акции \"Удвоим минуты и гигабайты в первый месяц\"";

    @Test
    public void changeTabsTest() {

        MainGooglePage searchPage = new MainGooglePage(driver);
        searchPage.open()
                .enterQuery("мобайл тинькофф")
                .chooseHint("тинькофф мобайл тарифы");

        ResultsGooglePage resultPage = new ResultsGooglePage(driver);
        TinkoffMobilePage tinkoffMobilePage = resultPage.chooseResultByLink(TINKOFF_MOBILE_TARIFFS_LINK);

        resultPage
                .switchToWindow(tinkoffMobilePage)
                .assertTitle("Тарифы Тинькофф Мобайл")
                .switchToWindow(resultPage)
                .closeCurrentTab()
                .switchToWindow(tinkoffMobilePage)
                .assertUrl(TINKOFF_MOBILE_TARIFFS_LINK);

    }

    @Test
    public void changeRegionTest(){

        TinkoffMobilePage tariffsPage = new TinkoffMobilePage(driver);
        tariffsPage.open()
                .rejectRegionChoice()
                .selectMoscowRegion()
                .checkRegion(MOSCOW)
                .refresh()
                .checkRegion(MOSCOW);
        String moscowPriceDefault = tariffsPage.getTotalPrice();

        tariffsPage.chooseKrasnodar();

        String krasnodarPriceDefault = tariffsPage.getTotalPrice();

        assertNotEquals(moscowPriceDefault, krasnodarPriceDefault);

        tariffsPage.chooseMaxPackage();

        String krasnodarPriceMax = tariffsPage.getTotalPrice();

        tariffsPage
                .chooseMoscow()
                .chooseMaxPackage();

        String moscowPriceMax = tariffsPage.getTotalPrice();

        assertEquals(krasnodarPriceMax,moscowPriceMax);

    }

    @Test
    public void disabledButtonTest(){
        TinkoffMobilePage tariffsPage = new TinkoffMobilePage(driver);
        tariffsPage.open()
                .chooseEmptyPackage();
        assertFalse(tariffsPage.isButtonEnabled());
    }

    @Test
    public void checkFileDownload() {
        TinkoffDocsPage docsPage = new TinkoffDocsPage(driver);
        docsPage.open();
        String href = docsPage.findLinkByTitle(DOC_NAME);
        docsPage.download(href);
    }

}
