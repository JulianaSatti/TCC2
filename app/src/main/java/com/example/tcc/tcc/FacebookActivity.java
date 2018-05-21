package com.example.tcc.tcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class FacebookActivity extends AppCompatActivity {

    private Button f_ong, f_doacao, f_atividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        }

        //ira acessar a tela Doações
        f_doacao = (Button) findViewById(R.id.f_doacoes);
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


    public boolean onCreateOptionsMenu(Menu menu) {// esse metodo que manda na action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sair_google_face, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {
            LoginManager.getInstance().logOut();
            goLoginScreen();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
