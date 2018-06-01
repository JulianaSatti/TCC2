package com.example.tcc.tcc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

                InteresseAtividade.setId(json.getJSONObject(i).getString("id"));
                InteresseAtividade.setUser_id(json.getJSONObject(i).getString("user_id"));
                InteresseAtividade.setMensagem(json.getJSONObject(i).getString("descricao"));
                InteresseAtividade.setTelefone(json.getJSONObject(i).getString("telefone"));
                InteresseAtividade.setAtividade(json.getJSONObject(i).getString("atividade"));
                InteresseAtividade.setDescricao(json.getJSONObject(i).getString("descricao"));

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

        Intent novaTelaDoacoes = new Intent(getApplicationContext(),editarAtividadesInteresse.class);

        startActivity(novaTelaDoacoes);

    }

}
