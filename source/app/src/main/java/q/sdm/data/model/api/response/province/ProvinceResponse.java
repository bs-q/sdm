package q.sdm.data.model.api.response.province;

import lombok.Data;

@Data
public class ProvinceResponse {
    private Long id;
    private String provinceName;
    private Long parentId;
}
