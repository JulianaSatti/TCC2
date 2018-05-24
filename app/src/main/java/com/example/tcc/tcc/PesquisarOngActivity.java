package com.example.tcc.tcc;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.*;///////////////////////////////////

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.R.layout.simple_spinner_item;
import static android.widget.Toast.LENGTH_SHORT;

public class PesquisarOngActivity extends AppCompatActivity {
    private Spinner op, loc,categorias;
    private AsyncHttpClient cliente;
    private Button buscarOng;
    public static String categAtual;
    private EditText nomeOng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_ong);
//ira mostrar os itens do spinner
        op = (Spinner) findViewById(R.id.spinner_ong);
        //spinner opções de busca de ONG
        List<String> escolha = new ArrayList<>(Arrays.asList("Todas","Categoria","Nome da ONG"));
//está usando o adapter para criar um array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, simple_spinner_item, escolha );
//especificar o layout para usar quando a lista de escolhas aparecer
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //simple.. é um layout (spinner) padrão
        //aplica o adapter para o spinner
        op.setAdapter(dataAdapter);
        //opção de localização
        loc = (Spinner) findViewById(R.id.spinner_loc);

        List<String> dist = new ArrayList<>(Arrays.asList("10 km","20 km","30 km","Todas"));

        dataAdapter = new ArrayAdapter<String>(this, simple_spinner_item, dist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //simple.. é um layout (spinner) padrão
        loc.setAdapter(dataAdapter);

        //spinner com opções de categorias
        cliente = new AsyncHttpClient();

        categorias = (Spinner) findViewById(R.id.categoria);
        nomeOng = (EditText) findViewById(R.id.nome_ong);
        chamarSpinner();



        op.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorias = (Spinner) findViewById(R.id.categoria);

                EditText nome = (EditText) findViewById(R.id.nome_ong);

                //ao clicar na opção categoria ira mostrar outro spinner
                categorias.setVisibility(id == 1 ? View.VISIBLE : View.INVISIBLE);
                categorias.setEnabled(id == 1);

                nome.setVisibility(id == 2 ? View.VISIBLE : View.INVISIBLE);
                nome.setEnabled(id == 2);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//////////////////////////Ao finalizar as seleções, irá efetuar a ação de busca, de acordo com os dados/////////////

        buscarOng = (Button) findViewById(R.id.buscar_ong);
        buscarOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (op.getSelectedItem().toString()=="Nome da ONG"){ //caso selecione Nome da Ong
                    categAtual = nomeOng.getText().toString();// pega o nome da ong e joga na variavel categAtual
                }
                else {

                    if (op.getSelectedItem().toString() == "Todas") {
                        categAtual = "";
                    } else {
                        categAtual = categorias.getSelectedItem().toString();
                    }
                }

                startActivity(new Intent(PesquisarOngActivity.this, ResultadoONGActivity.class));
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

    ////////////////////////////////////////////////criei aqui pra baixo e mais a classe categoria////////////////////////////////////////////////////////////////
    private void chamarSpinner()
    {
        //String url = "http://bd-linny.000webhostapp.com/spinner.php";
        String url = "http://35.199.87.88/api/spinner_ong.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
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
        ArrayList<Categoria>lista = new ArrayList<Categoria>();
        try {
            JSONArray jArray =new JSONArray(resposta); //ira criar um novo jsonarray com os valores conforme o tamanho da tabela//
            for(int i=0;i<jArray.length();i++) {
                Categoria p = new Categoria();
                p.setCategoria(jArray.getJSONObject(i).getString("categoria")); //retorna um valor caso existir.//
                lista.add(p);
            }
            ArrayAdapter<Categoria> a = new ArrayAdapter<Categoria>(this,android.R.layout.simple_dropdown_item_1line, lista);
            categorias.setAdapter(a);


        }catch (Exception e){
            e.printStackTrace();
        }
    }




}