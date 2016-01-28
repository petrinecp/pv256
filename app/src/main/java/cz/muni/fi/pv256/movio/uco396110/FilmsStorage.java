package cz.muni.fi.pv256.movio.uco396110;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco396110.model.Film;

public class FilmsStorage {
    private static final int NUMBER_OF_FILMS_PER_CATEGORY = 6;
    private static FilmsStorage mInstance = new FilmsStorage();
    private List<FilmAdapterData> mFilms = new ArrayList<>();

    public static FilmsStorage getInstance() {
        return mInstance;
    }

    private FilmsStorage() {
    }

    public List<FilmAdapterData> getFilms() {
        return mFilms;
    }

    public void setFilms(List<FilmAdapterData> films) {
        mFilms = films;
    }

    public FilmAdapterData getFilm(int index) {
        return mFilms.get(index);
    }

    public void addFilms(List<Film> films, FilmCategory category) {
        int limit = films.size() < NUMBER_OF_FILMS_PER_CATEGORY ? films.size() : NUMBER_OF_FILMS_PER_CATEGORY;
        List<Film> oFilms = films.subList(0, limit);

        for (Film film : oFilms) {
            mFilms.add(new FilmAdapterData(category, film));
        }
    }

    public  void clear() {
        mFilms.clear();
    }
}
