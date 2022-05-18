package q.sdm.data.model.api.request.register;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String password;
    private String email;
}
