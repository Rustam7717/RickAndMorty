package com.example.rickandmorty.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.rickandmorty.common.Resource;
import com.example.rickandmorty.data.models.CustomCharacter;
import com.example.rickandmorty.data.models.MainResponse;
import com.example.rickandmorty.data.remote.apiservices.RickAndMortyApi;
import com.example.rickandmorty.ui.activities.App;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {


    private RickAndMortyApi api;

    @Inject
    public MainRepository(RickAndMortyApi api) {
        this.api = api;

    }

    public LiveData<Resource<MainResponse>> getCharacters() {
        MutableLiveData<Resource<MainResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getCharacters().enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(Resource.success(response.body()));
                } else {
                    liveData.setValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<Resource<CustomCharacter>> getCharacter(int id) {
        MutableLiveData<Resource<CustomCharacter>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getCharacter(id).enqueue(new Callback<CustomCharacter>() {
            @Override
            public void onResponse(Call<CustomCharacter> call, Response<CustomCharacter> response) {
                if (response.isSuccessful() && response.body() !=null) {
            liveData.setValue(Resource.success(response.body()));
                }else {
                    liveData.setValue(Resource.error(response.message(), null));

                }
            }

            @Override
            public void onFailure(Call<CustomCharacter> call, Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));

            }
        });
        return liveData;
    }

}
