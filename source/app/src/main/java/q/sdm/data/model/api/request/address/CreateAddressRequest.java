package q.sdm.data.model.api.request.address;

import lombok.Data;

@Data
public class CreateAddressRequest {
    private String address;
    private Long communeId;
    private Long customerId;
    private Long districtId;
    private String name;
    private String phone;
    private Long provinceId;
}
