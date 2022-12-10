package com.example.anotaesoficial.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anotaesoficial.R;
import com.example.anotaesoficial.model.Anotacoes;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Anotacoes> listAnotacoes;

    public MyAdapter(List<Anotacoes> lista) {
        this.listAnotacoes = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Anotacoes notas = listAnotacoes.get(position);

         holder.txtTitulo.setText(notas.getTitulo());
         holder.campoAbreviacao.setText(notas.getcampoText());
         holder.data.setText(notas.getData());
    }

    @Override
    public int getItemCount() {
        return this.listAnotacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtTitulo,campoAbreviacao,data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.textTituloRecycler);
            campoAbreviacao = itemView.findViewById(R.id.txtBreviacao);
            data = itemView.findViewById(R.id.textDataRecycler);
        }
    }
}
