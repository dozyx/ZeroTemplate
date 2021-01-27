package cn.dozyx.template.provider.db

import androidx.room.RoomDatabase

abstract class BookDatabase : RoomDatabase() {
    public abstract fun bookDao(): BookDao
}