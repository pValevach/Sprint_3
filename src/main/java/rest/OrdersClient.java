package rest;

import io.restassured.response.ValidatableResponse;
import pojo.Courier;
import pojo.Orders;
import pojo.OrdersList;

public class OrdersClient extends RestClient {

    private final String ROOT = "/orders";

    public ValidatableResponse create(Orders orders) {
        return reqSpec
                .body(orders).log().all()
                .when()
                .post(ROOT)
                .then().log().all();
    }

    public ValidatableResponse getListOf(OrdersList ordersList) {
        return reqSpec
                .body(ordersList)
                .when()
                .get(ROOT)
                .then().log().all();
    }

}
