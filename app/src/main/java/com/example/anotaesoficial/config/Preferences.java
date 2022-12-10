package com.example.anotaesoficial.config;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final String ARQUIVO_PREFERENCIA = "ConfigEditor";
    private static final String CHAVE_TEXTO = "cortext";
    private static final String CHAVE_FUNDO = "corFundo";
    private static final String CHAVE_SIZE = "tamanhoFonte";

    public Preferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        editor = preferences.edit();

    }

    public void salvarCorTexto(String corTexto){
        editor.putString(CHAVE_TEXTO,corTexto);
        editor.commit();
    }

    public void salvarTamanhoText(String size){
        editor.putString(CHAVE_SIZE,size);
        editor.commit();
    }

    public void salvarCorFundo(String corfundo){
        editor.putString(CHAVE_FUNDO,corfundo);
        editor.commit();
    }

    public String recuperarCorTexto(){
        return preferences.getString(CHAVE_TEXTO,"");
    }

    public String recuperarCorFundo(){
        return preferences.getString(CHAVE_FUNDO,"");
    }

    public String recuperarTamanhoFont(){
        return preferences.getString(CHAVE_SIZE,"");
    }
}
