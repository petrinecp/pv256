package cz.muni.fi.pv256.movio.uco396110.service;

import cz.muni.fi.pv256.movio.uco396110.AuthInterceptor;

public class FilmServiceFactory {
    private static final String SERVER_URL = "https://api.themoviedb.org/";
    private static final String API_KEY = "92dbd1480449551071ed308156bc7c53";

    private FilmServiceFactory () {

    }

    public static FilmsService create() {
        return ServiceFactory.createService(FilmsService.class, SERVER_URL, new AuthInterceptor(API_KEY));
    }
}
