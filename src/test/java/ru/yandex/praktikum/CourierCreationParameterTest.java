package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierCreationParameterTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;
    private int statusCode;
    private String errorMessage;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    public CourierCreationParameterTest(Courier courier, int statusCode, String errorMessage) {
        this.courier = courier;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2}")
    public static Object[][] getData() {
        return new Object[][]{
                {Courier.getCourierWithLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithFirstNameOnly(), 400, "Недостаточно данных для создания учетной записи"},
        };
    }

    @Test
    @DisplayName("Create a courier")
    @Description("Create a courier with empty fields")
    public void checkFieldsValidationOfCourierCreationTest() {
        ValidatableResponse response = courierClient.createCourier(courier);
        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        assertEquals("Неверный код статуса", statusCode, actualStatusCode);
        assertEquals("Неверное сообщение об ошибке", errorMessage, actualMessage);
    }
}
