package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersList {

    private int courierId;
    private String nearestStation;
    private int limit;
    private int page;
}
