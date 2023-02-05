package com.example.anotaesoficial.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.anotaesoficial.databinding.AdapterRecyclerBinding;
import com.example.anotaesoficial.model.Anotacoes;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Anotacoes> listAnotacoes;

    public MyAdapter(List<Anotacoes> lista) {
        this.listAnotacoes = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new MyViewHolder(AdapterRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Anotacoes notas = listAnotacoes.get(position);

         holder.binding.textTituloRecycler.setText(notas.getTitulo());
         holder.binding.txtBreviacao.setText(notas.getcampoText());
         holder.binding.textDataRecycler.setText(notas.getData());
    }

    @Override
    public int getItemCount() {
        return this.listAnotacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private AdapterRecyclerBinding binding;

        public MyViewHolder(AdapterRecyclerBinding b) {
            super(b.getRoot());
            this.binding = b;

        }
    }
}
