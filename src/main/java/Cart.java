import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonKey;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class Cart {
    private List<Products> cart;
    @JsonAlias({"total_discount"})
    private float totalDiscount;
    @JsonAlias({"total_price"})
    private float totalPrice;

    private float dddd;
}
