package com.example.tcc.tcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DoacoesActivity extends AppCompatActivity {
    private Button financeiro;
    private Button objemat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacoes);
//ira acessar a tela de fazer doação financeira
        financeiro = (Button) findViewById(R.id.button);
        financeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoacoesActivity.this, doacontrifinanActivity.class));
            }
        });

        //ira acessar a tela de fazer doação de objetos e materias
        objemat = (Button) findViewById(R.id.btn_doaobj);
        objemat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoacoesActivity.this, doaobjmatActivity.class));
            }
        });



    }
}
