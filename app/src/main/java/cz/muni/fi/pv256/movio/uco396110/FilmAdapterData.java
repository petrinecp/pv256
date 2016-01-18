package cz.muni.fi.pv256.movio.uco396110;

import cz.muni.fi.pv256.movio.uco396110.model.Film;

/**
 * Created by peter on  18.1. .
 */
public class FilmAdapterData {
    private FilmCategory mCategory;
    private Film mFilm;

    public FilmAdapterData(FilmCategory category, Film film) {
        mCategory = category;
        mFilm = film;
    }

    public FilmCategory getCategory() {
        return mCategory;
    }

    public Film getFilm() {
        return mFilm;
    }

    @Override
    public String toString() {
        return mFilm.toString();
    }
}
