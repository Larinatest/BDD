package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    DashboardPage dashboardPage;
    DataHelper.CardInfo firstCardInfo;
    DataHelper.CardInfo secondCardInfo;
    int BalnceFirstCard;
    int BalanceSecondCard;

    @BeforeEach
    void setUp() {
        open("http:localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificatinPage = loginPage.validLogin(authInfo);
        var verificatinCode = DataHelper.getVerificatinCode(authInfo);
        dashboardPage = verificatinPage.validVerify(verificatinCode);
        firstCardInfo = DataHelper.getFirstCardInfo();
        secondCardInfo = DataHelper.getSecondCardInfo();
        BalnceFirstCard = dashboardPage.getCardBalance(0);
        BalanceSecondCard = dashboardPage.getCardBalance(1);
    }

    @Test
    void efficientTransferBetweenCards() {
        var amount = DataHelper.generateValidAmount(BalnceFirstCard);
        var expectedBalanceFirstCard = BalnceFirstCard - amount;
        var expectedBalanceSecondCard = BalanceSecondCard + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        dashboardPage.reloadDashboardPage();
        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard),
                () -> assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard));
    }

    @Test
    void invalidTransferBetweenCards() {
        var amount = DataHelper.generateInvalidAmount(BalanceSecondCard);
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
        transferPage.error("Ошибка");
    }

    @Test
    void invalidLogin() {
        open("http:localhost:9999");
        var loginPage = new LoginPage();
        loginPage.invalidUser(DataHelper.getRandomLogin());
        loginPage.errorMessage("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void invalidPassword() {
        open("http:localhost:9999");
        var loginPage = new LoginPage();
        loginPage.invalidUser(DataHelper.getRandomPassword());
        loginPage.errorMessage("Ошибка! Неверно указан логин или пароль");
    }

}