package com.example.tcc.tcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

public class BuscaAtividadesActivity extends AppCompatActivity {

    Spinner spinner_atividades;
    Button buscar;
    EditText descricaoAtividade;
    Spinner categorias;
    public static String categAtualAtividade;
    private AsyncHttpClient clienteAtividade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_atividades);

        spinner_atividades = (Spinner) findViewById(R.id.spinner_atividades);
        descricaoAtividade = (EditText) findViewById(R.id.descricao_atividade);
        categorias = (Spinner) findViewById(R.id.spinner_categoria_atividade);


        this.clienteAtividade = new AsyncHttpClient();

        chamarSpinner();

        //Pega o array que esta la no string.xml
         ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.opcoesAtividade, android.R.layout.simple_spinner_dropdown_item);
        spinner_atividades.setAdapter(adapter);

         //Pega qual item do spinner foi selecionado
         Button buscar = (Button) findViewById(R.id.buscar_atividades);
         buscar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if (spinner_atividades.getSelectedItem().toString().equals("Descrição da Atividade")) {
                     categAtualAtividade = descricaoAtividade.getText().toString();
                 }
                 else {

                 if (spinner_atividades.getSelectedItem().toString().equals("Todas")){
                     categAtualAtividade = "";
                     } else {
                         categAtualAtividade = categorias.getSelectedItem().toString();
                     }
                 }

                 //Toast.makeText(getApplicationContext(),categAtualAtividade,Toast.LENGTH_LONG).show();
                 startActivity(new Intent(BuscaAtividadesActivity.this, AtividadesEncontradasActivity.class));
             }
         });

         spinner_atividades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 categorias.setVisibility(id == 1 ? View.VISIBLE : View.INVISIBLE);
                 categorias.setEnabled(id == 1);

                 descricaoAtividade.setVisibility(id == 2 ? View.VISIBLE : View.INVISIBLE);
                 descricaoAtividade.setEnabled(id == 2);

             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {
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
            startActivity(new Intent(this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
            startActivity(new Intent(this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes){
            startActivity(new Intent(this,Notificacao.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void chamarSpinner() {
        String url = "http://35.199.87.88/api/spinner_atividade.php";
        this.clienteAtividade.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    carregarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void carregarSpinner(String resposta) {
        ArrayList<Categoria> lista = new ArrayList<Categoria>();
        try {
            JSONArray jArray =new JSONArray(resposta);
            for(int i=0;i<jArray.length();i++) {
                Categoria p = new Categoria();
                // nome do campo do BD que vai retornar na pesquisa
                p.setCategoria(jArray.getJSONObject(i).getString("area_interesse"));
                lista.add(p);
            }
            ArrayAdapter<Categoria> a = new ArrayAdapter<Categoria>(this,android.R.layout.simple_dropdown_item_1line, lista);
            categorias.setAdapter(a);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
