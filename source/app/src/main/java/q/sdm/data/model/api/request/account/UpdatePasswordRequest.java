package q.sdm.data.model.api.request.account;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String idHash;
    private String newPassword;
    private String otp;
}
