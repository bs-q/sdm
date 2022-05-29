package q.sdm.data.model.api.response;

import lombok.Data;

@Data
public class DataWrapper <T> {
    private T data;
}
