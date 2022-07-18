package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.model.Orders;

import static io.restassured.RestAssured.given;

public class OrdersClient extends RestAssuredClient {
    private static final String ORDERS_PATH = "api/v1/orders";
    private static final String ORDERS_PATH_WITH_TRACK = "/api/v1/orders/track?t=";

    @Step("Create an order")
    public ValidatableResponse createOrder(Orders orders) {
        return given()
                .spec(getBaseSpec())
                .body(orders)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Get a list of orders")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH)
                .then();
    }

    @Step("Get a list of orders with track number")
    public ValidatableResponse getOrderListWithTrack(int track) {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH_WITH_TRACK + track)
                .then();
    }

    @Step("Cancel an order")
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .spec(getBaseSpec())
                .body(track)
                .when()
                .put(ORDERS_PATH + "cancel")
                .then();
    }
}
