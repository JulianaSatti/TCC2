
package com.example.tcc.tcc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewId;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NecessidadesONGActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String url = "";
    ListView listNecessidades;
    public static Necessidade necessidades;
    TextView textNecessidadeNaoEncontrada;

    NecessidadesEncontradasAdapter NecessidadesEncontradasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_necessidades_ong);

        textNecessidadeNaoEncontrada = (TextView) findViewById(R.id.textViewNecessidadeNaoEncontradas);

        textNecessidadeNaoEncontrada.setVisibility(View.INVISIBLE);

        listNecessidades = (ListView) findViewById(R.id.lv_necessidades_ong);
        listNecessidades.setOnItemClickListener(this);
        Boolean TemDado = BuscarNecessidades();

        if(TemDado==false){
            textNecessidadeNaoEncontrada.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Nenhuma necessidade encontrada", Toast.LENGTH_SHORT).show();
        }

    }

    public Boolean BuscarNecessidades(){
        ArrayList<Necessidade> fim = new ArrayList<Necessidade>();
        Boolean TemDado = false;
        try{
            String retorno = new HTTPService("buscarNecessidades", "ong_id="+lerListViewId).execute().get();

            JSONArray json = new JSONArray(retorno);

            for (int i=0; i<json.length(); i++){
                Necessidade necessidade = new Necessidade();

                necessidade.setOng_id(json.getJSONObject(i).getString("ong_id"));
                necessidade.setId(json.getJSONObject(i).getString("id"));
                necessidade.setNecessidade(json.getJSONObject(i).getString("objeto"));
                necessidade.setDescricao(json.getJSONObject(i).getString("descricao"));

                TemDado = true;
                fim.add(necessidade);
            }
            carregaResultadoLista(fim);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return TemDado;
    }

    public void carregaResultadoLista(ArrayList<Necessidade> lista){
        NecessidadesEncontradasAdapter = new NecessidadesEncontradasAdapter(NecessidadesONGActivity.this,lista);
        listNecessidades.setAdapter(NecessidadesEncontradasAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        necessidades = NecessidadesEncontradasAdapter.getItem(i);
        Intent doar = new Intent (getApplicationContext(), CadastroDoacoesONG.class);
        startActivity(doar);
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
