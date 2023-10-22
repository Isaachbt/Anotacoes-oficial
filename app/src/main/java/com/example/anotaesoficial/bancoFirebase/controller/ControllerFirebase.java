package com.example.anotaesoficial.bancoFirebase.controller;

import com.example.anotaesoficial.bancoFirebase.config.ConfiguracaoFirebase;
import com.example.anotaesoficial.bancoFirebase.config.ReferenciaFirebase;
import com.example.anotaesoficial.model.Anotacoes;
import com.example.anotaesoficial.model.FolderModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ControllerFirebase {
    private static DatabaseReference databaseReference;


    public static void salvarUsuario(String email)
    {
        databaseReference = ConfiguracaoFirebase.getFirebaseDatabase().child(ReferenciaFirebase.DADOS_USUARIO);
        DatabaseReference dataReference = databaseReference;
        Map<String, String> valores = new HashMap<>();
        valores.put("Email", email);
        dataReference.setValue(valores);
        salvarFolder("Tudo");
    }

    public static boolean salvarAnotacoes(Anotacoes anotacoes)
    {
        if (anotacoes != null)
        {
            anotacoes.salvarAnotacoes();
            return true;
        }
        return false;
    }

    public static void salvarFolder(@NotNull String nomeFolder)
    {
        FolderModel folder = new FolderModel();
        folder.setNomeFolder(nomeFolder);
        folder.salvarFolder();
    }


}
