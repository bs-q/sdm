package q.sdm.data.model.api.response.setting;

import lombok.Data;

@Data
public class SettingResponse {
    private String value;
    private String key;

    public static String VAT_CLIENT = "vatClient";
    public static String ORDERS_LIMIT_CANCEL_TIME = "ordersLimitCancelTime";
}
