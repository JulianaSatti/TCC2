package com.example.tcc.tcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.tcc.tcc.AtividadesEncontradasActivity.atividadeStatica;

public class VisualizarAtividades extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_atividades);

        TextView nomeOng = (TextView) findViewById(R.id.textViewNomeOng);
        TextView nomeAtividade = (TextView) findViewById(R.id.textViewNomeAtividade);
        TextView descricao = (TextView) findViewById(R.id.textViewDescricaoAtividade);
        TextView area_interesse = (TextView) findViewById(R.id.textViewAreaInteresse);
        TextView data = (TextView) findViewById(R.id.textViewData);
        TextView local = (TextView) findViewById(R.id.textViewLocal);
        TextView voluntarios = (TextView) findViewById(R.id.textViewVoluntarios);
        TextView qtd_voluntarios = (TextView) findViewById(R.id.textViewQtdVoluntarios);
        Button interesseVoluntario = (Button) findViewById(R.id.buttonVoluntariado);

        nomeOng.setText(atividadeStatica.getNomeOng());
        nomeAtividade.setText(atividadeStatica.getNomeAtividade());
        descricao.setText(atividadeStatica.getDescricao());
        area_interesse.setText(atividadeStatica.getArea_interesse());
        data.setText(atividadeStatica.getData() + " das " + atividadeStatica.getHora_inicio() + " ás " + atividadeStatica.getHora_termino());
        local.setText(atividadeStatica.getLocal());
        voluntarios.setText(atividadeStatica.getVoluntario());
        qtd_voluntarios.setText(atividadeStatica.getQtd_voluntario());

        interesseVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VisualizarAtividades.this,InteresseVoluntarioAtividade.class));
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }if (id ==R.id.action_perfil){
            startActivity(new Intent(this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
            startActivity(new Intent(this,AlterarSenhaActivity.class));
        }
        if (id ==R.id.inicio){
            startActivity(new Intent(this,TelaInicialActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
