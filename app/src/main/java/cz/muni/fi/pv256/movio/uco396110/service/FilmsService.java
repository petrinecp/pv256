package cz.muni.fi.pv256.movio.uco396110.service;

import java.io.IOException;
import java.util.List;

import cz.muni.fi.pv256.movio.uco396110.model.Film;

public interface FilmsService {
    List<Film> getFilmsInTheatre();
    List<Film> getMostPopularFilms();
    Film getFilm(int index);
    int getFilmsCount();
    void Update() throws IOException;
}