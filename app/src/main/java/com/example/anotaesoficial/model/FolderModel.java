package com.example.anotaesoficial.model;

import com.example.anotaesoficial.bancoFirebase.config.ConfiguracaoFirebase;
import com.example.anotaesoficial.bancoFirebase.config.ReferenciaFirebase;
import com.example.anotaesoficial.bancoFirebase.config.UsuarioFirebase;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

public class FolderModel {

    private String nomeFolder = "";
    private String chave;

    public void salvarFolder()
    {
        try{
        DatabaseReference data = ConfiguracaoFirebase.getFirebaseDatabase();
        setChave(data.push().getKey());
        data.child(ReferenciaFirebase.CHAVE_FOLDER)
                .child(UsuarioFirebase.getIdentificadorUsuario())
                .child(getChave())
                .setValue(this);
    }catch (DatabaseException e)
    {
        e.getStackTrace();
    }

    }

    public String getNomeFolder() {
        return nomeFolder;
    }

    public void setNomeFolder(String nomeFolder) {
        this.nomeFolder = nomeFolder;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
}
