package com.example.projectmcs

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.service.autofill.UserData

class DBHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "Data"

        private const val  COLUMN_USERNAME= "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_ID = "id"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME(
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT , 
            $COLUMN_USERNAME VARCHAR(50),
            $COLUMN_PASSWORD VARCHAR(50),
            $COLUMN_EMAIL VARCHAR(50),
            $COLUMN_PHONE VARCHAR(10)
            )
        """.trimIndent()

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable ="DROP TABLE IF EXISTS $TABLE_NAME"


        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun insertUser(username: String, password: String, email: String, phone: String): Long{
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PHONE, phone)
        }

        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }


    fun findUserByUsername(username: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        var userExists = false

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                userExists = true
            }
            cursor.close()
        }

        db.close()
        return userExists
    }

    fun getPasswordByUsername(username: String): String? {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_PASSWORD), selection, selectionArgs, null, null, null)

        var password: String? = null

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            }
            cursor.close()
        }

        db.close()
        return password
    }

    fun getPhoneByUsername(username: String): String? {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_PHONE), selection, selectionArgs, null, null, null)

        var phone: String? = null

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            }
            cursor.close()
        }

        db.close()
        return phone
    }






    fun getUserDetails(username: String, password: String): User? {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query("Data", null, selection, selectionArgs, null, null, null)

        cursor.moveToFirst()

        if ( cursor.count > 0){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))

            return User(id,username,password, email, phone)
        }

        cursor.close()
        db.close()
        return null
    }

    data class User(val id: Int, val username: String, val password: String, val email: String, val phone:String)

}