package ru.netology.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardPositiveTest {
    @ParameterizedTest
    @CsvSource({
            "Екатеринбург, Дмитрий, +79122518775",
            "Москва, Дмитрий Тарасов, +12345678910",
            "Санкт-Петербург, Дмитрий-Тарасов, +00000000000",
            "Ростов-на-Дону, Дмитрий-Тарасов Алексеевич, +99999999999"
    })
    void shouldRegisterAccount(String city, String name, String phone) {
        //Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] [type=tel]").setValue("06.06.2023");
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $(".checkbox__box").click();
        $(".button__text").click();
        //$(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        //$x("//div[contains(text(), 'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification']").find(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }
}