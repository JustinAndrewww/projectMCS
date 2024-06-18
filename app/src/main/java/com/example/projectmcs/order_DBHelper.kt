package com.example.projectmcs

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class order_DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Orders.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "orders"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ITEM_ID = "item_id"
        private const val COLUMN_QUANTITY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USER_ID INTEGER,"
                + "$COLUMN_ITEM_ID INTEGER,"
                + "$COLUMN_QUANTITY INTEGER)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert a new order
    fun insertOrder(userId: Int, itemId: Int, quantity: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_USER_ID, userId)
            put(COLUMN_ITEM_ID, itemId)
            put(COLUMN_QUANTITY, quantity)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    // Get all orders
    fun getAllOrders(): Cursor {
        val db = this.readableDatabase
        return db.query(TABLE_NAME, null, null, null, null, null, null)
    }

    // Get orders by user ID
    fun getOrdersByUserId(userId: Int): Cursor {
        val db = this.readableDatabase
        return db.query(
            TABLE_NAME,
            null,
            "$COLUMN_USER_ID = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )
    }

    // Update an order
    fun updateOrder(id: Int, userId: Int, itemId: Int, quantity: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_USER_ID, userId)
            put(COLUMN_ITEM_ID, itemId)
            put(COLUMN_QUANTITY, quantity)
        }
        return db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    // Delete an order
    fun deleteOrder(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}
