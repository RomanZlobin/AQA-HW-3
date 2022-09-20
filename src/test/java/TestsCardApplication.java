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
        WebDriverManager.chromedriver().setup();;
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @Test
    void happyPathTest() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Тест Тестов");
        elements.get(1).sendKeys("+79131234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void badNameTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Test Testov");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79131234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertNotNull(driver.findElement(By.className("input_invalid")).getText());
    }

    @Test
    void badPhoneTest(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Тест Тестов");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7913123456");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertNotNull(driver.findElement(By.className("input_invalid")).getText());
    }

    @Test
    void withoutAgreementTest(){
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Тест Тестов");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+7913123456");
        driver.findElement(By.className("button")).click();
        assertNotNull(driver.findElement(By.className("input_invalid")).getText());
    }


    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
}
