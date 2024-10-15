package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TrabsferPage {
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMessage = $("[data-test-id='error-notification']");


    public TrabsferPage() {
        transferHead.shouldBe(visible);
    }

    public void makeTransfer(String sum, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(sum);
        fromInput.setValue(cardInfo.getNumberCard());
        transferButton.click();
    }

    public DashboardPage makeValidTransfer(String sum, DataHelper.CardInfo cardInfo) {
        makeTransfer(sum, cardInfo);
        return new DashboardPage();
    }

    public void error(String expectedText) {
        errorMessage.shouldHave(Condition.exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}