package cn.dozyx.template.provider.db

import android.content.Context
import androidx.room.Room

object BookDbManager {
    lateinit var context: Context

    val database: BookDatabase by lazy {
        Room.databaseBuilder(context, BookDatabase::class.java, "book_room").build()
    }

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun getDao(): BookDao = database.bookDao()
}