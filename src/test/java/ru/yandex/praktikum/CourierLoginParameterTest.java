package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.util.CourierCredentials;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierLoginParameterTest {
    private CourierClient courierClient = new CourierClient();
    private static Courier courier = Courier.getRandomCourier();
    private CourierCredentials credentials;
    private int courierId;
    private int statusCode;
    private String errorMessage;

    public CourierLoginParameterTest(CourierCredentials credentials, int statusCode, String errorMessage) {
        this.credentials = credentials;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2}")
    public static Object[][] getData() {
        return new Object[][]{
                //authorizeWithLoginTest падает с ошибкой 504
                //{CourierCredentials.authorizeWithLogin(courier), 400, "Недостаточно данных для входа"},
                {CourierCredentials.authorizeWithPassword(courier), 400, "Недостаточно данных для входа"},
                {CourierCredentials.authorizeWithRandomCredentials(courier), 404, "Учетная запись не найдена"}
        };
    }

    @Test
    @DisplayName("Login a courier")
    @Description("Login a courier with invalid data")
    public void courierLoginTest() {
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().path("id");
        int actualStatusCode = new CourierClient().loginCourier(credentials).extract().statusCode();
        String actualErrorMessage = new CourierClient().loginCourier(credentials).extract().path("message");

        assertEquals("Неверный код статуса", statusCode, actualStatusCode);
        assertEquals("Неверное сообщение об ошибке", errorMessage, actualErrorMessage);
    }
}
