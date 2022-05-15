package rest;

import pojo.Courier;
import pojo.Credentials;
import org.junit.Before;
import org.junit.After;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CourierTest {

    private int courierId;
    private CourierClient courierClient;

    @Before
    public void setup() {
        courierClient = new CourierClient();
    }

    @After
    public void teardown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Can create new courier")
    public void creationSuccess() {
        Courier courier = Courier.getRandom();

        boolean created201 = courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");

        Credentials validCredos = Credentials.from(courier);
        courierId = courierClient.login(validCredos)
                .extract()
                .path("id");

        assertTrue(created201);
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Can't create courier with non-unique login")
    public void cntCreateTwoSameUsers() {
        Courier courier = Courier.getRandom();

        boolean created201 = courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");

        String created409 = courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("message");

        Credentials validCredos = Credentials.from(courier);
        courierId = courierClient.login(validCredos)
                .extract()
                .path("id");

        assertTrue(created201);
        assertEquals("Этот логин уже используется. Попробуйте другой.", created409);
    }

    @Test
    @DisplayName("Can't create courier without login")
    public void loginDntExistError() {
        Courier courier = Courier.getRandom();
        courier.setLogin("");

        String created400 = courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");

        assertEquals("Недостаточно данных для создания учетной записи", created400);
    }

    @Test
    @DisplayName("Can't create courier without password")
    public void passwordDntExistError() {
        Courier courier = Courier.getRandom();
        courier.setPassword("");

        String created400 = courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");

        assertEquals("Недостаточно данных для создания учетной записи", created400);
    }
}
