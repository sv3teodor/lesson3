import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    private Integer id;
    private String name;
    private String category;
    private Double price;
    private Double discount;
    private Integer quantity;
}
