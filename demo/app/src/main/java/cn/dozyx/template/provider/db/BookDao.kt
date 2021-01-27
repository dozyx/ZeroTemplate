package cn.dozyx.template.provider.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    fun getAllBooks(): List<Book>

    @Insert
    fun addBook(book: Book)

    @Delete
    fun removeBook(book: Book)
}