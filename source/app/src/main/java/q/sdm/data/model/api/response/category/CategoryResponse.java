package q.sdm.data.model.api.response.category;

import lombok.Data;
import q.sdm.BuildConfig;

@Data
public class CategoryResponse {
    private Long id;
    private String categoryName;
    private String categoryImage;

    public String getCategoryImage(){
        return BuildConfig.BASE_URL+"/v1/file/download"+ categoryImage;
    }
}
