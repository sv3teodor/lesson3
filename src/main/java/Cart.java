import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private List<Products> cart;
    @JsonProperty("total_discount")
    private float totalDiscount;
    @JsonProperty("total_price")
    private float totalPrice;
}
