package ru.pepelaz.foffree.database

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.table.TableUtils
import com.j256.ormlite.support.ConnectionSource
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.sql.SQLException
import android.content.ContentValues.TAG
import ru.pepelaz.foffree.App


object  DatabaseHelper : OrmLiteSqliteOpenHelper(App.instance, "fof.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Location::class.java)
        } catch (e: SQLException) {
            Log.d("test_test", "error creating DB fof.db")
            throw RuntimeException(e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, connectionSource: ConnectionSource, oldVer: Int, newVer: Int) {
        try {
            TableUtils.dropTable<Location, Any>(connectionSource, Location::class.java, true)
            onCreate(db, connectionSource)
            onCreate(db, connectionSource)
        } catch (e: SQLException) {
            Log.d(TAG, "error upgrading db fof.db from ver " + oldVer)
            throw RuntimeException(e)
        }
    }
}