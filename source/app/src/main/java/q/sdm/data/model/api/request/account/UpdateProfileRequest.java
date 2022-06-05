package q.sdm.data.model.api.request.account;

import java.util.Date;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private Date birthday;
    private String customerAvatarPath;
    private String customerEmail;
    private String customerFullName; //*
    private String customerPassword;
    private String oldPassword; //*
    private int sex;
}
