package com.example.rickandmorty.data.remote.pagging;


import com.example.rickandmorty.data.models.MainResponse;
import com.example.rickandmorty.data.remote.apiservices.RickAndMortyApi;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersStorage {

    private RickAndMortyApi api;
    public CharactersStorage(RickAndMortyApi api) {
        this.api = api;
    }
    public void getCharactersByPage(int page, OnCharacterReadyCallBack callBack) {
        api.getCharacterByPage(page).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful() && response.body() !=null) {
                    callBack.onReady(response.body());

                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });

    }

    interface OnCharacterReadyCallBack {
        void onReady(MainResponse response);
    }
}
