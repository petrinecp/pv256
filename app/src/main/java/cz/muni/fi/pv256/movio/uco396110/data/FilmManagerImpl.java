package cz.muni.fi.pv256.movio.uco396110.data;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import cz.muni.fi.pv256.movio.uco396110.data.FilmContract.FilmEntry;
import cz.muni.fi.pv256.movio.uco396110.model.Film;

public class FilmManagerImpl implements FilmManager {

    private static final String[] FILM_COLUMNS = {
            FilmEntry._ID,
            FilmEntry.COLUMN_REMOTE_ID,
            FilmEntry.COLUMN_TITLE,
            FilmEntry.COLUMN_RELEASE_DATE_TEXT,
            FilmEntry.COLUMN_OVERVIEW,
            FilmEntry.COLUMN_COVER_PATH,
            FilmEntry.COLUMN_BACKDROP_PATH,
    };

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
        FilmDbHelper.validate(film);

        film.setId(ContentUris.parseId(mContext.getContentResolver().insert(FilmEntry.CONTENT_URI, FilmDbHelper.prepareFilmValues(film))));
    }

    public Film getFilm(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id == null");
        }

        Cursor cursor = mContext.getContentResolver().query(FilmEntry.CONTENT_URI, FILM_COLUMNS, WHERE_ID, new String[] { id.toString() }, null);
        if (cursor != null && cursor.moveToFirst()) {
            Film film;
            try {
                 film = FilmDbHelper.getFilm(cursor);
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
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return FilmDbHelper.getFilm(cursor);
                }
            } finally {
                cursor.close();
            }
        }

        return null;
    }

    public void updateFilm(Film film) {
        if (film.getId() == null) {
            throw new IllegalStateException("film's id cannot be null");
        }
        FilmDbHelper.validate(film);

        mContext.getContentResolver().update(FilmEntry.CONTENT_URI, FilmDbHelper.prepareFilmValues(film), WHERE_ID, new String[]{String.valueOf(film.getId())});
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
}
