package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.client.OrdersClient;
import ru.yandex.praktikum.model.Orders;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class OrderListParameterTest {
    private Orders orders = new Orders();
    private OrdersClient ordersClient;
    private int track;
    private static List<String> colors;
    private int expectedCode;

    public OrderListParameterTest(List<String> colors, int expectedCode) {
        this.colors = colors;
        this.expectedCode = expectedCode;
    }

    @Before
    public void setUp() {
        //orders = Orders.getRandomOrder(colors);
        ordersClient = new OrdersClient();
        Orders.getRandomOrder(colors);
    }

    @After
    public void tearDown() {
        ordersClient.cancelOrder(track);
    }

    @Parameterized.Parameters(name = "Тестовые данные:{0} {1}")
    public static Object[][] getColorData() {
        return new Object[][]{
                {List.of("BLACK"), 201},
                {List.of("GREY"), 201},
                {List.of("BLACK", "GREY"), 201},
                {List.of(), 201},
        };
    }

    @Test
    @DisplayName("Choose colors for an order")
    @Description("Choose colors for an order")
    public void shouldBeAbleToChooseColorTest() {
        ValidatableResponse response = ordersClient.createOrder(orders);
        int actualStatusCode = response.extract().statusCode();
        track = response.extract().path("track");

        assertThat("Неверный код статуса", actualStatusCode, equalTo(expectedCode));
        assertNotEquals("Неверный track", 0, track);
    }
}
