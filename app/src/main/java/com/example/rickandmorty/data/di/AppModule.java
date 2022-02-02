package com.example.rickandmorty.data.di;

import com.example.rickandmorty.data.remote.apiservices.RickAndMortyApi;
import com.example.rickandmorty.data.remote.pagging.CharactersDataSource;
import com.example.rickandmorty.data.remote.pagging.CharactersStorage;
import com.example.rickandmorty.data.repositories.MainRepository;
import java.util.concurrent.TimeUnit;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    public static Retrofit provideRetrofit(OkHttpClient provideClient) {
        return new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideClient)
                .build();
    }

    @Provides
    public static OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level
                .BODY))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }
    @Provides
    public static RickAndMortyApi provideApi(Retrofit retrofit) {
        return retrofit.create(RickAndMortyApi.class);
    }

    @Provides
    public static MainRepository provideMainRepository(RickAndMortyApi api)   {
        return new MainRepository(api);
    }
    @Provides
    public static CharactersStorage provideCharacterStorage(RickAndMortyApi api) {
        return new CharactersStorage(api);
    }
    @Provides
    public static CharactersDataSource provideDataSource(CharactersStorage storage) {
        return new CharactersDataSource(storage);
    }
}

