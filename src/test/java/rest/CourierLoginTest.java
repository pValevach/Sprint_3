package rest;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Courier;
import pojo.Credentials;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CourierLoginTest {

    private int courierId;
    private CourierClient courierClient;
    private final Courier courier = Courier.getRandom();

    @Before
    public void setup() {
        courierClient = new CourierClient();

        courierClient.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED);
    }

    @After
    public void teardown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Can login")
    public void loginSuccess() {

        Credentials credentials = Credentials.from(courier);
        courierId = courierClient.login(credentials)
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id");

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Can't login with wrong credos")
    public void requiredFieldsDntExistError() {

        Credentials wrongCredos = new Credentials("", "");
        String errorMessage = courierClient.login(wrongCredos)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");

        Credentials validCredos = Credentials.from(courier);
        courierId = courierClient.login(validCredos)
                .extract()
                .path("id");

        assertEquals("Недостаточно данных для входа", errorMessage);
    }

    @Test
    @DisplayName("Can't login with wrong password")
    public void wrongPasswordError() {

        Credentials wrongPasswordCredos = new Credentials(courier.getLogin(), "");
        String errorMessage = courierClient.login(wrongPasswordCredos)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
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
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");

        Credentials validCredos = Credentials.from(courier);
        courierId = courierClient.login(validCredos)
                .extract()
                .path("id");

        assertEquals("Учетная запись не найдена", errorMessage);
    }


}
