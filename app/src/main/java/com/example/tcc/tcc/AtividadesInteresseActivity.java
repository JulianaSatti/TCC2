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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.tcc.tcc.AtividadesEncontradasActivity.atividadeStatica;

public class AtividadesInteresseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView AtividadesInteresse;
    public static InteresseAtividade interesseAtividadeStatica;

    AtividadesInteresseAdapter AtividadesInteresseAdapter;
    List<InteresseAtividade> Lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades_interesse);

        AtividadesInteresse = (ListView)findViewById(R.id.listViewAtividadesInteresse);
        AtividadesInteresse.setOnItemClickListener(this);
        BuscarAtividadesInteresse();

    }

    private void BuscarAtividadesInteresse() {
        ArrayList<InteresseAtividade> fim = new ArrayList<>();

        try {
            SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
            String user_id = prefs.getString("id",null);



            String retorno = new HTTPService("BuscarAtividadeInteresse", "user_id=" + user_id).execute().get();

            JSONArray json = new JSONArray(retorno);

            for (int i = 0; i < json.length(); i++) {
                InteresseAtividade InteresseAtividade = new InteresseAtividade();

                InteresseAtividade.setId(json.getJSONObject(i).getString("id_notificacao"));
                InteresseAtividade.setUser_id(json.getJSONObject(i).getString("user_id"));
                InteresseAtividade.setMensagem(json.getJSONObject(i).getString("descricao_notificacao"));
                InteresseAtividade.setTelefone(json.getJSONObject(i).getString("telefone"));
                InteresseAtividade.setAtividade(json.getJSONObject(i).getString("atividade"));
                InteresseAtividade.setDescricao(json.getJSONObject(i).getString("descricao_atividade"));

                fim.add(InteresseAtividade);
            }
            carregaResultadoLista(fim);

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    public void carregaResultadoLista(ArrayList<InteresseAtividade> lista){
        AtividadesInteresseAdapter = new AtividadesInteresseAdapter(AtividadesInteresseActivity.this, lista);
        AtividadesInteresse.setAdapter(AtividadesInteresseAdapter);
    }


        @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        interesseAtividadeStatica = AtividadesInteresseAdapter.getItem(position);

        Intent novaTelaAtividades = new Intent(getApplicationContext(),editarAtividadesInteresse.class);

        startActivity(novaTelaAtividades);

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
