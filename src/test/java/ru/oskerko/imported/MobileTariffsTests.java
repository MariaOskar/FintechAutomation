package ru.oskerko.imported;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.oskerko.BaseRunner;
import ru.oskerko.pages.MainGooglePage;
import ru.oskerko.pages.ResultsGooglePage;
import ru.oskerko.pages.TinkoffDocsPage;
import ru.oskerko.pages.TinkoffMobilePage;

import static org.junit.Assert.*;

public class MobileTariffsTests extends BaseRunner {
    Logger logger = LoggerFactory.getLogger(MobileTariffsTests.class);
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

        logger.info("Получаем цену тарифа по умолчанию для Московского региона");
        String moscowPriceDefault = tariffsPage.getTotalPrice();

        tariffsPage.chooseKrasnodar();

        logger.info("Получаем цену тарифа по умолчанию для Краснодарского региона");
        String krasnodarPriceDefault = tariffsPage.getTotalPrice();

        logger.info("Проверяем на несоответствие цены пакетов по умолчанию в Москве и Краснодаре");
        assertNotEquals(moscowPriceDefault, krasnodarPriceDefault);

        tariffsPage.chooseMaxPackage();

        logger.info("Получаем цену максимального тарифа для Краснодарского региона");
        String krasnodarPriceMax = tariffsPage.getTotalPrice();

        tariffsPage
                .chooseMoscow()
                .chooseMaxPackage();

        logger.info("Получаем цену максимального тарифа для Московского региона");
        String moscowPriceMax = tariffsPage.getTotalPrice();

        logger.info("Проверяем на соответствие цены максимальных пакетов в Москве и Краснодаре");
        assertEquals(krasnodarPriceMax,moscowPriceMax);

    }

    @Test
    public void disabledButtonTest(){
        TinkoffMobilePage tariffsPage = new TinkoffMobilePage(driver);
        tariffsPage.open()
                .chooseEmptyPackage();

        logger.info("Проверяем, что кнопка отправки формы неактивна");
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
