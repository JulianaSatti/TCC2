package com.example.tcc.tcc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        nomeOng.setText(atividadeStatica.getNomeOng());
        nomeAtividade.setText(atividadeStatica.getNomeAtividade());
        descricao.setText(atividadeStatica.getDescricao());
        area_interesse.setText(atividadeStatica.getArea_interesse());
        data.setText(atividadeStatica.getData() + " das " + atividadeStatica.getHora_inicio() + " ás " + atividadeStatica.getHora_termino());
        local.setText(atividadeStatica.getLocal());
        voluntarios.setText(atividadeStatica.getVoluntario());
        qtd_voluntarios.setText(atividadeStatica.getQtd_voluntario());

    }
}
