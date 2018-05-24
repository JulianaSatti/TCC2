package com.example.tcc.tcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import static com.example.tcc.tcc.DoacoesRealizadasActivity.doacoesStatica;


public class VisualizarDoacoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_doacoes);

        Bundle extras = savedInstanceState;

        TextView categoriaDoacao = (TextView) findViewById(R.id.textViewCategoriaDaocao);
        EditText nomeDaocao = (EditText) findViewById(R.id.nom_obj);
        EditText descricaoDaocao = (EditText) findViewById(R.id.desc_obj_visualiza);
        RadioButton Ret_Sim = (RadioButton) findViewById(R.id.radio_sim_visualiza);
        RadioButton Ret_Nao = (RadioButton) findViewById(R.id.radio_nao_visualiza);

        categoriaDoacao.setText(doacoesStatica.getCategoria());
        nomeDaocao.setText(doacoesStatica.getNome());
        descricaoDaocao.setText(doacoesStatica.getDescricao());
        if(doacoesStatica.getLevar_local().trim().toUpperCase().equals("SIM")){
            Ret_Sim.setText("Sim");

        } else {
            Ret_Nao.setActivated(true);
            Ret_Nao.setText("Não");
        }


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
            startActivity(new Intent(this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
            startActivity(new Intent(this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes){
            startActivity(new Intent(this,Notificacao.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
