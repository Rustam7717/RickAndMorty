package com.example.rickandmorty.ui.fragments.character.detail_fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.rickandmorty.R;
import com.example.rickandmorty.base.BaseFragment;
import com.example.rickandmorty.common.Resource;
import com.example.rickandmorty.data.models.CustomCharacter;
import com.example.rickandmorty.databinding.FragmentCharacterDetailBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterDetailFragment extends BaseFragment<FragmentCharacterDetailBinding> {
    private CharacterDetailViewModel viewModel;
    private CharacterDetailFragmentArgs args;

    public CharacterDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CharacterDetailViewModel.class);
        args = CharacterDetailFragmentArgs.fromBundle(getArguments());
        viewModel.getCharacterById(args.getCharacterId());
    }

    @Override
    protected FragmentCharacterDetailBinding bind() {
        return FragmentCharacterDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupObservers() {
        viewModel.liveData.observe(getViewLifecycleOwner(), new Observer<Resource<CustomCharacter>>() {
            @Override
            public void onChanged(Resource<CustomCharacter> resource) {
                switch (resource.status) {
                    case LOADING: {
                        binding.ivAvatar.setVisibility(View.GONE);
                        binding.tvName.setVisibility(View.GONE);
                        binding.tvStatus.setVisibility(View.GONE);
                        binding.pbCharacter.setVisibility(View.VISIBLE);
                        break;
                    }
                    case SUCCESS: {
                        binding.ivAvatar.setVisibility(View.VISIBLE);
                        binding.tvName.setVisibility(View.VISIBLE);
                        binding.tvStatus.setVisibility(View.VISIBLE);
                        binding.pbCharacter.setVisibility(View.GONE);
                        binding.tvName.setText(resource.data.getName());
                        binding.tvGender.setText(resource.data.getGender());
                        binding.tvSpices.setText(resource.data.getSpecies());
                        binding.tvStatus.setText(resource.data.getStatus());
                        Glide.with(binding.getRoot()).load(resource.data.getImage())
                                .centerCrop().into(binding.ivAvatar);
                        generateQR(resource.data.getId());

                        break;
                    }
                    case ERROR: {
                        binding.ivAvatar.setVisibility(View.GONE);
                        binding.tvName.setVisibility(View.GONE);
                        binding.tvStatus.setVisibility(View.GONE);
                        binding.pbCharacter.setVisibility(View.GONE);
                        Snackbar.make(binding.getRoot(), resource.msg, BaseTransientBottomBar
                                .LENGTH_LONG).show();
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void setupListeners() {

    }

    @Override
    protected void setupUI() {

    }

    private void generateQR(int id) {
        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrBitmap = encoder.encodeBitmap(
                    String.valueOf(id),
                    BarcodeFormat.QR_CODE,
                    220,
                    220
            );
            binding.ivQr.setImageBitmap(qrBitmap);
        } catch (Exception e) {

        }
    }
}
