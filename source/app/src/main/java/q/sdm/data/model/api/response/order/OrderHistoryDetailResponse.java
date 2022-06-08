package q.sdm.data.model.api.response.order;

import static q.sdm.data.model.api.response.order.OrderHistoryResponse.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Objects;

import lombok.Data;
import q.sdm.R;


@Data
public class OrderHistoryDetailResponse {
    private Long id;
    private Date createdDate;
    private Double ordersTotalMoney;
    private Integer ordersSaleOff;
    private Integer ordersState;
    private Integer ordersPrevState;
    private String ordersAddress;
    private Integer ordersVat;
    private String ordersCode;
    private Integer paymentMethod;
    private String receiverName;
    private String receiverPhone;
    private List<OrderProductResponse> ordersDetailDtos;


    public Integer[] generateColorListState(){ // 7 view, 4 icon, 3 line , 0 - 0 - 0 - 0
        Integer[] colorList = new Integer[]{R.color.gray,R.color.gray,R.color.gray,R.color.gray,R.color.gray,R.color.gray,R.color.gray};
        if (Objects.equals(ordersState, ORDERS_STATE_CREATED)) {
            colorList[0] = R.color.order_blue;
        } else if (Objects.equals(ordersState, ORDERS_STATE_ACCEPTED)) {
            for (int i = 0; i < 3; i++) {
                colorList[i] = R.color.order_blue;
            }
        } else if (Objects.equals(ordersState, ORDERS_STATE_SHIPPING)) {
            for (int i = 0; i < 5; i++) {
                colorList[i] = R.color.order_blue;
            }
        } else if (ordersState == ORDERS_STATE_DONE) {
            for (int i = 0; i < 7; i++) {
                colorList[i] = R.color.order_blue;
            }
        }
        return colorList;
    }
}
