package com.example.tcc.tcc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import static com.example.tcc.tcc.BuscaAtividadesActivity.categAtualAtividade;


public class AtividadesEncontradasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String parametros = "";
    String url = "";
    ListView listEncontradas;
    Spinner categoria;
    public static atividadesEncontradas atividadeStatica;

    atividadesEncontradasAdapter atividadesEncontradasAdapter;
    List<atividadesEncontradas> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades_encontradas);

        //pega o item que esta na activity anterior

        //Intent busca = getIntent();

        listEncontradas = (ListView) findViewById(R.id.lv_atividades_encontradas);
        listEncontradas.setOnItemClickListener(this);
        BuscarAtividades();
    }

    public void BuscarAtividades() {
        Gson gson = new Gson();
        ArrayList<atividadesEncontradas> fim = new ArrayList<atividadesEncontradas>();
        try {
            //Toast.makeText(this,"Pesquisando por:" + categAtualAtividade,Toast.LENGTH_LONG).show();
            String retorno = new HTTPService("buscarAtividades", "categoria="+categAtualAtividade).execute().get();

            JSONArray json = new JSONArray(retorno);

            for(int i = 0; i< json.length();i++){
                atividadesEncontradas atividadesEncontradas = new atividadesEncontradas();

                atividadesEncontradas.setId_user(json.getJSONObject(i).getString("id_user"));
                atividadesEncontradas.setId(json.getJSONObject(i).getString("id"));
                atividadesEncontradas.setNomeOng(json.getJSONObject(i).getString("nome_fantasia"));
                atividadesEncontradas.setNomeAtividade(json.getJSONObject(i).getString("atividade"));
                atividadesEncontradas.setDescricao(json .getJSONObject(i).getString("descricao"));
                atividadesEncontradas.setArea_interesse(json.getJSONObject(i).getString("area_interesse"));
                atividadesEncontradas.setData(json.getJSONObject(i).getString("data"));
                atividadesEncontradas.setHora_inicio(json.getJSONObject(i).getString("hora_inicio"));
                atividadesEncontradas.setHora_termino(json.getJSONObject(i).getString("hora_termino"));
                atividadesEncontradas.setLocal(json.getJSONObject(i).getString("local"));
                atividadesEncontradas.setVoluntario(json.getJSONObject(i).getString("voluntario"));
                atividadesEncontradas.setQtd_voluntario(json.getJSONObject(i).getString("qtd_voluntario"));


                fim.add(atividadesEncontradas);
            }

            carregaResultadoLista(fim);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void carregaResultadoLista(ArrayList<atividadesEncontradas> lista){
        atividadesEncontradasAdapter = new atividadesEncontradasAdapter(AtividadesEncontradasActivity.this, lista);
        listEncontradas.setAdapter(atividadesEncontradasAdapter);//preenchido dinamicamente
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        atividadeStatica = atividadesEncontradasAdapter.getItem(i);
        Intent novaTela = new Intent(getApplicationContext(),VisualizarAtividades.class);
        startActivity(novaTela);
    }
    /*
    @Override
    protected void onStart() {

        super.onStart();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        String text_spinner_categoria = categoria.getSelectedItem().toString();

        url = "http://35.199.87.88/api/buscarAtividades.php";
        parametros = "categoria=" + text_spinner_categoria ;
        AsyncTask<String, Void, String> json = new SolicitaDados().execute(url);


    }

    /*
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
    }*/
}
