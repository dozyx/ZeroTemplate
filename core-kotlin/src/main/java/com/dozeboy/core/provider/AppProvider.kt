package com.dozeboy.core.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * @author dozeboy
 * @date 2019/1/6
 */
class AppProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        if (context == null) {
            return false
        }
        return true
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        throw UnsupportedOperationException("Not implement in core")
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        throw UnsupportedOperationException("Not implement in core")
    }


    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Not implement in core")
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Not implement in core")
    }

    override fun getType(uri: Uri?): String? = null
}