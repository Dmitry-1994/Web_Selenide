package ru.netology.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Keys;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardNegativeTest {
    private String getDate(int countDay, String format) {
        return LocalDate.now().plusDays(countDay).format(DateTimeFormatter.ofPattern(format));
    }

    @BeforeEach
    void startSetup() {
        //Configuration.holdBrowserOpen = true;
    }

    @Test
    void emptyFieldAll() {
        open("http://localhost:9999/");
        $(".button__text").click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void emptyFieldCity() {
        String currentDay = getDate(1, "dd.MM.yyy");
        open("http://localhost:9999/");
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(currentDay);
        $("[data-test-id=name] input").setValue("Дмитрий Тарасов");
        $("[data-test-id=phone] input").setValue("+79122518775");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvSource({
            "Ekaterinburg",
            "Екатеринбург_Tarasov",
            "!Екатеринбург",
            "Екатеринбург007"
    })
    void invalidCity(String city) {
        String currentDay = getDate(1, "dd.MM.yyy");
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(currentDay);
        $("[data-test-id=name] input").setValue("Дмитрий Тарасов");
        $("[data-test-id=phone] input").setValue("+79122518775");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @ParameterizedTest
    @CsvSource({
            "''",
            "00.01.2024",
            "32.01.2024",
            "30.02.2024",
            "31.00.2024",
            "31.13.2024"
    })
    void incorrectData(String data) {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(data);
        $("[data-test-id=name] input").setValue("Дмитрий Тарасов");
        $("[data-test-id=phone] input").setValue("+79122518775");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id=date] .input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Неверно введена дата"));
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "2",
            "-1",
            "-365"
    })
    void invalidData(int data) {
        String currentDay = getDate(data, "dd.MM.yyy");
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(currentDay);
        $("[data-test-id=name] input").setValue("Дмитрий Тарасов");
        $("[data-test-id=phone] input").setValue("+79122518775");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id=date] .input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void emptyFieldName() {
        String currentDay = getDate(4, "dd.MM.yyy");
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(currentDay);
        $("[data-test-id=phone] input").setValue("+79122518775");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvSource({
            "Dima",
            "Дмитрий_Tarasov",
            "!Дмитрий",
            "Дмитрий007"
    })
    void invalidName(String name) {
        String currentDay = getDate(4, "dd.MM.yyy");
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(currentDay);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue("+79122518775");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Имя и Фамилия указаные неверно"));
    }

    @Test
    void emptyFieldPhone() {
        String currentDay = getDate(4, "dd.MM.yyy");
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(currentDay);
        $("[data-test-id=name] input").setValue("Дмитрий Тарасов");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvSource({
            "+791225187755",
            "+7912251877",
            "+7",
            "+",
            "89122518775",
            "@79122518775",
            "Дмитрий"
    })
    void invalidPhone(String phone) {
        String currentDay = getDate(4, "dd.MM.yyy");
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(currentDay);
        $("[data-test-id=name] input").setValue("Дмитрий Тарасов");
        $("[data-test-id=phone] input").setValue(phone);
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldBe(visible)
                .shouldHave(text("Телефон указан неверно"));
    }

    @Test
    void emptyFieldAgree() {
        String currentDay = getDate(4, "dd.MM.yyy");
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(currentDay);
        $("[data-test-id=name] input").setValue("Дмитрий Тарасов");
        $("[data-test-id=phone] input").setValue("+79122518775");
        $(".button__text").click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
    }
}
