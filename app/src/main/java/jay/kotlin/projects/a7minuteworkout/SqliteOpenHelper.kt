package jay.kotlin.projects.a7minuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLiteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object { // companion object is an object that allows us to create static variables
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SevenMinutesWorkout.db"
        private const val TABLE_HISTORY = "history"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_COMPLETED_DATE = "completed_date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_HISTORY_TABLE = ("CREATE TABLE " +
                TABLE_HISTORY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_COMPLETED_DATE
                + " TEXT" + ")") // Create History Table Query.
        db.execSQL(CREATE_HISTORY_TABLE) // Executing the create table query.
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY") // It drops the existing history table
        onCreate(db) // Calls the onCreate function so all the updated table will be created.
    }

    fun addDate(date: String) {
        val values = ContentValues() //
        values.put(COLUMN_COMPLETED_DATE, date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, values)
        db.close() // Database is closed after insertion.
    }

    fun getAllCompletedDatesList(): ArrayList<String> {
        val list = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)

        // Move the cursor to the next row.
        while (cursor.moveToNext()) {

            list.add(cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))
        }
        cursor.close() // Cursor is closed after its used.
        return list // List is returned.
    }
}