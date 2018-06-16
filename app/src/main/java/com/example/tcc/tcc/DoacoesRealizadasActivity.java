package com.example.tcc.tcc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DoacoesRealizadasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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
        BuscarDoacoes();
    }

    public void BuscarDoacoes(){
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
                DoacoesCadastradas.setItemDoado(json.getJSONObject(i).getString("doado"));

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
        Intent novaTelaDoacoes = new Intent(getApplicationContext(),editarDoacoes.class);
        novaTelaDoacoes.putExtra("categoria",doacoesStatica.getCategoria());
        startActivity(novaTelaDoacoes);

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
            //ao clicar em sair irá sair do aplicativo, apenas o finish irá sair apenas da tela atual e não do aplicativo.
            startActivity(new Intent(this,MainActivity.class));

            finish();
            return true;

        }if (id ==R.id.action_perfil){
            startActivity(new Intent(this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
<<<<<<< HEAD
            startActivity(new Intent(this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes) {
            startActivity(new Intent(this, Notificacao.class));
=======
            startActivity(new Intent(DoacoesRealizadasActivity.this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes){
            startActivity(new Intent(DoacoesRealizadasActivity.this,Notificacao.class));
>>>>>>> b528da63dca334c41986860ee41f724771bab6ae
        }if(id==R.id.action_atividades_interessadas){
            startActivity (new Intent(this,AtividadesInteresseActivity.class));
        }if(id==R.id.logo_maos){
            startActivity(new Intent(this, TelaInicialActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

