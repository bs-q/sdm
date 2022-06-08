package q.sdm.data.local.sqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import q.sdm.data.model.db.ProductEntity;
import q.sdm.data.model.db.UserEntity;

@Dao
public interface DbProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ProductEntity productEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductEntity> userEntities);

    @Query("SELECT * FROM db_products")
    List<ProductEntity> loadAll();

    @Query("SELECT * FROM db_products ORDER BY id ASC")
    LiveData<List<ProductEntity>> loadAllToLiveData();

    @Query("SELECT * FROM db_users WHERE id=:id")
    ProductEntity findById(long id);

    @Delete
    void delete(ProductEntity userEntity);

    @Query("delete from db_products where id in (:productIds)")
    void deleteListProduct(List<Long> productIds);

    @Query("DELETE FROM db_products")
    void deleteAll();

}
