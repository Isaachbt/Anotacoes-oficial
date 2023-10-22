package com.example.anotaesoficial.model;

import android.util.Log;

import com.example.anotaesoficial.bancoFirebase.config.ConfiguracaoFirebase;
import com.example.anotaesoficial.bancoFirebase.config.ReferenciaFirebase;
import com.example.anotaesoficial.bancoFirebase.config.UsuarioFirebase;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

public class Anotacoes {


    private String nome_Folder = "Tudo";
    private String titulo;
    private String campo_Text;
    private String data;
    private String ultimaAtualizacao;
    private String chave;

    public void salvarAnotacoes()
    {
        try{
            DatabaseReference data = ConfiguracaoFirebase.getFirebaseDatabase();
            setChave(data.push().getKey());
            data.child(ReferenciaFirebase.CHAVE_ANOTACOES)
                    .child(UsuarioFirebase.getIdentificadorUsuario())
                    .child(chave)
                    .setValue(this);
        }catch (DatabaseException e)
        {
            Log.e("ERRO","Erro ao tentar salvar anotacoes");
           Log.e("ERRO", Objects.requireNonNull(e.getMessage()));
        }
    }

    public String getNome_Folder() {
        return nome_Folder;
    }

    public void setNome_Folder(String nome_Folder) {
        this.nome_Folder = nome_Folder;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCampo_Text() {
        return campo_Text;
    }

    public void setCampo_Text(String campo_Text) {
        this.campo_Text = campo_Text;
    }

    public String getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(String ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
}
