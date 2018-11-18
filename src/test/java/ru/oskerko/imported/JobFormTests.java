package ru.oskerko.imported;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.oskerko.BaseRunner;
import ru.oskerko.pages.TinkoffJobsPage;

public class JobFormTests extends BaseRunner {
    Logger logger = LoggerFactory.getLogger(JobFormTests.class);
    private static final String REQUIRED_FIELD_MESSAGE = "Поле обязательное";
    private static final String REQUIRED_PHONE_MESSAGE = "Необходимо указать номер телефона";
    private static final String FIO_ERROR_MESSAGE = "Недостаточно информации. Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)";
    private static final String EMAIL_ERROR_MESSAGE = "Введите корректный адрес эл. почты";
    private static final String PHONE_ERROR_MESSAGE = "Код города/оператора должен начинаться с цифры 3, 4, 5, 6, 8, 9";
    private static final String CITY_ERROR_MESSAGE = "Допустимо использовать только буквы русского, латинского алфавита и дефис";

    @Test
    public void testRequiredFieldsMessages() {
        TinkoffJobsPage jobsPage = new TinkoffJobsPage(driver);
        jobsPage.open()
                .touchFIO()
                .touchEmail()
                .touchPhone()
                .touchCity()
                .touchJobsField()
                .touchJobsField()
                .touchForm();

        logger.info("Проверяем наличие сообщения об обязательном заполении поля Ф.И.О.");
        assertEquals(REQUIRED_FIELD_MESSAGE, jobsPage.getFioErrorText() );
        logger.info("Проверяем наличие сообщения об обязательном заполении поля Email");
        assertEquals(REQUIRED_FIELD_MESSAGE, jobsPage.getEmailErrorText());
        logger.info("Проверяем наличие сообщения об обязательном заполении поля Телефон");
        assertEquals(REQUIRED_PHONE_MESSAGE, jobsPage.getPhoneErrorText());
        logger.info("Проверяем наличие сообщения об обязательном заполении поля Город");
        assertEquals(REQUIRED_FIELD_MESSAGE, jobsPage.getCityErrorText());
        logger.info("Проверяем наличие сообщения об обязательном заполении поля Вакансия");
        assertEquals(REQUIRED_FIELD_MESSAGE, jobsPage.getJobErrorText());
    }

    @Test
    public void testInvalidDataMessages() {

        TinkoffJobsPage jobsPage = new TinkoffJobsPage(driver);
        jobsPage.open()
                .fillFIO("#$#$#$")
                .fillEmail("#$#$#$")
                .fillPhone("123")
                .fillCity("@#$%$#")
                .touchForm();

        logger.info("Проверяем сообщение об ошибке поля Ф.И.О.");
        assertEquals(FIO_ERROR_MESSAGE, jobsPage.getFioErrorText());
        logger.info("Проверяем сообщение об ошибке поля Email");
        assertEquals(EMAIL_ERROR_MESSAGE, jobsPage.getEmailErrorText());
        logger.info("Проверяем сообщение об ошибке поля Телефон");
        assertEquals(PHONE_ERROR_MESSAGE, jobsPage.getPhoneErrorText());
        logger.info("Проверяем сообщение об ошибке поля Город");
        assertEquals(CITY_ERROR_MESSAGE, jobsPage.getCityErrorText());
    }
}
