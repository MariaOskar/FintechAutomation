package ru.oskerko.handwritten;

import org.junit.Test;
import org.openqa.selenium.By;
import ru.oskerko.BaseRunner;
import ru.oskerko.handwritten.sections.FormSection;

import static org.junit.Assert.assertEquals;

public class JobFormTests extends BaseRunner {

    private static final String FIO_ERROR_MESSAGE = "Недостаточно информации. Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)";
    private static final String EMAIL_ERROR_MESSAGE = "Введите корректный адрес эл. почты";
    private static final String PHONE_ERROR_MESSAGE = "Код города/оператора должен начинаться с цифры 3, 4, 5, 6, 8, 9";
    private static final String CITY_ERROR_MESSAGE = "Допустимо использовать только буквы русского, латинского алфавита и дефис";
    private static final String REQUIRED_FIELD_MESSAGE = "Поле обязательное";
    private static final String REQUIRED_PHONE_MESSAGE = "Необходимо указать номер телефона";


    @Test
    public void testRequiredFieldsMessages() {
        driver.get(baseUrl);
        FormSection form = new FormSection(driver);
        form.fioField.click();
        form.emailField.click();
        form.phoneField.click();
        form.cityField.click();
        form.jobSelect.click();
        driver.findElement(By.cssSelector(".Scene__form_3ubwk")).click();

        assertEquals(REQUIRED_FIELD_MESSAGE,form.getFioErrorText() );
        assertEquals(REQUIRED_FIELD_MESSAGE,form.getEmailErrorText());
        assertEquals(REQUIRED_PHONE_MESSAGE, form.getPhoneErrorText());
        assertEquals(REQUIRED_FIELD_MESSAGE, form.getCityErrorText());
        assertEquals(REQUIRED_FIELD_MESSAGE,form.getJobErrorText());
    }

    @Test
    public void testInvalidDataMessages() {
        driver.get(baseUrl);
        FormSection form = new FormSection(driver);
        form
                .fillFIO("#$#$#$")
                .fillEmail("#$#$#$")
                .fillPhone("123")
                .fillCity("@#$%$#");

        assertEquals(FIO_ERROR_MESSAGE, form.getFioErrorText());
        assertEquals(EMAIL_ERROR_MESSAGE, form.getEmailErrorText());
        assertEquals(PHONE_ERROR_MESSAGE, form.getPhoneErrorText());
        assertEquals(CITY_ERROR_MESSAGE, form.getCityErrorText());
    }


}
