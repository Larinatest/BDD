package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("[data-test-id='password'] input");
    private final SelenideElement buttonField = $("[data-test-id='action-login']");
    private final SelenideElement error = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        invalidUser(info);
        return new VerificationPage();
    }

    public void invalidUser(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        buttonField.click();
    }

    public void errorMessage(String expectedText) {
        error.shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
}
