package q.sdm.data.model.api.response.address;

import lombok.Data;
import q.sdm.data.model.api.response.province.ProvinceResponse;

@Data
public class AddressResponse {
    private String address;
    private Long id;
    private ProvinceResponse districtDto;
    private ProvinceResponse communeDto;
    private ProvinceResponse provinceDto;

    public String getParseAddress(){
        return address + ", " + communeDto.getProvinceName() + ", " + districtDto.getProvinceName() + ", " + provinceDto.getProvinceName();
    }
}
