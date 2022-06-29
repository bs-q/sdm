package q.sdm.data.model.api.response.order;

import java.util.Date;

import lombok.Data;
import q.sdm.R;

@Data
public class OrderHistoryResponse {
    private Long id;
    private Date createdDate;
    private Double ordersTotalMoney;
    private Integer ordersState;
    private Integer ordersPrevState;
    private String ordersAddress;
    private Integer ordersVat;
    private String ordersCode;
    private Integer paymentMethod;
    private String receiverName;
    private String receiverPhone;

    public int getColor(){
        if (ordersState == ORDERS_STATE_CREATED) {
            return R.color.order_black;
        } else if (ordersState == ORDERS_STATE_ACCEPTED) {
            return R.color.order_yellow;
        } else if (ordersState == ORDERS_STATE_SHIPPING) {
            return R.color.order_blue;
        } else if (ordersState == ORDERS_STATE_DONE) {
            return R.color.order_green;
        }
        return R.color.primary;
    }
    public String getStateMessage(){
        if (ordersState == ORDERS_STATE_CREATED) {
            return "Mới tạo";
        } else if (ordersState == ORDERS_STATE_ACCEPTED) {
            return "Đã duyệt";
        } else if (ordersState == ORDERS_STATE_SHIPPING) {
            return "Vận chuyển";
        } else if (ordersState == ORDERS_STATE_DONE) {
            return "Hoàn tất";
        }
        return "Đã huỷ";
    }

    public static final Integer ORDERS_STATE_CREATED = 0;
    public static final Integer ORDERS_STATE_ACCEPTED = 1;
    public static final Integer ORDERS_STATE_SHIPPING = 2;
    public static final Integer ORDERS_STATE_DONE = 3;
    public static final Integer ORDERS_STATE_CANCEL = 4;
}
