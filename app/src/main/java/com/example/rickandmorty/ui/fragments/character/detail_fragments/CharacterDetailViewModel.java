package com.example.rickandmorty.ui.fragments.character.detail_fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.rickandmorty.base.BaseFragment;
import com.example.rickandmorty.common.Resource;
import com.example.rickandmorty.data.models.CustomCharacter;
import com.example.rickandmorty.data.repositories.MainRepository;
import com.example.rickandmorty.databinding.FragmentCharacterDetailBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CharacterDetailViewModel extends ViewModel {

    private MainRepository repository;
    public LiveData<Resource<CustomCharacter>> liveData;

    @Inject
    public CharacterDetailViewModel(MainRepository repository) {
        this.repository = repository;
    }

    public void getCharacterById(int id) {
        liveData = repository.getCharacter(id);
    }
}






