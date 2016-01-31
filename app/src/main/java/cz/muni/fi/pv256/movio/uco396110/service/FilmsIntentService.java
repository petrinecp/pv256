package cz.muni.fi.pv256.movio.uco396110.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.muni.fi.pv256.movio.uco396110.R;
import cz.muni.fi.pv256.movio.uco396110.helper.AppStatus;
import cz.muni.fi.pv256.movio.uco396110.model.Film;
import cz.muni.fi.pv256.movio.uco396110.model.Films;
import retrofit2.Call;

public class FilmsIntentService extends IntentService {
    public static final String FILMS_IN_THEATRE_ARG = "InTheatre";
    public static final String MOST_POPULAR_FILMS_ARG = "MostPopular";

    private static final String IMAGE_SERVER_URL = "http://image.tmdb.org/t/p/{0}/{1}";
    private static final String IMAGE_SIZE_SMALL = "w300";
    private static final String IMAGE_SIZE_MEDIUM = "w500";

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "FilmsIntentService";

    public FilmsIntentService() {
        super(FilmsIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        if (AppStatus.getInstance(this).isNetworkAvailable()) {
            /* Update UI: Download Service is Running */
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            try {
                FilmsService service = FilmServiceFactory.create();
                ArrayList<Film> filmsInTheatre = downloadFilmsInTheatre(service);
                ArrayList<Film> mostPopularFilms = downloadMostPopularFilms(service);

                if (null != filmsInTheatre && filmsInTheatre.size() > 0) {
                    bundle.putParcelableArrayList(FILMS_IN_THEATRE_ARG, filmsInTheatre);
                }

                if (null != mostPopularFilms && mostPopularFilms.size() > 0) {
                    bundle.putParcelableArrayList(MOST_POPULAR_FILMS_ARG, mostPopularFilms);
                }

                /* Sending result back to activity */
                receiver.send(STATUS_FINISHED, bundle);
            } catch (Exception e) {

                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, getString(R.string.download_fail));
                receiver.send(STATUS_ERROR, bundle);
            }
        } else {
            /* Sending error message back to activity */
            bundle.putString(Intent.EXTRA_TEXT, getString(R.string.no_connection));
            receiver.send(STATUS_ERROR, bundle);
        }

        Log.d(TAG, "Service Stopping!");
    }

    private ArrayList<Film> downloadFilmsInTheatre(FilmsService service) throws IOException {
        ArrayList<Film> result = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String currentDate = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        String monthAgoDate = dateFormat.format(calendar.getTime());

        Call<Films> call = service.getFilmsByReleaseDateRange(monthAgoDate, currentDate);
        Films filmsInTheatre = call.execute().body();
        result.addAll(getFilmsWithAbsoluteImagePaths(filmsInTheatre).subList(0, 6));

        return result;
    }

    private ArrayList<Film> downloadMostPopularFilms(FilmsService service) throws IOException {
        ArrayList<Film> result = new ArrayList<>();
        Call<Films> call = service.getFilms("popularity.desc");
        Films mostPopularFilms = call.execute().body();
        result.addAll(getFilmsWithAbsoluteImagePaths(mostPopularFilms).subList(0, 6));

        return result;
    }

    private ArrayList<Film> getFilmsWithAbsoluteImagePaths(Films films) {
        return getFilmsWithAbsoluteImagePaths(films.getResults());
    }

    public static ArrayList<Film> getFilmsWithAbsoluteImagePaths(ArrayList<Film> films) {
        for (Film film : films) {
            getFilmWithAbsoluteImagePaths(film);
        }

        return films;
    }

    public static Film getFilmWithAbsoluteImagePaths(Film film) {
        String posterFullUrl = MessageFormat.format(IMAGE_SERVER_URL, IMAGE_SIZE_SMALL, film.getCoverPath());
        film.setCoverPath(posterFullUrl);

        String backdropFullUrl = MessageFormat.format(IMAGE_SERVER_URL, IMAGE_SIZE_MEDIUM, film.getBackdropPath());
        film.setBackdropPath(backdropFullUrl);

        return film;
    }
}
