import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Tests {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\NETOLOGY\\chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
//        Не могу понять смысл добавления нижестоящего кода? С ним даже как-то кривее работает.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    @DisplayName("Заявка успешно заполняется и отправляется")
    void shouldTestWithNormalData() throws InterruptedException {
        driver.get("http://localhost:7777");
        Thread.sleep(1000);
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Якимов Дмитрий");
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Заявка не заполняется с неверными данными в поле Фамилия и Имя")
    void shouldTestIfWrongName() throws InterruptedException {
        driver.get("http://localhost:7777");
        Thread.sleep(1000);
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Iachimov Dmitri");
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        Thread.sleep(1000);
    }
    @Test
    @DisplayName("Заявка не заполняется с неверными данными в поле Телефон")
    void shouldTestIfWrongNumber() throws InterruptedException {
        driver.get("http://localhost:7777");
        Thread.sleep(1000);
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Якимов Дмитрий");
        elements.get(1).sendKeys("321313131313331");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("#root > div > form > div:nth-child(2) > span > span > span.input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        Thread.sleep(1000);
    }
}
