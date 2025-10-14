package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;

@ExtendWith(BaseTest.ScreenshotTestWatcher.class)
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    public static class ScreenshotTestWatcher implements TestWatcher {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof BaseTest baseTest) {
                baseTest.takeScreenshot("Скриншот при ошибке");
                baseTest.closeDriver();
            }
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof BaseTest baseTest) {
                baseTest.closeDriver();
            }
        }
    }

    protected void takeScreenshot(String screenshotName) {
        if (driver != null && driver instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
        }
    }

    protected void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
