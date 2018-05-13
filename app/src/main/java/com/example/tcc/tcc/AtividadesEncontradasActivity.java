package com.example.tcc.tcc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class AtividadesEncontradasActivity extends AppCompatActivity {

    String parametros = "";
    String url = "";
    ListView listEncontradas;
    Spinner categoria;

    atividadesEncontradasAdapter atividadesEncontradasAdapter;
    List<atividadesEncontradas> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades_encontradas);

        //pega o item que esta na activity anterior

        //Intent busca = getIntent();

        listEncontradas = (ListView) findViewById(R.id.lv_atividades_encontradas);

        lista = new ArrayList<atividadesEncontradas>();
        atividadesEncontradasAdapter = new atividadesEncontradasAdapter(AtividadesEncontradasActivity.this, lista);
        listEncontradas.setAdapter(atividadesEncontradasAdapter);//preenchido dinamicamente

        listaAtividadesEncontradas();

    }

    @Override
    protected void onStart() {

        super.onStart();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        String text_spinner_categoria = categoria.getSelectedItem().toString();

        url = "http://35.199.87.88/api/buscarAtividades.php";
        parametros = "categoria=" + text_spinner_categoria ;
        new SolicitaDados().execute(url);


    }


    class SolicitaDados extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls){

            return Conexao.postDados(urls[0], parametros);
        }

    }


    private void listaAtividadesEncontradas(){

        Ion.with(getBaseContext())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        for (int i = 0; i <result.size(); i++){
                            JsonObject obj = result.get(i).getAsJsonObject();

                            atividadesEncontradas ae = new atividadesEncontradas();

                            ae.setId(obj.get("Id").getAsInt());
                            ae.setNomeOng(obj.get("NomeONG ").getAsString());
                            ae.setNomeAtividade(obj.get("NomeAtividade").getAsString());
                        }

                    }


                });
    }
}
