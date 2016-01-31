package cz.muni.fi.pv256.movio.uco396110.service;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class ServiceFactory {

    private ServiceFactory() {

    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, Interceptor authInterceptor) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }
}
