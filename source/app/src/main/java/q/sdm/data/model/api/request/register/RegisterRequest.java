package q.sdm.data.model.api.request.register;

import lombok.Data;

@Data
public class RegisterRequest {
    private String customerFullName;
    private String customerPassword;
    private String customerPhone;
}
