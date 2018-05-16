package com.example.tcc.tcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class FacebookActivity extends AppCompatActivity {

    private Button f_ong, f_doacao, f_atividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        }

        f_doacao = (Button) findViewById(R.id.f_doacoes);//aqui que faz transicao de tela ao clique no btn Doações
        f_doacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacebookActivity.this, DoacoesActivity.class));
            }
        });

        //ira acessar a tela pesquisar ONG
        f_ong = (Button) findViewById(R.id.f_ong);
        f_ong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacebookActivity.this, PesquisarOngActivity.class));
            }
        });

        //ira acessar a tela pesquisar VAGA
        f_atividade = (Button) findViewById(R.id.f_atividade);
        f_atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacebookActivity.this, BuscaAtividadesActivity.class));
            }
        });

    }

    private void goLoginScreen() {
        Intent facebook= new Intent(this, MainActivity.class);
        facebook.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(facebook);
    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
}
