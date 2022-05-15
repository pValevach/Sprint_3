package rest;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.Courier;
import pojo.Credentials;

import static org.apache.http.HttpStatus.*;

public class CourierClient extends RestClient {

    private final String ROOT = "/courier";
    private final String LOGIN = ROOT + "/login";
    private final String COURIER = ROOT + "/{courierId}";

    @Step("Send POST request to api/v1/courier and save response")
    public ValidatableResponse createCourier(Courier courier) {
        return reqSpec
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Send POST request to api/v1/courier/login and save response")
    public ValidatableResponse login(Credentials credentials) {
        return reqSpec
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Send DELETE request to api/v1/courier/{courierId}, save response and check that SC is 200")
    public void deleteCourier(int courierId) {
        reqSpec
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(SC_OK);
    }
}

