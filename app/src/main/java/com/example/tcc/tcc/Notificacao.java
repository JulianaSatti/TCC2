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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.tcc.tcc.BuscaAtividadesActivity.categAtualAtividade;

public class Notificacao extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String url = "";
    ListView lv_notificacoes;
    //Spinner categoria;
    public static atividadesEncontradas atividadeStatica;
    TextView NotNaoEncontradas;

    notificacoesEncontradasAdapter notificacoesEncontradasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        NotNaoEncontradas = (TextView) findViewById(R.id.NotNaoEncontradas);

        NotNaoEncontradas.setVisibility(View.INVISIBLE);

        lv_notificacoes = (ListView) findViewById(R.id.lv_notificacoes);
        lv_notificacoes.setOnItemClickListener(this);
        Boolean TemDado = BuscarAtividades();

        if (TemDado == false) {
            NotNaoEncontradas.setVisibility(View.VISIBLE);

        }

    }

    public Boolean BuscarAtividades() {

        ArrayList<NotificacoesEncontradas> fim = new ArrayList<NotificacoesEncontradas>();
        Boolean TemDado = false;
        try {
            SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
            String id_user = prefs.getString("id",null);
            String retorno = new HTTPService("notificacoes", "user_id=" + id_user).execute().get();

            JSONArray json = new JSONArray(retorno);

            for(int i = 0; i< json.length();i++){
                NotificacoesEncontradas NotificacoesEncontradas = new NotificacoesEncontradas();

                NotificacoesEncontradas.setId(json.getJSONObject(i).getString("id"));
                NotificacoesEncontradas.setId_user(json.getJSONObject(i).getString("user_id"));
                NotificacoesEncontradas.setOng_id(json.getJSONObject(i).getString("ong_id"));
                NotificacoesEncontradas.setTipoId(json.getJSONObject(i).getString("tipo_id"));
                NotificacoesEncontradas.setTipo(json.getJSONObject(i).getString("tipo"));
                NotificacoesEncontradas.setDescricao(json .getJSONObject(i).getString("descricao"));
                NotificacoesEncontradas.setTelefone(json.getJSONObject(i).getString("telefone"));
                NotificacoesEncontradas.setVisualizada(json.getJSONObject(i).getString("visualizada"));
                NotificacoesEncontradas.setData(json.getJSONObject(i).getString("data_criacao"));



                TemDado = true;
                fim.add(NotificacoesEncontradas);
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

    public void carregaResultadoLista(ArrayList<NotificacoesEncontradas> lista){
        notificacoesEncontradasAdapter = new notificacoesEncontradasAdapter(Notificacao.this, lista);
        lv_notificacoes.setAdapter(notificacoesEncontradasAdapter);//preenchido dinamicamente
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //atividadeStatica = atividadesEncontradasAdapter.getItem(i);
        //Intent novaTela = new Intent(getApplicationContext(),VisualizarAtividades.class);
        //startActivity(novaTela);
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
            startActivity(new Intent(this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes) {
            startActivity(new Intent(this, Notificacao.class));
        }if(id==R.id.action_atividades_interessadas){
            startActivity (new Intent(this,AtividadesInteresseActivity.class));
        }if(id==R.id.logo_maos){
            startActivity(new Intent(this, TelaInicialActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


}