package q.sdm.data.model.api.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class LoginRequest extends BaseRequest{
    private String app = "CLIENT_WEB";
    private String phoneOrEmail;
    private String password;
}
