package cz.muni.fi.pv256.movio.uco396110.service;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import cz.muni.fi.pv256.movio.uco396110.FilmAdapterData;
import cz.muni.fi.pv256.movio.uco396110.FilmCategory;
import cz.muni.fi.pv256.movio.uco396110.FilmsStorage;
import cz.muni.fi.pv256.movio.uco396110.model.Film;
import cz.muni.fi.pv256.movio.uco396110.model.SearchResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TheMovieDbFilmsServiceImpl implements FilmsService {
    private static final String SERVER_URL = "https://api.themoviedb.org/";
    private static final String API_KEY = "92dbd1480449551071ed308156bc7c53";
    private static final String API_KEY_PARAM = "api_key";

    private static final String IN_THEATRES_API_URL = "3/discover/movie?primary_release_date.gte={0}&primary_release_date.lte={1}";
    private static final String MOST_POPULAR_API_URL = "3/discover/movie?sort_by=popularity.desc";
    private static final String IMAGE_SERVER_URL = "http://image.tmdb.org/t/p/{0}/{1}";
    private static final String IMAGE_SIZE_SMALL = "w300";
    private static final String IMAGE_SIZE_MEDIUM = "w500";

    private static final String GET_IN_THEATRES_URL = SERVER_URL + IN_THEATRES_API_URL + "&" + API_KEY_PARAM + "=" + API_KEY;
    private static final String GET_MOST_POPULAR_URL = SERVER_URL + MOST_POPULAR_API_URL + "&" + API_KEY_PARAM + "=" + API_KEY;
    private FilmsStorage mFilmsStorage;

    public TheMovieDbFilmsServiceImpl() {
        mFilmsStorage = FilmsStorage.getInstance();
    }

    @Override
    public List<Film> getFilmsInTheatre() {
//        FilmsStorage filmsStorage = FilmsStorage.getInstance();
//        List<FilmAdapterData> films = filmsStorage.getFilmsInTheatre();
//        if (films == null) {
//            try {
//                Update();
//                films = filmsStorage.getFilmsInTheatre();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        return null;
    }

    @Override
    public List<Film> getMostPopularFilms() {
        return null;
    }

    @Override
    public Film getFilm(int index) {
        FilmsStorage filmsStorage = FilmsStorage.getInstance();
        return filmsStorage.getFilm(index).getFilm();
    }

    @Override
    public int getFilmsCount() {
        return 0;
    }

    @Override
    public void Update() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String currentDate = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        String monthAgoDate = dateFormat.format(calendar.getTime());
        Fetch(FilmCategory.IN_THEATRES, MessageFormat.format(GET_IN_THEATRES_URL, monthAgoDate, currentDate));

        Fetch(FilmCategory.MOST_POPULAR, GET_MOST_POPULAR_URL);
    }

    private void Fetch(FilmCategory category, String sourceUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(sourceUrl)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        String body = response.body().string();
        Gson gson = new Gson();
        SearchResponse searchResponse = gson.fromJson(body, SearchResponse.class);
        List<Film> filmsInTheatre = searchResponse.getResults();
        for (Film film : filmsInTheatre) {
            String posterFullUrl = MessageFormat.format(IMAGE_SERVER_URL, IMAGE_SIZE_SMALL, film.getCoverPath());
            film.setCoverPath(posterFullUrl);

            String backdropFullUrl = MessageFormat.format(IMAGE_SERVER_URL, IMAGE_SIZE_MEDIUM, film.getBackdropPath());
            film.setBackdropPath(backdropFullUrl);

            mFilmsStorage.addFilm(new FilmAdapterData(category, film));
        }
    }
}
