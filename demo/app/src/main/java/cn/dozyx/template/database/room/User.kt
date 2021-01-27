package cn.dozyx.template.database.room

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 实体类，表示一条数据
 * Create by dozyx on 2019/5/17
 */
@Entity
class User {
    @JvmField
    @PrimaryKey
    var uid = 0

    @JvmField
    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @JvmField
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
    var avatar: ByteArray? = null

    override fun toString(): String {
        return "User{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}'
    }

    fun toContentValue(): ContentValues {
        val contentValues = ContentValues()
        contentValues.put("uid", uid)
        contentValues.put("first_name", firstName)
        contentValues.put("last_name", lastName)
        return contentValues
    }
}