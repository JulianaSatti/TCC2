package com.example.tcc.tcc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
    TextView textAtividadeNaoEncontrada;

    atividadesEncontradasAdapter atividadesEncontradasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades_encontradas);

        textAtividadeNaoEncontrada = (TextView) findViewById(R.id.textViewAtividadesNaoEncontradas);

        textAtividadeNaoEncontrada.setVisibility(View.INVISIBLE);

        listEncontradas = (ListView) findViewById(R.id.lv_atividades_encontradas);
        listEncontradas.setOnItemClickListener(this);
        Boolean TemDado = BuscarAtividades();

        if (TemDado == false) {
            textAtividadeNaoEncontrada.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Nenhuma Atividade Encontrada", Toast.LENGTH_SHORT).show();
        }

    }

    public Boolean BuscarAtividades() {

        ArrayList<atividadesEncontradas> fim = new ArrayList<atividadesEncontradas>();
        Boolean TemDado = false;
        try {

            String retorno = new HTTPService("buscarAtividades", "categoria="+categAtualAtividade).execute().get();

            JSONArray json = new JSONArray(retorno);

            for(int i = 0; i< json.length();i++){
                atividadesEncontradas atividadesEncontradas = new atividadesEncontradas();

                atividadesEncontradas.setId_user(json.getJSONObject(i).getString("id_user"));
                atividadesEncontradas.setOng_id(json.getJSONObject(i).getString("ong_id"));
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

                TemDado = true;
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
        return TemDado;
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
            startActivity (new Intent(this,Notificacao.class));
        }
        return super.onOptionsItemSelected(item);
    }


}
