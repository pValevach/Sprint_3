package rest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.Courier;
import pojo.Orders;

import java.util.*;

import static org.junit.Assert.assertNotEquals;


@RunWith(Parameterized.class)
public class OrdersTest {

    private final OrdersClient ordersClient = new OrdersClient();

    private final String[] color;

    public OrdersTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Different colors")
    public static List<String[][]> getDifferentColors() {
        return Arrays.asList(new String[][][]{
                {{"Black"}},
                {{"Grey"}},
                {{"Black", "Grey"}},
                {{}}
        });
    }

    @Test
    @DisplayName("Can create order")
    public void canCreateOrder() {
        Orders orders = Orders.getDefault();
        orders.setColor(color);

        int orderTrack = ordersClient.create(orders)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");

        assertNotEquals(0, orderTrack);
    }
}

