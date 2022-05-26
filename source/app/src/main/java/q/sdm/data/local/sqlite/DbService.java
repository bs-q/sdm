package q.sdm.data.local.sqlite;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.data.model.db.UserEntity;

public interface DbService {

    Observable<List<UserEntity>> getAllDbUser();

    LiveData<List<UserEntity>> loadAllToLiveData();

    Observable<Long> insertUser(UserEntity user);

    Observable<Boolean> deleteUser(UserEntity user);

    Observable<UserEntity> findById(Long id);

    Observable<List<ProductEntity>> getAllDbProduct();

    LiveData<List<ProductEntity>> loadAllProductToLiveData();

    Observable<Long> insertProduct(ProductEntity user);

    Observable<Boolean> deleteProduct(ProductEntity user);

    Observable<ProductEntity> findProductById(Long id);


    void nukeProducts();

}
