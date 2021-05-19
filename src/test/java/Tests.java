import io.github.bonigarcia.wdm.WebDriverManager;
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
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
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
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Якимов Дмитрий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("span[class=button__text]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Заявка не заполняется с неверными данными в поле Фамилия и Имя")
    void shouldTestIfWrongName() throws InterruptedException {
        driver.get("http://localhost:7777");
        Thread.sleep(1000);
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Iachimov Dmitri");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("span[class=button__text]")).click();
        String text = driver.findElement(By.cssSelector("span[class=input__sub]")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        Thread.sleep(1000);
    }
    @Test
    @DisplayName("Заявка не заполняется с неверными данными в поле Телефон")
    void shouldTestIfWrongNumber() throws InterruptedException {
        driver.get("http://localhost:7777");
        Thread.sleep(1000);
        WebElement form = driver.findElement(By.cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Якимов Дмитрий");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("3131313131311");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("span[class=button__text]")).click();
        String text = driver.findElement(By.cssSelector("#root > div > form > div:nth-child(2) > span > span > span.input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        Thread.sleep(1000);
    }
}
