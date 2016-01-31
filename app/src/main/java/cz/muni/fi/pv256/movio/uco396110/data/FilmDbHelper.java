package cz.muni.fi.pv256.movio.uco396110.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import cz.muni.fi.pv256.movio.uco396110.data.FilmContract.FilmEntry;
import cz.muni.fi.pv256.movio.uco396110.model.Film;

public class FilmDbHelper extends SQLiteOpenHelper {
    public static final int COL_FILM_ID = 0;
    public static final int COL_REMOTE_ID = 1;
    public static final int COL_FILM_TITLE = 2;
    public static final int COL_FILM_RELEASE_DATE = 3;
    public static final int COL_FILM_OVERVIEW = 4;
    public static final int COL_FILM_COVER_PATH = 5;
    public static final int COL_FILM_BACKDROP_PATH = 6;

    public static final String DATABASE_NAME = "films.db";
    private static final int DATABASE_VERSION = 3;

    public FilmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + FilmEntry.TABLE_NAME + " (" +
                FilmEntry._ID + " INTEGER PRIMARY KEY," +
                FilmEntry.COLUMN_REMOTE_ID + " INTEGER NOT NULL," +
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

    public static ContentValues prepareFilmValues(Film film) {
        ContentValues values = new ContentValues();
        values.put(FilmEntry.COLUMN_REMOTE_ID, film.getRemoteId());
        values.put(FilmEntry.COLUMN_TITLE, film.getTitle());
        values.put(FilmEntry.COLUMN_RELEASE_DATE_TEXT, FilmContract.getDbDateString(film.getReleaseDate()));
        values.put(FilmEntry.COLUMN_OVERVIEW, film.getOverview());
        values.put(FilmEntry.COLUMN_COVER_PATH, film.getCoverPath());
        values.put(FilmEntry.COLUMN_BACKDROP_PATH, film.getBackdropPath());
        return values;
    }

    public static Film getFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(COL_FILM_ID));
        film.setRemoteId(cursor.getLong(COL_REMOTE_ID));
        film.setTitle(cursor.getString(COL_FILM_TITLE));
        film.setReleaseDate(FilmContract.getDateFromDb(cursor.getString(COL_FILM_RELEASE_DATE)));
        film.setOverview(cursor.getString(COL_FILM_OVERVIEW));
        film.setCoverPath(cursor.getString(COL_FILM_COVER_PATH));
        film.setBackdropPath(cursor.getString(COL_FILM_BACKDROP_PATH));
        return film;
    }

    public static void validate(Film film) {
        if (film == null) {
            throw new IllegalArgumentException("film == null");
        }
        if (TextUtils.isEmpty(film.getTitle())) {
            throw new IllegalStateException("film's title cannot be empty");
        }
        if (film.getReleaseDate() == null ) {
            throw new IllegalStateException("film's releaseDate cannot be null");
        }

        if (TextUtils.isEmpty(film.getCoverPath())) {
            throw new IllegalStateException("film's coverPath cannot be empty");
        }
    }
}
