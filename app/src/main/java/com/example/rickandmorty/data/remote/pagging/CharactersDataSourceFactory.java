package com.example.rickandmorty.data.remote.pagging;

import androidx.paging.DataSource;

import com.example.rickandmorty.data.models.CustomCharacter;

public class CharactersDataSourceFactory extends DataSource.Factory<Integer, CustomCharacter> {

    private CharactersStorage storage;

    public CharactersDataSourceFactory(CharactersStorage storage) {
        this.storage = storage;
    }

    @Override
    public DataSource<Integer, CustomCharacter> create() {

        return new CharactersDataSource(storage);
    }
}
