package rest;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pojo.OrdersList;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.apache.http.HttpStatus.*;

public class OrdersListTest {

    private OrdersClient ordersClient;

    @Before
    public void setup() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Can get orders list")
    public void getOrderListCountNotNull() {
        OrdersList ordersList = new OrdersList();

        List<Object[]> count = ordersClient.getListOf(ordersList)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("orders");

        assertNotNull(count);
    }
}
