import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtsTests {
    private static WebDriver driver;

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://mts.by");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("Проверить надписи в незаполненных полях каждого варианта оплаты услуг")
    public void testEmptyFieldsLabels() {
        checkEmptyFieldLabel("Услуги связи");
        checkEmptyFieldLabel("Домашний интернет");
        checkEmptyFieldLabel("Рассрочка");
        checkEmptyFieldLabel("Задолженность");
    }

    @Step("Проверить надпись в незаполненном поле варианта {variant}")
    public void checkEmptyFieldLabel(String variant) {
        WebElement variantElement = driver.findElement(By.xpath("//label[contains(text(), '" + variant + "')]"));
        variantElement.click();
        WebElement field = driver.findElement(By.xpath("//input[@placeholder='Введите номер телефона']"));
        assertEquals("Введите номер телефона", field.getAttribute("placeholder"));
    }

    @Test
    @Description("Заполнить поля и проверить работу кнопки «Продолжить» для варианта «Услуги связи»")
    public void testSubmitServiceConnection() {
        fillServiceConnectionForm("297777777", "10");
        checkConfirmationDetails("297777777", "10");
    }

    @Step("Заполнить форму «Услуги связи» номером {phoneNumber} и суммой {amount}")
    public void fillServiceConnectionForm(String phoneNumber, String amount) {
        WebElement variantElement = driver.findElement(By.xpath("//label[contains(text(), 'Услуги связи')]"));
        variantElement.click();
        WebElement phoneField = driver.findElement(By.xpath("//input[@placeholder='Введите номер телефона']"));
        phoneField.sendKeys(phoneNumber);
        WebElement amountField = driver.findElement(By.xpath("//input[@placeholder='Сумма']"));
        amountField.sendKeys(amount);
        WebElement continueButton = driver.findElement(By.xpath("//button[contains(text(), 'Продолжить')]"));
        continueButton.click();
    }

    @Step("Проверить корректность отображения подтверждения с номером {phoneNumber} и суммой {amount}")
    public void checkConfirmationDetails(String phoneNumber, String amount) {
        WebElement confirmationPhone = driver.findElement(By.xpath("//div[contains(text(), '297777777')]"));
        WebElement confirmationAmount = driver.findElement(By.xpath("//div[contains(text(), '10')]"));
        assertEquals(phoneNumber, confirmationPhone.getText());
        assertEquals(amount, confirmationAmount.getText());
        WebElement paymentField = driver.findElement(By.xpath("//input[@placeholder='Введите данные карты']"));
        assertEquals("Введите данные карты", paymentField.getAttribute("placeholder"));
        WebElement paymentIcons = driver.findElement(By.xpath("//div[@class='payment-icons']"));
        assertTrue(paymentIcons.isDisplayed());
    }
}
