import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String generateDate(int daysToAdd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate deliveryDate = currentDate.plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String deliveryDateStr = deliveryDate.format(formatter);
        return deliveryDateStr;
    }

    @Test
    void shouldDeliveryCard() {
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Ростов-на-Дону");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(4));
        $("[data-test-id=name] input").setValue("Федорова Алла-Светлана");
        $("[data-test-id=phone] input").setValue("+79013749921");
        $(withText("Успешно")).shouldBe(Condition.hidden);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(13));
        $(withText("Успешно")).shouldBe(Condition.visible);
    }
}
