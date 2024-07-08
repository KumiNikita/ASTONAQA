import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MtsByTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://mts.by");
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testBlockTitle() {
        WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'paymentBlockTitle')]")));
        assertEquals("Онлайн пополнение без комиссии", blockTitle.getText());
    }

    @Test
    public void testPaymentSystemLogos() {
        WebElement logoBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'paymentBlockLogos')]")));

        assertTrue(logoBlock.findElement(By.xpath(".//img[@alt='Visa']")).isDisplayed());
        assertTrue(logoBlock.findElement(By.xpath(".//img[@alt='MasterCard']")).isDisplayed());
    }

    @Test
    public void testMoreInfoLink() {
        WebElement moreInfoLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Подробнее о сервисе")));
        moreInfoLink.click();

        assertTrue(driver.getCurrentUrl().contains("expected_url_part")); // замените "expected_url_part" на часть URL, который вы ожидаете
    }

    @Test
    public void testServiceContinuation() {

        WebElement serviceOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Услуги связи']")));
        serviceOption.click();

        WebElement phoneNumberInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
        phoneNumberInput.sendKeys("297777777");

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Продолжить')]")));
        continueButton.click();


        WebElement nextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='nextStep']")));
        assertTrue(nextElement.isDisplayed());
    }

    @Test
    public void testEmptyFieldLabels() {
        String[] serviceOptions = {"Услуги связи", "Домашний интернет", "Рассрочка", "Задолженность"};
        String[] expectedLabels = {"Введите номер телефона", "Введите номер лицевого счета", "Введите номер договора", "Введите номер договора"};

        for (int i = 0; i < serviceOptions.length; i++) {
            WebElement serviceOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='" + serviceOptions[i] + "']")));
            serviceOption.click();
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@class, 'paymentInput')]")));
            assertEquals(expectedLabels[i], inputField.getAttribute("placeholder"));
        }
    }

    @Test
    public void testServiceContinuationAndValidation() {

        WebElement serviceOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Услуги связи']")));
        serviceOption.click();

        WebElement phoneNumberInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
        phoneNumberInput.sendKeys("297777777");

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Продолжить')]")));
        continueButton.click();


        WebElement nextStepBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'nextStepBlock')]")));
        WebElement phoneNumberConfirmation = nextStepBlock.findElement(By.xpath(".//span[contains(@class, 'phoneNumberConfirmation')]"));
        WebElement amountConfirmation = nextStepBlock.findElement(By.xpath(".//span[contains(@class, 'amountConfirmation')]"));
        WebElement cardNumberField = nextStepBlock.findElement(By.xpath(".//input[@name='cardNumber']"));
        WebElement cardDateField = nextStepBlock.findElement(By.xpath(".//input[@name='cardDate']"));
        WebElement cardCVVField = nextStepBlock.findElement(By.xpath(".//input[@name='cardCVV']"));
        WebElement payButton = nextStepBlock.findElement(By.xpath(".//button[contains(text(), 'Оплатить')]"));


        assertEquals("297777777", phoneNumberConfirmation.getText());
        assertTrue(amountConfirmation.getText().contains("expected_amount"));


        WebElement logoBlock = nextStepBlock.findElement(By.xpath("//div[contains(@class, 'paymentLogos')]"));
        assertTrue(logoBlock.findElement(By.xpath(".//img[@alt='Visa']")).isDisplayed());
        assertTrue(logoBlock.findElement(By.xpath(".//img[@alt='MasterCard']")).isDisplayed());


        assertEquals("Введите номер карты", cardNumberField.getAttribute("placeholder"));
        assertEquals("MM/YY", cardDateField.getAttribute("placeholder"));
        assertEquals("CVV", cardCVVField.getAttribute("placeholder"));


        assertTrue(payButton.isDisplayed());
        assertTrue(payButton.getText().contains("Оплатить"));
    }
}
