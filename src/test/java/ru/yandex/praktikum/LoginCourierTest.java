package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.util.CourierCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @Description("Login a courier successfully")
    @DisplayName("Login a courier successfully")
    public void courierLoginTest() {
        courierClient.createCourier(courier);
        int statusCode = courierClient.loginCourier(CourierCredentials.from(courier)).extract().statusCode();
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().path("id");

        assertThat("Неверный код статуса", statusCode, equalTo(200));
        assertThat("Неверный ID курьера", courierId, notNullValue());
    }
}
