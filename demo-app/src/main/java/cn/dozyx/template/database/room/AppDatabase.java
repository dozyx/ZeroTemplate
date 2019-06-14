package cn.dozyx.template.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Create by timon on 2019/5/17
 **/
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
