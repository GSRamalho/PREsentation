package com.guilherme.getherfy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.presentation.R;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        verificaLogin();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira_tela);

        TextView loginBtn = findViewById(R.id.activity_primeira_tela_login);
        TextView cadastrarBtn = findViewById(R.id.activity_primeira_tela_cadastro);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });

        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));

            }
        });
    }

    private void verificaLogin() {
        preferences = getSharedPreferences("USER_LOGIN", 0);

        if (preferences.getString("userEmail", null) != null) {
            startActivity(new Intent(MainActivity.this, AbasActivity.class));
            finish();
        }
    }
}
