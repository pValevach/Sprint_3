package rest;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.Orders;
import pojo.OrdersList;

public class OrdersClient extends RestClient {

    private final String ROOT = "/orders";

    @Step("Send POST request to api/v1/orders and save response")
    public ValidatableResponse create(Orders orders) {
        return reqSpec
                .body(orders).log().all()
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Send GET request to api/v1/orders and save response")
    public ValidatableResponse getListOf(OrdersList ordersList) {
        return reqSpec
                .body(ordersList)
                .when()
                .get(ROOT)
                .then().log().all();
    }

}
