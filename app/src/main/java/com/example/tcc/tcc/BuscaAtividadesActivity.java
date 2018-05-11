package com.example.tcc.tcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BuscaAtividadesActivity extends AppCompatActivity {

    Spinner spinner_categoria;
    Button buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_atividades);

        spinner_categoria = (Spinner) findViewById(R.id.spinner_cat_atividades);

        //Pega o array que esta la no string.xml
         ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.cat_atividades, android.R.layout.simple_spinner_item);
         spinner_categoria.setAdapter(adapter);

         //Pega qual item do spinner foi selecionado
         Button buscar = (Button) findViewById(R.id.buscar_atividades);
         buscar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String item = spinner_categoria.getSelectedItem().toString();
                 //Só pra fazer teste se mostra a opção certinho
                 Toast.makeText(getApplicationContext(), "Item escolhido:"+item, Toast.LENGTH_SHORT).show();
             }
         });

        //ira acessar a tela Atividades Encontradas
        buscar = (Button) findViewById(R.id.buscar_atividades);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
         startActivity(new Intent(BuscaAtividadesActivity.this, AtividadesEncontradasActivity.class));
          }
        });
    }
}
