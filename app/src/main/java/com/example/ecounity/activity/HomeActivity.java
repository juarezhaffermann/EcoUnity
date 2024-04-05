package com.example.ecounity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ecounity.R;
import com.example.ecounity.activity.Util.ConfiguracaoBD;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = ConfiguracaoBD.Firebaseautenticacao();
    }
public void logout(View view) {
        try{
            auth.signOut();

        } catch (Exception e) {
            Toast.makeText(this, "Erro ao fazer logout. Tente novamente.", Toast.LENGTH_SHORT).show();
        }

}

}
