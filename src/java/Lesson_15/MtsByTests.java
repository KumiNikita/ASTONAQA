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
        // Проверяем наличие логотипов (например, Visa и MasterCard)
        assertTrue(logoBlock.findElement(By.xpath(".//img[@alt='Visa']")).isDisplayed());
        assertTrue(logoBlock.findElement(By.xpath(".//img[@alt='MasterCard']")).isDisplayed());
    }

    @Test
    public void testMoreInfoLink() {
        WebElement moreInfoLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Подробнее о сервисе")));
        moreInfoLink.click();

        assertTrue(driver.getCurrentUrl().contains("expected_url_part"));
    }

    @Test
    public void testServiceContinuation() {

        WebElement serviceOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Услуги связи']")));
        serviceOption.click();

        WebElement phoneNumberInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phoneNumber")));
        phoneNumberInput.sendKeys("297777777");

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Продолжить')]")));
        continueButton.click();


        WebElement nextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='nextStep']"))); // замените на реальный локатор
        assertTrue(nextElement.isDisplayed());
    }
}
