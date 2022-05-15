package rest;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.Orders;

import static org.apache.http.HttpStatus.*;

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
                .statusCode(SC_CREATED)
                .extract()
                .path("track");

        assertNotEquals(0, orderTrack);
    }
}

