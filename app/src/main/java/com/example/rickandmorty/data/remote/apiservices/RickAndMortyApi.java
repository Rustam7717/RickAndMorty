package com.example.rickandmorty.data.remote.apiservices;

import com.example.rickandmorty.data.models.CustomCharacter;
import com.example.rickandmorty.data.models.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RickAndMortyApi {

    @GET("character")
    Call<MainResponse> getCharacters();

    @GET("character/{id}")
    Call<CustomCharacter> getCharacter(
            @Path("id") int id
    );

    @GET("character")
    Call<MainResponse> getCharacterByPage(
            @Query("page") int page
    );


}
