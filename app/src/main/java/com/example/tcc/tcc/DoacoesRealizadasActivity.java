package com.example.tcc.tcc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DoacoesRealizadasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String parametros ="";
    String url = "";
    ListView doacoesEncontradas;
    public static DoacoesCadastradas doacoesStatica;

    doacoesCadastradasAdapter doacoesCadastradasAdapter;
    List<DoacoesCadastradas> Lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacoes_realizadas);

        doacoesEncontradas = (ListView) findViewById(R.id.listViewDoacoesRealizadas);
        doacoesEncontradas.setOnItemClickListener(this);
        BuscarDaocoes();
    }

    public void BuscarDaocoes(){
        ArrayList<DoacoesCadastradas> fim = new ArrayList<DoacoesCadastradas>();

        try{
            SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
            String id_user = prefs.getString("id",null);
            String retorno = new HTTPService("buscarDoacoesCadastradas", "id_user=" + id_user).execute().get();

            JSONArray json = new JSONArray(retorno);

            for(int i = 0; i<json.length();i++){
                DoacoesCadastradas DoacoesCadastradas = new DoacoesCadastradas();

                DoacoesCadastradas.setId(json.getJSONObject(i).getString("id"));
                DoacoesCadastradas.setUser_id(json.getJSONObject(i).getString("id_user"));
                DoacoesCadastradas.setOng_id(json.getJSONObject(i).getString("ong_id"));
                DoacoesCadastradas.setNome(json.getJSONObject(i).getString("nome"));
                DoacoesCadastradas.setCategoria(json.getJSONObject(i).getString("categoria"));
                DoacoesCadastradas.setDescricao(json.getJSONObject(i).getString("descricao"));
                DoacoesCadastradas.setLevar_local(json.getJSONObject(i).getString("levar_local"));

                fim.add(DoacoesCadastradas);

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

    public void carregaResultadoLista(ArrayList<DoacoesCadastradas> lista){
        doacoesCadastradasAdapter = new doacoesCadastradasAdapter(DoacoesRealizadasActivity.this, lista);
        doacoesEncontradas.setAdapter(doacoesCadastradasAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        doacoesStatica = doacoesCadastradasAdapter.getItem(i);
        //Toast.makeText(getApplicationContext(), doacoesStatica.getCategoria(), Toast.LENGTH_LONG).show();
        Intent novaTelaDoacoes = new Intent(getApplicationContext(),VisualizarDoacoes.class);
        novaTelaDoacoes.putExtra("categoria",doacoesStatica.getCategoria());
        startActivity(novaTelaDoacoes);

    }
}