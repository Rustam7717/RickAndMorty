package com.example.rickandmorty.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.rickandmorty.common.OnItemClick;
import com.example.rickandmorty.data.models.CustomCharacter;
import com.example.rickandmorty.databinding.ItemCharacterBinding;

public class CharactersPagedAdapter extends PagedListAdapter<CustomCharacter, CharacterAdapter.CharacterViewHolder> {
    private OnItemClick<Integer> onItemClick;

    public void setOnItemClick(OnItemClick<Integer> onItemClick) {
        this.onItemClick = onItemClick;
    }

    public CharactersPagedAdapter(@NonNull DiffUtil.ItemCallback<CustomCharacter> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CharacterAdapter.CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCharacterBinding binding = ItemCharacterBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);

        return new CharacterAdapter.CharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.CharacterViewHolder holder, int position) {
        holder.onBind(getItem(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(getItem(holder.getAdapterPosition()).getId());
            }
        });
    }
}
