package tests;

import base.BaseTest;
import base.WebChecks;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.FormFieldsPage;
import utils.RandomUtils;

import static io.qameta.allure.Allure.step;

public class FormFieldsTest extends BaseTest {
    WebChecks webChecks;
    FormFieldsPage formPage;

    @Test
    @AllureId("TC-001")
    @DisplayName("Работа с полями и формами")
    void testFormFieldsSubmission() {
        step("Предусловие", () -> {
            driver.get("https://practice-automation.com/form-fields/");
            webChecks = new WebChecks(driver, wait);
            formPage = new FormFieldsPage(driver, wait);
        });

        formPage.fillInput(formPage.fldName, "Name", RandomUtils.fullName())
                .fillInput(formPage.fldPassword, "Password", RandomUtils.password())
                .selectDrink("Milk")
                .selectDrink("Coffee")
                .selectColor("Yellow")
                .selectRandomAutomationOption()
                .fillInput(formPage.fldEmail, "Email", RandomUtils.email())
                .fillInput(formPage.fldMessage, "Message", formPage.createMessage())
                .clickSubmit();

        webChecks.checkAlertTextWithoutClose("Message received!");
    }

    @Test
    @AllureId("TC-002")
    @DisplayName("Проверка отправки формы с заполнением только обязательного поля 'Name'")
    void testSubmitFormWithRequiredNameOnly() {
        step("Предусловие", () -> {
            driver.get("https://practice-automation.com/form-fields/");
            webChecks = new WebChecks(driver, wait);
            formPage = new FormFieldsPage(driver, wait);
        });

        formPage.fillInput(formPage.fldName, "Name", RandomUtils.fullName())
                .clickSubmit();

        webChecks.checkAlertTextWithoutClose("Message received!");
    }

    @Test
    @AllureId("TC-003")
    @DisplayName("Проверка отправки формы с пустыми полями")
    void testSubmitFormWithEmptyRequiredFields() {
        step("Предусловие", () -> {
            driver.get("https://practice-automation.com/form-fields/");
            webChecks = new WebChecks(driver, wait);
            formPage = new FormFieldsPage(driver, wait);
        });

        formPage.clickSubmit();

        webChecks.verifyPageScrolledToTop();
        webChecks.checkElementIsFocused(formPage.fldName, "поле 'Name'");
    }
}
