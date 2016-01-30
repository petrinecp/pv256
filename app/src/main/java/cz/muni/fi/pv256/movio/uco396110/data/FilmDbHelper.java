package cz.muni.fi.pv256.movio.uco396110.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cz.muni.fi.pv256.movio.uco396110.data.FilmContract.FilmEntry;

public class FilmDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "films.db";
    private static final int DATABASE_VERSION = 1;

    public FilmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + FilmEntry.TABLE_NAME + " (" +
                FilmEntry._ID + " INTEGER PRIMARY KEY," +
                FilmEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                FilmEntry.COLUMN_RELEASE_DATE_TEXT + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_OVERVIEW + " TEXT NOT NULL," +
                FilmEntry.COLUMN_COVER_PATH + " TEXT NOT NULL," +
                FilmEntry.COLUMN_BACKDROP_PATH + " TEXT," +
                "UNIQUE (" + FilmEntry.COLUMN_TITLE + ", " + FilmEntry.COLUMN_RELEASE_DATE_TEXT + ") ON CONFLICT REPLACE" +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FilmEntry.TABLE_NAME);
        onCreate(db);
    }
}
