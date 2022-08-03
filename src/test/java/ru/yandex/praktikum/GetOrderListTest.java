package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.OrdersClient;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class GetOrderListTest {
    private OrdersClient ordersClient;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Get a list of orders")
    @Description("Get a list of orders")
    public void getOrdersListTest() {
        ValidatableResponse response = ordersClient.getOrderList();
        int statusCode = response.extract().statusCode();

        assertEquals("Неверный код статуса", 200, statusCode);
        assertThat("Список заказов пустой", response.extract().path("orders"), notNullValue());
    }
}
