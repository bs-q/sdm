package q.sdm.data.model.api.request.order;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import q.sdm.data.model.db.ProductEntity;

@Data
public class CreateOrderRequest {
    private String ordersAddress;
    private Integer paymentMethod;
    private String receiverName;
    private String receiverPhone;
    private List<OrderDetailResponse> ordersDetailDtos;
    
    public void createOrderDetail(List<ProductEntity> productEntities) {
        ordersDetailDtos = new ArrayList<>();
        for (ProductEntity item :
                productEntities) {
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setProductId(item.id);
            orderDetailResponse.setAmount(item.amount);
            ordersDetailDtos.add(orderDetailResponse);
        }
    }

}
