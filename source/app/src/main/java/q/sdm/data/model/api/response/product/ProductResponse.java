package q.sdm.data.model.api.response.product;

import java.util.Date;

import lombok.Data;
import q.sdm.BuildConfig;

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

    public String getProductImage(){
        return BuildConfig.BASE_URL+"v1/file/download"+productImage;
    }
}
