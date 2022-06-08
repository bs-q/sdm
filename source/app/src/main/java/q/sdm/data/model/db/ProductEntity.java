package q.sdm.data.model.db;

import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    @ColumnInfo(name = "quantity_in_stock")
    public Integer quantityInStock;

    @ColumnInfo(name = "sale")
    public Double sale;

    public String getProductThumbnail(){
        return thumbnail;
    }

    /**
     * Use this method to observe amount of product.
     */
    @Ignore
    private ObservableInt amountObservable;
    public ObservableInt getObservableAmount(){
        if (amountObservable == null) {
            amountObservable = new ObservableInt(amount);
            amountObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    ProductEntity.this.amount = amountObservable.get();
                }
            });
        }
        return amountObservable;
    }
}
