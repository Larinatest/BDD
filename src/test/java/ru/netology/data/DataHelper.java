package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    public static String getGenerateLogin(String local) {
        Faker faker = new Faker(new Locale(local));
        return faker.name().fullName();
    }

    public static String getGeneratePassword(String local) {
        Faker faker = new Faker(new Locale(local));
        return faker.internet().password();
    }

    public static int generateValidAmount(int balance) {
        return new Random().nextInt(Math.abs(balance)) + 1; // генерирует рандомное число баланса карты
    }

    public static int generateInvalidAmount(int balance) {
        return Math.abs(balance) + new Random().nextInt(10000) + 1;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getRandomLogin() {
        return new AuthInfo(getGenerateLogin("en"), getAuthInfo().getPassword());
    }

    public static AuthInfo getRandomPassword() {
        return new AuthInfo(getAuthInfo().getLogin(), getGeneratePassword("en"));
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    @Value
    public static class CardInfo {
        private String numberCard;
        private String testId;
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificatinCode {
        private String code;
    }

    public static VerificatinCode getVerificatinCode(AuthInfo authInfo) {
        return new VerificatinCode("12345");
    }

}