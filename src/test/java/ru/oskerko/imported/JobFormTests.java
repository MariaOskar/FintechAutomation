package ru.oskerko.imported;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import ru.oskerko.BaseRunner;
import ru.oskerko.pages.TinkoffJobsPage;

public class JobFormTests extends BaseRunner {

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
                .touchForm()
        ;

        assertEquals(REQUIRED_FIELD_MESSAGE, jobsPage.getFioErrorText() );
        assertEquals(REQUIRED_FIELD_MESSAGE, jobsPage.getEmailErrorText());
        assertEquals(REQUIRED_PHONE_MESSAGE, jobsPage.getPhoneErrorText());
        assertEquals(REQUIRED_FIELD_MESSAGE, jobsPage.getCityErrorText());
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

        assertEquals(FIO_ERROR_MESSAGE, jobsPage.getFioErrorText());
        assertEquals(EMAIL_ERROR_MESSAGE, jobsPage.getEmailErrorText());
        assertEquals(PHONE_ERROR_MESSAGE, jobsPage.getPhoneErrorText());
        assertEquals(CITY_ERROR_MESSAGE, jobsPage.getCityErrorText());
    }
}
