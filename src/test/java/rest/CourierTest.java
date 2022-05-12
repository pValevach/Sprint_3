package rest;

import pojo.Courier;
import pojo.Credentials;
import org.junit.Before;
import org.junit.After;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

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
        if (courierId != 0) { courierClient.delete(courierId); }
    }

    @Test
    @DisplayName("Could create new courier")
    public void creationSuccess() {
        Courier courier = Courier.getRandom();

        boolean created201 = courierClient.create(courier)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");

        Credentials validCredos = Credentials.from(courier);
        courierId = courierClient.login(validCredos)
                .extract()
                .path("id");;

        assertTrue(created201);
        assertNotEquals(0,courierId);
    }

    @Test
    @DisplayName("Couldn't create courier with non-unique login")
    public void cntCreateTwoSameUsers() {
        Courier courier = Courier.getRandom();

        boolean created201 = courierClient.create(courier)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");;
        String created409 = courierClient.create(courier)
                .assertThat()
                .statusCode(409)
                .extract()
                .path("message");

        Credentials validCredos = Credentials.from(courier);
        courierId = courierClient.login(validCredos)
                .extract()
                .path("id");

        assertTrue(created201);
        assertEquals("Этот логин уже используется. Попробуйте другой.",created409);
    }

    @Test
    @DisplayName("Couldn't create courier without required fields")
    public void requiredFieldsDntExistError() {
        Courier courier = new Courier();

        String created400 = courierClient.create(courier)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");

        assertEquals("Недостаточно данных для создания учетной записи", created400);
    }
}
