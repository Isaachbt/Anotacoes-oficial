package com.example.anotaesoficial.bancoFirebase.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {
    private static DatabaseReference database;
    private static FirebaseAuth auth;

    public static DatabaseReference getFirebaseDatabase()
    {
        if (database == null){
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            DatabaseReference reference = firebaseDatabase.getReference();
            reference.keepSynced(true);
            database = reference;
        }
        return database;
    }

    public static FirebaseAuth getFirebaseAutenticacao()
    {
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }



}
