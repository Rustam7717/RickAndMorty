package com.example.rickandmorty.ui.adapters;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickandmorty.common.OnItemClick;
import com.example.rickandmorty.data.models.CustomCharacter;
import com.example.rickandmorty.databinding.ItemCharacterBinding;
import com.example.rickandmorty.ui.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<CustomCharacter> list = new ArrayList<>();
private OnItemClick<Integer> onItemClickListener;

    public void setOnItemClickListener(OnItemClick<Integer> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<CustomCharacter> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCharacterBinding binding = ItemCharacterBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        return new CharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(list.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
private ItemCharacterBinding binding;
        public CharacterViewHolder(@NonNull ItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(CustomCharacter customCharacter) {
            binding.tvName.setText(customCharacter.getName());
            binding.tvStatus.setText(customCharacter.getStatus());
            Glide.with(binding.getRoot())
                    .load(customCharacter.getImage())
                    .centerCrop()
                    .into(binding.ivCharacter);
        }
    }

}