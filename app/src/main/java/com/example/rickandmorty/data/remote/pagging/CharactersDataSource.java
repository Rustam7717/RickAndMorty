package com.example.rickandmorty.data.remote.pagging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.rickandmorty.data.models.CustomCharacter;
import com.example.rickandmorty.data.models.MainResponse;

import java.util.List;

import javax.inject.Inject;

public class CharactersDataSource extends PageKeyedDataSource<Integer, CustomCharacter> {

    private CharactersStorage storage;

    @Inject
    public CharactersDataSource(CharactersStorage storage) {
        this.storage = storage;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, CustomCharacter> callback) {
    storage.getCharactersByPage(0, new CharactersStorage.OnCharacterReadyCallBack() {
        @Override
        public void onReady(MainResponse response) {
            if (response !=null) {
                List<CustomCharacter> result = response.getResults();
                String[] splitedNextPageUrl =
                        response.getInfo().getNext().split("=");
                Integer nextPage = Integer.parseInt(splitedNextPageUrl[1]);
                callback.onResult(result,null, nextPage);
            }
        }
    });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CustomCharacter> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, CustomCharacter> callback) {
    storage.getCharactersByPage(params.key, new CharactersStorage.OnCharacterReadyCallBack() {
        @Override
        public void onReady(MainResponse response) {
            List<CustomCharacter> result = response.getResults();
            String[] splitedNextPageUrl =
                    response.getInfo().getNext().split("=");
            Integer nextPage = Integer.parseInt(splitedNextPageUrl[1]);
            callback.onResult(result,nextPage);
        }
    });
    }
}
