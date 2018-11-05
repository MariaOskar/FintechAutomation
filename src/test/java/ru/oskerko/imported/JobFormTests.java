package ru.oskerko.imported;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import ru.oskerko.BaseRunner;

public class JobFormTests extends BaseRunner {


    @Test
    public void testRequiredFieldsMessages() {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//*[@name='fio']")).click();
        driver.findElement(By.xpath("//*[@name='email']")).click();
        driver.findElement(By.xpath("//*[@name='phone']")).click();
        driver.findElement(By.xpath("//*[@name='city']")).click();
        driver.findElement(By.xpath("//div[@class='SelectItem__contentWrapper_3eEeN']")).click();
        driver.findElement(By.xpath("//div[@class='SelectItem__contentWrapper_3eEeN']")).click();
        driver.findElement(By.xpath("//*[@class='Scene__form_3ubwk']")).click();

        assertEquals("Поле обязательное",
                driver.findElement(By.xpath("//*[@class='Row__row_AjrJL'][1]//*[@class='Error__errorMessage_q8BBY']")).getText());
        assertEquals("Поле обязательное",
                driver.findElement(By.xpath("//*[@class='Row__row_AjrJL'][2]//div[1]//*[@class='Error__errorMessage_q8BBY']")).getText());
        assertEquals("Необходимо указать номер телефона",
                driver.findElement(By.xpath("//*[@class='Row__row_AjrJL'][2]//div[2]//*[@class='Error__errorMessage_q8BBY']")).getText());
        assertEquals("Поле обязательное",
                driver.findElement(By.xpath("//*[@class='Row__row_AjrJL'][3]//*[@class='Error__errorMessage_q8BBY']")).getText());
        assertEquals("Поле обязательное",
                driver.findElement(By.xpath("//*[@class='Row__row_AjrJL'][4]//*[@class='Error__errorMessage_q8BBY']")).getText());
    }

    @Test
    public void testInvalidDataMessages() {
        driver.get(baseUrl);
        driver.findElement(By.cssSelector("[name='fio']")).click();
        driver.findElement(By.cssSelector("[name='fio']")).clear();
        driver.findElement(By.cssSelector("[name='fio']")).sendKeys("#$#$#$");
        driver.findElement(By.cssSelector("[name='email']")).click();
        driver.findElement(By.cssSelector("[name='email']")).clear();
        driver.findElement(By.cssSelector("[name='email']")).sendKeys("@#$%$#");
        driver.findElement(By.cssSelector("[name='phone']")).click();
        driver.findElement(By.cssSelector("[name='phone']")).clear();
        driver.findElement(By.cssSelector("[name='phone']")).sendKeys("123");
        driver.findElement(By.cssSelector("[name='city']")).click();
        driver.findElement(By.cssSelector("[name='city']")).clear();
        driver.findElement(By.cssSelector("[name='city']")).sendKeys("@#$%$",Keys.ENTER);
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
