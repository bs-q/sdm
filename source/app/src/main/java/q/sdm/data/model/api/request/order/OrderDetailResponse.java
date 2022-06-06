package q.sdm.data.model.api.request.order;

import lombok.Data;

@Data
public class OrderDetailResponse {
    private Integer amount;
    private Long productId;
}
