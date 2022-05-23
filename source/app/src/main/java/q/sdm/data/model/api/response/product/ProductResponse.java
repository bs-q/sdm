package q.sdm.data.model.api.response.product;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String productName;
    private String productImage;
    private Long productPrice;
}
