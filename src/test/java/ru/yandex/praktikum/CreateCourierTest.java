package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.util.CourierCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {
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
    @Description("Create a courier successfully")
    @DisplayName("Create a courier successfully")
    public void canCreateCourierTest() {
        ValidatableResponse response = courierClient.createCourier(courier);
        boolean isCourierCreated = response.extract().path("ok");
        int statusCode = response.extract().statusCode();
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().path("id");

        assertTrue("Курьер не создан", isCourierCreated);
        assertThat("Неверный ID курьера", courierId, notNullValue());
        assertThat("Неверный код статуса", statusCode, equalTo(201));
    }

    @Test
    @Description("Create an identical courier")
    @DisplayName("Create an identical courier")
    public void shouldNotCreateIdenticalCourierTest() {
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().path("id");
        int statusCode = courierClient.createCourier(courier).extract().statusCode();
        boolean isCourierIdentical = courierClient.createCourier(courier).extract().path("message").toString().contains("Этот логин уже используется");

        assertTrue("Создано два одинаковых курьера", isCourierIdentical);
        assertThat("Неверный код статуса", statusCode, equalTo(409));
    }
}