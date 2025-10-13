package utils;

import net.datafaker.Faker;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Locale;

public class RandomUtils {
    private static final Faker faker = new Faker(new Locale("en"));

    public static String fullName() {
        return faker.name().fullName();
    }

    public static String password() {
        return faker.internet().password(12, 18, true, true, true);
    }

    public static String email() {
        return faker.internet().emailAddress();
    }

    public static WebElement randomElement(List<WebElement> elements) {
        return faker.options().nextElement(elements);
    }
}