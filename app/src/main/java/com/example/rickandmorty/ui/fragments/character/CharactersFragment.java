package com.example.rickandmorty.ui.fragments.character;

import android.os.Bundle;
import android.view.View;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import com.example.rickandmorty.R;
import com.example.rickandmorty.base.BaseFragment;
import com.example.rickandmorty.common.OnItemClick;
import com.example.rickandmorty.data.models.CustomCharacter;
import com.example.rickandmorty.databinding.FragmentCharactersBinding;
import com.example.rickandmorty.ui.adapters.CharacterAdapter;
import com.example.rickandmorty.ui.adapters.CharactersPagedAdapter;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharactersFragment extends BaseFragment<FragmentCharactersBinding> implements OnItemClick<Integer> {

    private CharacterAdapter adapter;
    private CharactersPagedAdapter pagedAdapter;
    private CharactersViewModel viewModel;
    private NavController controller;

    public CharactersFragment() {
        adapter = new CharacterAdapter();

    }

    private ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(new ScanContract(), new ActivityResultCallback<ScanIntentResult>() {
        @Override
        public void onActivityResult(ScanIntentResult result) {
            if (result.getContents() != null) {
                controller.navigate(CharactersFragmentDirections
                        .actionCharactersFragmentToCharacterDetailFragment(Integer.parseInt(result.getContents())));
            }
        }
    });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CharactersViewModel.class);
//        viewModel.getCharacters();
        pagedAdapter = new CharactersPagedAdapter(getDiffUtilCallBack());
        pagedAdapter.setOnItemClick(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(requireActivity(), R.id.nav_host);
    }

    @Override
    protected FragmentCharactersBinding bind() {

        return FragmentCharactersBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupObservers() {
        viewModel.pagedLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<CustomCharacter>>() {
            @Override
            public void onChanged(PagedList<CustomCharacter> customCharacters) {
                pagedAdapter.submitList(customCharacters);
            }
        });
//        viewModel.charactersLiveData.observe(getViewLifecycleOwner(), new Observer<Resource<MainResponse>>() {
//            @Override
//            public void onChanged(Resource<MainResponse> resource) {
//                switch (resource.status) {
//                    case LOADING: {
//                        binding.recyclerCharacter.setVisibility(View.GONE);
//                        binding.pbCharacter.setVisibility(View.VISIBLE);
//                        break;
//                    }
//                    case SUCCESS: {
//                        binding.recyclerCharacter.setVisibility(View.VISIBLE);
//                        binding.pbCharacter.setVisibility(View.GONE);
//                        adapter.setList(resource.data.getResults());
//                        break;
//                    }
//                    case ERROR: {
//                        Log.e("TAG", "onChanged" + resource.msg);
//
//                    }
//                }
//            }
//        });
    }

    @Override
    protected void setupListeners() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanOptions options = new ScanOptions();
                options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
                options.setPrompt("Scan character QR Code");
                options.setBeepEnabled(true);
                options.setCameraId(0);
                qrCodeLauncher.launch(options);
            }
        });
    }

    @Override
    protected void setupUI() {
        binding.recyclerCharacter.setAdapter(pagedAdapter);
    }

    @Override
    public void onItemClick(Integer characterId) {
        controller.navigate(CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(characterId));
    }

    private DiffUtil.ItemCallback<CustomCharacter> getDiffUtilCallBack() {
        return new DiffUtil.ItemCallback<CustomCharacter>() {
            @Override
            public boolean areItemsTheSame(@NonNull CustomCharacter oldItem, @NonNull CustomCharacter newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull CustomCharacter oldItem, @NonNull CustomCharacter newItem) {
                return oldItem.getId().equals(newItem.getId());
            }
        };
    }
}