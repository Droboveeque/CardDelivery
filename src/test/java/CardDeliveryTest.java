import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryTest {

    private String validDate(int addDays) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    }

    @Test
    void shouldOrderCard() {
        String data = validDate(4);
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Владивосток");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $("[data-test-id='date'] input").setValue(data);
        $("[data-test-id='name'] input").setValue("Петров Петя");
        $("[data-test-id='phone'] input").setValue("+71220000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + data));
    }
}
