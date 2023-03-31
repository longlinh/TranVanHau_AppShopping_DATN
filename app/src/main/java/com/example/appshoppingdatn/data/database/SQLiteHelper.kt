package com.example.appshoppingdatn.data.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    fun QueryData(sql: String?) {
        val database = writableDatabase
        database.execSQL(sql)
    }

    fun getData(sql: String?): Cursor {
        val database = readableDatabase
        return database.rawQuery(sql, null)
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {}
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
    }
}
