package com.guilherme.getherfy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.guilherme.presentation.R;

public class PrimeiraTelaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira_tela);

        TextView loginBtn = findViewById(R.id.activity_primeira_tela_login);
        TextView cadastrarBtn = findViewById(R.id.activity_primeira_tela_cadastro);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrimeiraTelaActivity.this, LoginActivity.class));

            }
        });

        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrimeiraTelaActivity.this, CadastrarActivity.class));

            }
        });
    }
}
