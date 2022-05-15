package pojo;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public String[] color;

    @Step("Get orders object with hardcode fields")
    public static Orders getDefault() {
        return new Orders("Кабанчик",
                "Весело",
                "Поле, 414 apt.",
                3,
                "+7 111 222 33 44",
                5,
                "2022-05-16",
                "Скоро буде свято, весела гулянка",
                null);
    }
}
