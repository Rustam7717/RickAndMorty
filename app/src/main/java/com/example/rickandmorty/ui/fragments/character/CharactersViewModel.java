package com.example.rickandmorty.ui.fragments.character;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.rickandmorty.common.Resource;
import com.example.rickandmorty.data.models.CustomCharacter;
import com.example.rickandmorty.data.models.MainResponse;
import com.example.rickandmorty.data.remote.pagging.CharactersDataSource;
import com.example.rickandmorty.data.remote.pagging.CharactersDataSourceFactory;
import com.example.rickandmorty.data.remote.pagging.CharactersStorage;
import com.example.rickandmorty.data.repositories.MainRepository;
import com.example.rickandmorty.ui.activities.App;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CharactersViewModel extends ViewModel {

    public LiveData<Resource<MainResponse>> charactersLiveData;
    private MainRepository repository;
    private CharactersDataSourceFactory dataSourceFactory;
    public LiveData<PagedList<CustomCharacter>> pagedLiveData;

    PagedList.Config config = new PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .build();
    @Inject
    public CharactersViewModel(MainRepository repository, CharactersStorage storage) {
        this.repository = repository;
        dataSourceFactory = new CharactersDataSourceFactory(storage);
        pagedLiveData = new LivePagedListBuilder<>(dataSourceFactory, config).build();


    }

    public void getCharacters() {
        charactersLiveData = repository.getCharacters();
    }


}
