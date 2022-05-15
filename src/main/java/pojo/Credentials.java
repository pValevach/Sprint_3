package pojo;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credentials {

    private String login;
    private String password;

    public Credentials(Courier courier) {
        this.login = courier.getLogin();
        this.password = courier.getPassword();
    }

    @Step("Get credos object from an existing courier")
    public static Credentials from(Courier courier) {
        return new Credentials(courier);
    }
}
