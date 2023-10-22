package com.example.anotaesoficial.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anotaesoficial.databinding.AdapterFolderBinding;
import com.example.anotaesoficial.model.FolderModel;

import java.util.ArrayList;
import java.util.List;

public class AdpterFolderNome extends RecyclerView.Adapter<AdpterFolderNome.MyViewHolderFouder>{
    List<FolderModel> list;
    private int selectedButtonPosition = 0;

    public AdpterFolderNome(List<FolderModel> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolderFouder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderFouder(AdapterFolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderFouder holder, @SuppressLint("RecyclerView") int position) {
        FolderModel folderModel = list.get(position);

        holder.binding.nomeFoudAdapter.setText(folderModel.getNomeFolder());

        if (position == selectedButtonPosition) {
            holder.binding.nomeFoudAdapter.requestFocus();
        } else {
            holder.binding.nomeFoudAdapter.clearFocus();
        }

        holder.binding.nomeFoudAdapter.setOnClickListener(view -> {
            selectedButtonPosition = position;
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolderFouder extends RecyclerView.ViewHolder {
        private AdapterFolderBinding binding;
        public MyViewHolderFouder(@NonNull AdapterFolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void setSelectedButtonPosition(int position) {
        selectedButtonPosition = position;
        notifyDataSetChanged();
    }
}
