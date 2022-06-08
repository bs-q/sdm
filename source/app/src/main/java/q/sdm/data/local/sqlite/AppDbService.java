package q.sdm.data.local.sqlite;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.data.model.db.UserEntity;

public class AppDbService implements DbService {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbService(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<UserEntity>> getAllDbUser() {
        return Observable.fromCallable(new Callable<List<UserEntity>>() {
            @Override
            public List<UserEntity> call() throws Exception {
                return mAppDatabase.getDbUserDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Long> insertUser(UserEntity user) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mAppDatabase.getDbUserDao().insert(user);
            }
        });
    }

    @Override
    public LiveData<List<UserEntity>> loadAllToLiveData() {
        return mAppDatabase.getDbUserDao().loadAllToLiveData();
    }


    @Override
    public Observable<Boolean> deleteUser(UserEntity user) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.getDbUserDao().delete(user);
                return true;
            }
        });
    }

    @Override
    public Observable<UserEntity> findById(Long id) {
        return Observable.fromCallable(new Callable<UserEntity>() {
            @Override
            public UserEntity call() throws Exception {
               return mAppDatabase.getDbUserDao().findById(id);
            }
        });
    }

    @Override
    public Observable<List<ProductEntity>> getAllDbProduct() {
        return Observable.fromCallable(new Callable<List<ProductEntity>>() {
            @Override
            public List<ProductEntity> call() throws Exception {
                return mAppDatabase.getDbProductDao().loadAll();
            }
        });
    }

    @Override
    public LiveData<List<ProductEntity>> loadAllProductToLiveData() {
        return mAppDatabase.getDbProductDao().loadAllToLiveData();
    }

    @Override
    public Observable<Long> insertProduct(ProductEntity user) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mAppDatabase.getDbProductDao().insert(user);
            }
        });    }

    @Override
    public Observable<Boolean> insertAllProduct(List<ProductEntity> products) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.getDbProductDao().insertAll(products);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteProduct(ProductEntity user) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.getDbProductDao().delete(user);
                return true;
            }
        });    }

    @Override
    public Observable<Boolean> deleteListProduct(List<Long> productIds) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.getDbProductDao().deleteListProduct(productIds);
                return true;
            }
        });
    }

    @Override
    public Observable<ProductEntity> findProductById(Long id) {
        return Observable.fromCallable(new Callable<ProductEntity>() {
            @Override
            public ProductEntity call() throws Exception {
                return mAppDatabase.getDbProductDao().findById(id);
            }
        });    }

    @Override
    public Observable<Boolean> nukeProducts() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.getDbProductDao().deleteAll();
                return true;
            }
        });
    }
}
