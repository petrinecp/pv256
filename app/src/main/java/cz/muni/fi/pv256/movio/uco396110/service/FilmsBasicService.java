package cz.muni.fi.pv256.movio.uco396110.service;

import java.io.IOException;
import java.util.List;

import cz.muni.fi.pv256.movio.uco396110.model.Film;

public interface FilmsBasicService {
    List<Film> getFilmsInTheatre() throws IOException;
    List<Film> getMostPopularFilms() throws IOException;
    int getFilmsCount();
    void Update() throws IOException;
}
