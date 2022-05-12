package rest;

import io.restassured.response.Validatable;
import io.restassured.response.ValidatableResponse;
import org.apache.groovy.json.internal.Value;
import pojo.Courier;
import pojo.Credentials;

public class CourierClient extends RestClient {

    private final String ROOT = "/courier";
    private final String LOGIN = ROOT + "/login";
    private final String COURIER = ROOT + "/{courierId}";

    public ValidatableResponse create(Courier courier) {
        return reqSpec
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    public ValidatableResponse login(Credentials credentials) {
        return reqSpec
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public void delete(int courierId) {
        reqSpec
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}

