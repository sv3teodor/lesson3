import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private List<Products> cart;
    @JsonAlias({"total_discount"})
    private float totalDiscount;
    @JsonAlias({"total_price"})
    private float totalPrice;
}
