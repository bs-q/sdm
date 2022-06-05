package q.sdm.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.EqualsAndHashCode;
import q.sdm.BuildConfig;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity(tableName = "db_products")
public class ProductEntity  extends BaseEntity{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "thumbnail")
    public String thumbnail;

    @ColumnInfo(name = "amount")
    public Integer amount;

    @ColumnInfo(name = "price")
    public Double price;

    public String getProductThumbnail(){
        return BuildConfig.BASE_URL + "v1/file/download" + thumbnail;
    }
}
