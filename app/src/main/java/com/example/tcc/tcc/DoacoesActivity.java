package com.example.tcc.tcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Juliana on 25/02/2018.
 */

public class DoacoesActivity extends AppCompatActivity {
    private Button doarealizadas;
    private Button objemat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacoes);

        //ira acessar a tela de fazer doação financeira
        doarealizadas = (Button) findViewById(R.id.btn_doa_realizadas);
        doarealizadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoacoesActivity.this, DoacoesRealizadasActivity.class));
            }
        });

        //ira acessar a tela de fazer doação de objetos e materias
        objemat = (Button) findViewById(R.id.btn_doaobj);
        objemat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoacoesActivity.this, DoaMatActivity.class));
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {// esse metodo que manda na action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tela_inicial, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {

            finish();
            return true;

        }if (id ==R.id.action_perfil){
            startActivity(new Intent(DoacoesActivity.this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
            startActivity(new Intent(DoacoesActivity.this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes){
            startActivity(new Intent(DoacoesActivity.this,Notificacao.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
