package cz.muni.fi.pv256.movio.uco396110.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.muni.fi.pv256.movio.uco396110.AuthInterceptor;

public class FilmServiceFactory {
    private static final String SERVER_URL = "https://api.themoviedb.org/";
    private static final String API_KEY = "92dbd1480449551071ed308156bc7c53";

    private FilmServiceFactory () {

    }

    public static FilmsService create() {
        AuthInterceptor authInterceptor = new AuthInterceptor(API_KEY);

        return ServiceFactory.createService(FilmsService.class, SERVER_URL, authInterceptor);
    }
}
