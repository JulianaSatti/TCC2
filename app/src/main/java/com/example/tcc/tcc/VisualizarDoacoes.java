package com.example.tcc.tcc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
