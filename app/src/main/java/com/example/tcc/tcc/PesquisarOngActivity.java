package com.example.tcc.tcc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class PesquisarOngActivity extends AppCompatActivity {
    private Spinner op,loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_ong);
//ira mostrar os itens do spinner
        op = (Spinner) findViewById(R.id.spinner_ong);

        List<String> escolha = new ArrayList<>(Arrays.asList("Todas","Categoria","Nome da ONG"));
//está usando o adapter para criar um array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                simple_spinner_item, escolha );
//especificar o layout para usar quando a lista de escolhas aparecer
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //simple.. é um layout (spinner) padrão
        //aplica o adapter para o spinner
        op.setAdapter(dataAdapter);

        loc = (Spinner) findViewById(R.id.spinner_loc);
        //opção de localização
        List<String> dist = new ArrayList<>(Arrays.asList("10 km","20 km","30 km","Todas"));
//está usando o adapter para criar um array
        dataAdapter = new ArrayAdapter<String>(this,
                simple_spinner_item, dist);
//especificar o layout para usar quando a lista de escolhas aparecer
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //simple.. é um layout (spinner) padrão
        //aplica o adapter para o spinner
        loc.setAdapter(dataAdapter);


        op.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner categorias = (Spinner) findViewById(R.id.categoria);
                EditText nome = (EditText) findViewById(R.id.nome_ong);


                categorias.setVisibility(id == 1 ? View.VISIBLE : View.INVISIBLE);
                categorias.setEnabled(id == 1);

                nome.setVisibility(id == 2 ? View.VISIBLE : View.INVISIBLE);
                nome.setEnabled(id == 2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
