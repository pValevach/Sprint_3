package rest;

import org.junit.Before;
import org.junit.Test;
import pojo.OrdersList;
import java.util.List;
import static org.junit.Assert.assertNotNull;

public class OrdersListTest {

    private OrdersClient ordersClient;

    @Before
    public void setup() {
        ordersClient = new OrdersClient();
    }

    @Test
    public void getOrderListCountNotNull() {
        OrdersList ordersList = new OrdersList();

        List<Object[]> count = ordersClient.getListOf(ordersList)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("orders");

        assertNotNull(count);
    }
}
