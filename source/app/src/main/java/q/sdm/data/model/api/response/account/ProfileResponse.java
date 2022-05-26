package q.sdm.data.model.api.response.account;

import java.util.Date;

import lombok.Data;

@Data
public class ProfileResponse {
private Date birthday;
private String createdBy;
private Date createdDate;
private String customerAddress;
private String customerAvatarPath;
private String customerEmail;
private String customerFullName;
private String customerPhone;
private Long id;
private Boolean isLoyalty;
private Date loyaltyDate;
private Integer loyaltyLevel;
private String modifiedBy;
private Date modifiedDate;
private String note;
private Integer saleOff;
private Integer sex;
private Integer status;
}
