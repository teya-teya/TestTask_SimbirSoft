package utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WaitHelper {

    @Step("Ждем пока элемент станет видимым")
    public static void waitForVisible(WebDriverWait wait, WebElement element) {
        wait.until(d -> element.isDisplayed());
    }

    @Step("Ждем пока элемент станет кликабельным")
    public static void waitForClickable(WebDriverWait wait, WebElement element) {
        wait.until(d -> element.isDisplayed() && element.isEnabled());
    }

    @Step("Ждем пока список элементов станет видимым")
    public static void waitForElementsVisible(WebDriverWait wait, List<WebElement> elements) {
        wait.until(d -> !elements.isEmpty() && elements.get(0).isDisplayed());
    }
}
