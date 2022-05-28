package q.sdm.data.model.api.response.product;

import java.util.Date;

import lombok.Data;

@Data
public class ProductResponse {
    Long categoryId;
    String createdBy;
    Date createdDate;
    String description;
    boolean hasChild;
    Long id;
    String labelColor;
    String productImage;
    String productName;
    Double productPrice;
    Integer saleoff;
    String shortDescription;
    Integer status;
}
