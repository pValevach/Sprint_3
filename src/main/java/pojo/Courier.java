package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import io.qameta.allure.Step;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courier {

    public String login;
    public String password;
    public String firstName;

    @Step("Get courier object with random fields")
    public static Courier getRandom() {
        String login = RandomStringUtils.randomAlphanumeric(8);
        String password = RandomStringUtils.randomAlphanumeric(8);
        String firstName = RandomStringUtils.randomAlphanumeric(8);

        return new Courier(login, password, firstName);
    }
}

