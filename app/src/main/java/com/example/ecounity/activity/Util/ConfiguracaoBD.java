package com.example.ecounity.activity.Util;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoBD {

    private static FirebaseAuth auth;

    public static FirebaseAuth Firebaseautenticacao(){
        if(auth == null){
            auth =FirebaseAuth.getInstance();
        }
        return auth;
    }
}
