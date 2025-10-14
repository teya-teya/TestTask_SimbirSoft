package base;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

public class WebChecks {
    public WebDriver driver;
    public WebDriverWait wait;

    public WebChecks(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Проверяем, что alert содержит текст '{expectedText}'")
    public void checkAlertTextWithoutClose(String expectedText) {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String actualText = alert.getText();

        Assertions.assertEquals(expectedText, actualText,
                "Текст в alert не совпадает. Ожидалось: " + expectedText + ", но было: " + actualText);
    }

    @Step("Проверяем что поле элемент '{nameElement}' в фокусе")
    public void checkElementIsFocused(WebElement element, String nameElement) {
        WaitHelper.waitForVisible(wait, element);

        WebElement activeElement = driver.switchTo().activeElement();
        Assertions.assertEquals(element, activeElement,
                "%s не в фокусе. Активный элемент: %s".formatted(nameElement, activeElement.getTagName()));
    }

    @Step("Проверяем что страница проскроллена к самому верху")
    public void verifyPageScrolledToTop() {
        wait.until(d -> {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                Long scrollY = (Long) js.executeScript("return window.pageYOffset;");
                Long scrollX = (Long) js.executeScript("return window.pageXOffset;");

                return scrollY <= 50 && scrollX == 0;
            } catch (Exception e) {
                return false;
            }
        });

        Allure.addAttachment("Скролл", "Страница проскроллена к верху");
    }
}
