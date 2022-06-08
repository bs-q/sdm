package q.sdm.data.model.api.response.order;

import lombok.Data;
import q.sdm.data.model.api.response.product.ProductResponse;

@Data
public class OrderProductResponse {
    private Long id;
    private Long ordersId;
    private Double price;
    private Integer amount;
    private ProductResponse productDto;
}
