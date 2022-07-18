package ru.yandex.praktikum.model;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;

public class Orders {
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<String> colors;
    private static Random random = new Random();

    public Orders() {
    }

    public Orders(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> colors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = colors;
    }

    public static Orders getRandomOrder(List<String> color) {
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        final String address = RandomStringUtils.randomAlphabetic(10);
        final String metroStation = RandomStringUtils.randomAlphabetic(10);
        final String phone = RandomStringUtils.randomAlphabetic(10);
        final int rentTime = random.nextInt();
        final String deliveryDate = RandomStringUtils.randomAlphabetic(10);
        final String comment = RandomStringUtils.randomAlphabetic(10);
        final List<String> colors = color;
        return new Orders(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, colors);
    }
}
