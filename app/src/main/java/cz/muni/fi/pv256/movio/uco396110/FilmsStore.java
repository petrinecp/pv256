package cz.muni.fi.pv256.movio.uco396110;

import cz.muni.fi.pv256.movio.uco396110.model.Film;

/**
 * Created by peter on  17.1. .
 */
public class FilmsStore {
    public static FilmAdapterData[] sFilms = new FilmAdapterData[] {
            new FilmAdapterData(FilmCategory.IN_THEATRES, new Film("The Revenant", "08-01-2016", "")),
            new FilmAdapterData(FilmCategory.IN_THEATRES, new Film("The Hateful Eight", "08-01-2016", "")),
            new FilmAdapterData(FilmCategory.IN_THEATRES, new Film("Star Wars: The Force Awakens", "18-12-2015", "")),
            new FilmAdapterData(FilmCategory.MOST_POPULAR, new Film("Creed", "25-11-2015", ""))
    };
}
