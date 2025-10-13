package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.RandomUtils;
import utils.WaitHelper;

import java.util.Comparator;
import java.util.List;

public class FormFieldsPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public FormFieldsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "name-input")
    public WebElement fldName;

    @FindBy(xpath = "//label[text()='Password ']/input")
    public WebElement fldPassword;

    @FindBy(css = "input[type='checkbox']")
    public List<WebElement> cbxsDrinks;

    @FindBy(xpath = "//input[@type='radio']")
    public List<WebElement> radioBtnsColors;

    @FindBy(id = "automation")
    public WebElement fldDoYouLike;

    @FindBy(xpath = "//select/option[position() > 1]")
    public List<WebElement>  optionsInDpdDoYouLike;

    @FindBy(id = "email")
    public WebElement fldEmail;

    @FindBy(id = "message")
    public WebElement fldMessage;

    @FindBy(id = "submit-btn")
    public WebElement btnSubmit;

    @FindBy(xpath = "//label[text()='Automation tools']/following-sibling::ul/li")
    public List<WebElement> tools;

    @Step("Заполняем поле {nameInput} текстом '{text}'")
    public FormFieldsPage fillInput(WebElement input, String nameInput, String text) {
        WaitHelper.waitForVisible(wait, input);
        input.clear();
        input.sendKeys(text);
        return this;
    }

    @Step("Выбираем напиток '{drink}'")
    public FormFieldsPage selectDrink(String drink) {
        WaitHelper.waitForElementsVisible(wait, cbxsDrinks);

        WebElement cbxDrink = cbxsDrinks.stream()
                .filter(checkbox -> drink.equals(checkbox.getAttribute("value")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Напиток '" + drink + "' не найден"));

        cbxDrink.click();
        return this;
    }

    @Step("Выбираем цвет '{color}'")
    public FormFieldsPage selectColor(String color) {
        WaitHelper.waitForElementsVisible(wait, radioBtnsColors);

        WebElement radioBtnColors = radioBtnsColors.stream()
                .filter(radio -> color.equals(radio.getAttribute("value")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Цвет '" + color + "' не найден"));

        radioBtnColors.click();
        return this;
    }

    @Step("Нажимаем на поле 'Do you like automation?' и выбираем в дропдауне случайный вариант")
    public FormFieldsPage selectRandomAutomationOption() {
        WaitHelper.waitForVisible(wait, fldDoYouLike);
        actions.moveToElement(fldDoYouLike).click().perform();
        WaitHelper.waitForElementsVisible(wait, optionsInDpdDoYouLike);
        WebElement randomOption = RandomUtils.randomElement(optionsInDpdDoYouLike);
        WaitHelper.waitForClickable(wait, randomOption);
        String selectedOption = randomOption.getText();

        randomOption.click();
        Allure.step("Выбрана опция: " + selectedOption);
        return this;
    }

    public String createMessage() {
        if (tools.isEmpty()) {
            throw new RuntimeException("Список инструментов пуст");
        }

        int countTools = tools.size();
        String longestTool = tools.stream()
                .max(Comparator.comparingInt(tool -> tool.getText().length()))
                .map(WebElement::getText)
                .orElse("не определен");

        return String.format(
                "Всего инструментов автоматизации: %d. " +
                        "Самый длинный по названию: %s.",
                countTools, longestTool
        );
    }

    @Step("Кликаем кнопку 'Submit'")
    public void clickSubmit() {
        WaitHelper.waitForClickable(wait, btnSubmit);
        actions.moveToElement(btnSubmit).click().perform();
    }
}
