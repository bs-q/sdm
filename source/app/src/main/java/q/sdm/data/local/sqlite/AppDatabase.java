package q.sdm.data.local.sqlite;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import q.sdm.data.local.sqlite.converter.Converters;
import q.sdm.data.local.sqlite.dao.DbProductDao;
import q.sdm.data.local.sqlite.dao.DbUserDao;
import q.sdm.data.model.db.ProductEntity;
import q.sdm.data.model.db.UserEntity;

@Database(entities = {UserEntity.class, ProductEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DbUserDao getDbUserDao();
    public abstract DbProductDao getDbProductDao();

}
