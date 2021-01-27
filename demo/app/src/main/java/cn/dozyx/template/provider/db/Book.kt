package cn.dozyx.template.provider.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class Book(@ColumnInfo val title: String, @ColumnInfo val author: String) {
}