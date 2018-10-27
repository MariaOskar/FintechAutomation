package ru.oskerko.imported;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import ru.oskerko.BaseRunner;

public class JobFormTests extends BaseRunner {


    @Test
    public void testRequiredFieldsMessages() {
        driver.get(baseUrl);
        driver.findElement(By.name("fio")).click();
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("city")).click();
        driver.findElement(By.cssSelector("div.SelectItem__contentWrapper_3eEeN")).click();
        driver.findElement(By.cssSelector("div.SelectItem__contentWrapper_3eEeN")).click();
        driver.findElement(By.cssSelector(".Scene__form_3ubwk")).click();

        assertEquals("Поле обязательное",
                driver.findElement(By.cssSelector(".Row__row_AjrJL:nth-child(1)  .Error__errorMessage_q8BBY")).getText());
        assertEquals("Поле обязательное",
                driver.findElement(By.cssSelector(".Row__row_AjrJL:nth-child(2) div:nth-child(1) .Error__errorMessage_q8BBY")).getText());
        assertEquals("Необходимо указать номер телефона",
                driver.findElement(By.cssSelector(".Row__row_AjrJL:nth-child(2) div:nth-child(2) .Error__errorMessage_q8BBY")).getText());
        assertEquals("Поле обязательное",
                driver.findElement(By.cssSelector(".Row__row_AjrJL:nth-child(3)  .Error__errorMessage_q8BBY")).getText());
        assertEquals("Поле обязательное",
                driver.findElement(By.cssSelector(".Row__row_AjrJL:nth-child(4)  .Error__errorMessage_q8BBY")).getText());
    }

    @Test
    public void testInvalidDataMessages() {
        driver.get(baseUrl);
        driver.findElement(By.name("fio")).click();
        driver.findElement(By.name("fio")).clear();
        driver.findElement(By.name("fio")).sendKeys("#$#$#$");
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("@#$%$#");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("123");
        driver.findElement(By.name("city")).click();
        driver.findElement(By.name("city")).clear();
        driver.findElement(By.name("city")).sendKeys("@#$%$",Keys.ENTER);
        assertEquals("Недостаточно информации. Введите фамилию, имя и отчество через пробел (Например: Иванов Иван Алексеевич)",
                driver.findElement(By.cssSelector(".Row__row_AjrJL:nth-child(1)  .Error__errorMessage_q8BBY")).getText());
        assertEquals("Введите корректный адрес эл. почты",
                driver.findElement(By.cssSelector(".Row__row_AjrJL:nth-child(2) div:nth-child(1) .Error__errorMessage_q8BBY")).getText());
        assertEquals("Код города/оператора должен начинаться с цифры 3, 4, 5, 6, 8, 9",
                driver.findElement(By.cssSelector(".Row__row_AjrJL:nth-child(2) div:nth-child(2) .Error__errorMessage_q8BBY")).getText());
        assertEquals("Допустимо использовать только буквы русского, латинского алфавита и дефис",
                driver.findElement(By.cssSelector(".Row__row_AjrJL:nth-child(3)  .Error__errorMessage_q8BBY")).getText());
    }



}
