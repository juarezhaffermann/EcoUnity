package com.example.ecounity.activity.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioAutenticado {

    public static boolean isUsuarioLogado() {
        FirebaseAuth auth = ConfiguracaoBD.Firebaseautenticacao();
        return auth.getCurrentUser() != null;
    }

    public static FirebaseUser getUsuarioLogado() { // Método opcional, se necessário
        FirebaseAuth auth = ConfiguracaoBD.Firebaseautenticacao();
        return auth.getCurrentUser();
    }
}

