package cz.muni.fi.pv256.movio.uco396110.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import cz.muni.fi.pv256.movio.uco396110.data.FilmContract.FilmEntry;
import cz.muni.fi.pv256.movio.uco396110.model.Film;

public class FilmManagerImpl implements FilmManager {
    public static final int COL_FILM_ID = 0;
    public static final int COL_FILM_TITLE = 1;
    public static final int COL_FILM_RELEASE_DATE = 2;
    public static final int COL_FILM_OVERVIEW = 3;
    public static final int COL_FILM_COVER_PATH = 4;
    public static final int COL_FILM_BACKDROP_PATH = 5;
    private static final String[] FILM_COLUMNS = {
            FilmEntry._ID,
            FilmEntry.COLUMN_TITLE,
            FilmEntry.COLUMN_RELEASE_DATE_TEXT,
            FilmEntry.COLUMN_OVERVIEW,
            FilmEntry.COLUMN_COVER_PATH,
            FilmEntry.COLUMN_BACKDROP_PATH,
    };

    private static final String LOCAL_DATE_FORMAT = "yyyyMMdd";

    private static final String WHERE_ID = FilmEntry._ID + " = ?";
    private static final String WHERE_TITLE = FilmEntry.COLUMN_TITLE + " = ?";

    private Context mContext;

    public FilmManagerImpl(Context context) {
        mContext = context.getApplicationContext();
    }

    public void createFilm(Film film) {
        if (film.getId() != null) {
            throw new IllegalStateException("film's id shouldn't be set");
        }
        validate(film);

        film.setId(ContentUris.parseId(mContext.getContentResolver().insert(FilmEntry.CONTENT_URI, prepareFilmValues(film))));
    }

    public Film getFilm(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id == null");
        }

        Cursor cursor = mContext.getContentResolver().query(FilmEntry.CONTENT_URI, FILM_COLUMNS, WHERE_ID, new String[] { id.toString() }, null);
        if (cursor != null && cursor.moveToFirst()) {
            Film film;
            try {
                 film = getFilm(cursor);
            } finally {
                cursor.close();
            }

            return film;
        }

        return null;
    }

    public Film getFilmByTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            throw new IllegalArgumentException("title cannot be empty");
        }

        Cursor cursor = mContext.getContentResolver().query(FilmEntry.CONTENT_URI, FILM_COLUMNS, WHERE_TITLE, new String[] { title }, null);
        if (cursor != null && cursor.moveToFirst()) {
            Film film;
            try {
                film = getFilm(cursor);
            } finally {
                cursor.close();
            }

            return film;
        }

        return null;
    }

    public void updateFilm(Film film) {
        if (film.getId() == null) {
            throw new IllegalStateException("film's id cannot be null");
        }
        validate(film);

        mContext.getContentResolver().update(FilmEntry.CONTENT_URI, prepareFilmValues(film), WHERE_ID, new String[]{String.valueOf(film.getId())});
    }

    public void deleteFilm(Film film) {
        if (film == null) {
            throw new NullPointerException("film");
        }
        if (film.getId() == null) {
            throw new IllegalStateException("film id cannot be null");
        }

        mContext.getContentResolver().delete(FilmEntry.CONTENT_URI, WHERE_ID, new String[]{String.valueOf(film.getId())});
        film.setId(null);
    }

    private ContentValues prepareFilmValues(Film film) {
        ContentValues values = new ContentValues();
        values.put(FilmEntry.COLUMN_TITLE, film.getTitle());
        values.put(FilmEntry.COLUMN_RELEASE_DATE_TEXT, FilmContract.getDbDateString(film.getReleaseDate()));
        values.put(FilmEntry.COLUMN_OVERVIEW, film.getOverview());
        values.put(FilmEntry.COLUMN_COVER_PATH, film.getCoverPath());
        values.put(FilmEntry.COLUMN_BACKDROP_PATH, film.getBackdropPath());
        return values;
    }

    private Film getFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(COL_FILM_ID));
        film.setTitle(cursor.getString(COL_FILM_TITLE));
        film.setReleaseDate(FilmContract.getDateFromDb(cursor.getString(COL_FILM_RELEASE_DATE)));
        film.setOverview(cursor.getString(COL_FILM_OVERVIEW));
        film.setCoverPath(cursor.getString(COL_FILM_COVER_PATH));
        film.setBackdropPath(cursor.getString(COL_FILM_BACKDROP_PATH));
        return film;
    }

    private void validate(Film film) {
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
