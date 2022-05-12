package rest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Courier;
import pojo.Credentials;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CourierLoginTest {

    private int courierId;
    private CourierClient courierClient;
    private final Courier courier = Courier.getRandom();
    ;

    @Before
    public void setup() {
        courierClient = new CourierClient();

        courierClient.create(courier)
                .assertThat()
                .statusCode(201);
    }

    @After
    public void teardown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Can login")
    public void loginSuccess() {

        Credentials credentials = Credentials.from(courier);
        courierId = courierClient.login(credentials)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Can't login without required fields")
    public void requiredFieldsDntExistError() {

        Credentials wrongCredos = new Credentials("", "");
        String errorMessage = courierClient.login(wrongCredos)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        Credentials validCredos = Credentials.from(courier);
        courierId = courierClient.login(validCredos)
                .extract()
                .path("id");

        assertEquals("Недостаточно данных для входа", errorMessage);
    }


    @Test
    @DisplayName("Can't login with non-existing user")
    public void userDntExistError() {

        Credentials wrongCredos = new Credentials("unique123", "unique123");
        String errorMessage = courierClient.login(wrongCredos)
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");

        Credentials validCredos = Credentials.from(courier);
        courierId = courierClient.login(validCredos)
                .extract()
                .path("id");

        assertEquals("Учетная запись не найдена", errorMessage);
    }


}
