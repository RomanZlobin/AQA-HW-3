import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestsCardApplication {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        ;
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @Test
    void happyPathTest() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Тест Тестов-Перетестов");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79131234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void badNameTest() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Test Testov");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79131234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("span.input_invalid span.input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void badPhoneTest() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Тест Тестов");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7913123456");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("span.input_invalid span.input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void withoutAgreementTest() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Тест Тестов");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7913123456");
        driver.findElement(By.className("button")).click();
        assertNotNull(driver.findElement(By.className("input_invalid")).getText());
    }

    @Test
    void EmptyTest() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("span.input_invalid span.input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    void withoutNameTest() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("span.input_invalid span.input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void withoutPhoneTest() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Тестов Тест");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("span.input_invalid span.input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void badNameAndPhoneWithAgreementTest() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Test Testov");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7123456789");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("span.input_invalid span.input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void badNameAndPhoneWithoutAgreementTest() {
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Test Testov");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7123456789");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("span.input_invalid span.input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }


    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
}
