package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

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

    public static Orders getDefault() {
        return new Orders("Кабанчик",
                "Кабанище",
                "Поле, 414 apt.",
                3,
                "+7 888 000 55 44",
                5,
                "2020-05-06",
                "Кабанчик вернись в Кабаноху",
                null);
    }
}
