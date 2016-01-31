package cz.muni.fi.pv256.movio.uco396110.service;

import cz.muni.fi.pv256.movio.uco396110.model.Film;
import cz.muni.fi.pv256.movio.uco396110.model.Films;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmsService {

    @GET("3/discover/movie")
    Call<Films> getFilmsByReleaseDateRange(@Query("primary_release_date.gte") String fromDate, @Query("primary_release_date.lte") String toDate);

    @GET("3/discover/movie")
    Call<Films> getFilms(@Query("sort_by") String sortBy);

    @GET("3/movie/{id}")
    Call<Film> getFilm(@Path("id") Long filmId);
}
