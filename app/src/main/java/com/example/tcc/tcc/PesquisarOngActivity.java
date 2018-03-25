package com.example.tcc.tcc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class PesquisarOngActivity extends AppCompatActivity {
    private Spinner op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_ong);
//ira mostrar os itens do spinner
        op = (Spinner) findViewById(R.id.spinner_ong);

        List<String> escolha = new ArrayList<>(Arrays.asList("Todas","Categoria","Palavra-Chave"));
//está usando o adapter para criar um array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                simple_spinner_item, escolha );
//especificar o layout para usar quando a lista de escolhas aparecer
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //simple.. é um layout (spinner) padrão
        //aplica o adapter para o spinner
        op.setAdapter(dataAdapter);

        op.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner categorias = (Spinner) findViewById(R.id.categoria);
                /*
                if (id == 1) {
                    //Apresentar categorias
                   categorias.setVisibility(View.VISIBLE);
                } else {
                    categorias.setVisibility(View.INVISIBLE);
                }
                */
                //ao clicar na opção categoria ira mostrar outro spinner
                List<String> categ = new ArrayList<>(Arrays.asList("Livros","Roupas"));

                categorias.setVisibility(id == 1 ? View.VISIBLE : View.INVISIBLE);
                categorias.setEnabled(id == 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
