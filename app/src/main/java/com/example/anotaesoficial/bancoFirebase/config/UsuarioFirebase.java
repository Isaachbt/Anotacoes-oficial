package com.example.anotaesoficial.bancoFirebase.config;

import androidx.annotation.NonNull;
import com.example.anotaesoficial.config.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


import java.util.Objects;

public class UsuarioFirebase {

    @NonNull
    public static String getIdentificadorUsuario(){

        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = Objects.requireNonNull(usuario.getCurrentUser()).getEmail();
        return Base64Custom.codificarBase64(Objects.requireNonNull(email));
    }

    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return usuario.getCurrentUser();
    }

    @NonNull
    public static DatabaseReference getRetornarAnotacoes(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        return firebaseRef.child(ReferenciaFirebase.CHAVE_ANOTACOES).child(getIdentificadorUsuario());
    }

    public static DatabaseReference salvarAnotacoes()
    {
        DatabaseReference ref = ConfiguracaoFirebase.getFirebaseDatabase();
        ref.child(ReferenciaFirebase.CHAVE_ANOTACOES).child(getIdentificadorUsuario());
        return ref;
    }

    public static DatabaseReference getRetornarFolder()
    {
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        return firebase.child(ReferenciaFirebase.CHAVE_FOLDER).child(getIdentificadorUsuario());
    }


}
