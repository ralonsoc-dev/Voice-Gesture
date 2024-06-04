package com.example.voicegesture.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class ConexionSqliteHelper(context: Context, name: String, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    val CREAR_TABLA = "CREATE TABLE datos (id_dato int primary key, letra text, matriz text);"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREAR_TABLA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Compruebo si esa tabla existe y si existe la actualizo o machacho o lo que sea
        db.execSQL("DROP TABLE IF EXISTS datos")
        onCreate(db)
    }

}