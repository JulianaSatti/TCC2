package com.example.tcc.tcc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewAgencia;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewBanco;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewCat;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewCidade;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewCnpj_Conta;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewConta;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewEmail;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewEnd;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewFoto;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewId;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewNum;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewHorario;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewRazao;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewSite;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewTel;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewUF;
import static com.example.tcc.tcc.BuscaAtividadesActivity.categAtualAtividade;
import static com.example.tcc.tcc.AtividadesEncontradasActivity.atividadeStatica;


public class detalhesOngActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    private TextView detalhesCategoria;
    private TextView detalhesRazao;
    private TextView detalhesEnd;
    private TextView detalhesEmail, detalhesNum;
    private TextView detalhesTel, detalhesCidades;
    private TextView detalhesSite, detalhesHorario;
    private TextView detalhesConta, detalhesBanco;
    private TextView detalhesAtiv,detalhesCNPJ;
    private TextView queroDoar;
    private SmartImageView smartImageView;
    private ListView lv_contas;
    TextView contaNaoEncontrada;
    bancosEncontradosAdapter bancosEncontradosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_ong);


        //////////////////////////////////////////////////////////////////////////////////////////
        detalhesCNPJ = (TextView) findViewById(R.id.detCNPJ);
        detalhesCNPJ.setText("CNPJ: "+lerListViewCnpj_Conta);


        //mexi aqui
        queroDoar =(TextView) findViewById(R.id.btn_doar);
        queroDoar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                               //Toast.makeText(getApplicationContext(),categAtualAtividade,Toast.LENGTH_LONG).show();
                startActivity(new Intent(detalhesOngActivity.this, CadastroDoacoesONG.class));
            }
        });

        contaNaoEncontrada = (TextView) findViewById(R.id.ContasNaoEncontradas);

        contaNaoEncontrada.setVisibility(View.INVISIBLE);

        lv_contas = (ListView) findViewById(R.id.lv_contas);
        // lv_contas.setOnItemClickListener(this);

        Boolean TemDado = BuscarContas();
        if (TemDado == false) {
            contaNaoEncontrada.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Nenhuma Conta Encontrada", Toast.LENGTH_SHORT).show();

        }else{
            detalhesCNPJ.setVisibility(View.VISIBLE);
        }


///////////Carrega todos os dados das variaveis globais//////////////////////
        detalhesCategoria = (TextView) findViewById(R.id.detCat);
        detalhesCategoria.setText("Categoria: "+lerListViewCat);
        detalhesRazao = (TextView) findViewById(R.id.detRazao);
        detalhesRazao.setText("Razão Social: "+lerListViewRazao);
        detalhesEnd = (TextView) findViewById(R.id.detEnd);
        detalhesEnd.setText("Endereço: "+lerListViewEnd+", " + lerListViewNum);
        //detalhesNum = (TextView) findViewById(R.id.detNum);
        //detalhesNum.setText(" "+lerListViewNum);
        detalhesEmail = (TextView) findViewById(R.id.detEmail);
        detalhesEmail.setText("E-mail: "+lerListViewEmail);
        detalhesTel = (TextView) findViewById(R.id.detTel);
        detalhesTel.setText("Telefone: "+lerListViewTel);
        detalhesCidades = (TextView) findViewById(R.id.detCidade);
        detalhesCidades.setText("Cidade: "+lerListViewCidade + " - " + lerListViewUF);
        detalhesHorario = (TextView) findViewById(R.id.detHorario);
        detalhesHorario.setText("Horário: "+lerListViewHorario);
        detalhesSite = (TextView) findViewById(R.id.detSite);
        detalhesSite.setText("Página: "+lerListViewSite);


        detalhesAtiv = (TextView) findViewById(R.id.detAtiv);
        detalhesAtiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categAtualAtividade = lerListViewRazao;

                //Toast.makeText(getApplicationContext(),categAtualAtividade,Toast.LENGTH_LONG).show();
                startActivity(new Intent(detalhesOngActivity.this, AtividadesEncontradasActivity.class));
            }
        });
        // detalhesConta = (TextView) findViewById(R.id.detConta);
        /*
        detalhesConta.setText("CNPJ:  "+lerListViewCnpj_Conta + "   Conta: "+ lerListViewConta);
        detalhesBanco = (TextView) findViewById(R.id.detBanco);
        detalhesBanco.setText("Ag.: "+lerListViewAgencia + "  -  "+lerListViewBanco);
*/


        /////Seleciona a foto e exibe////////////////////
        smartImageView=(SmartImageView)findViewById(R.id.foto2);

        String urlFinal="http://35.199.87.88/api/imagens/"+"ong.jpg";
        Rect rect=new Rect(smartImageView.getLeft(),// faz o retangulo de margem lado esq
                smartImageView.getTop(),// faz o retangulo de margem lado dir
                smartImageView.getRight(),// faz o retangulo de margem lado sup
                smartImageView.getBottom());// faz o retangulo de margem lado inf
        smartImageView.setImageUrl(urlFinal, rect);// faz o retangulo de margem

    }


    public Boolean BuscarContas() {

        ArrayList<atividadesEncontradas> fim = new ArrayList<atividadesEncontradas>();
        Boolean TemDado = false;
        try {

            String retorno = new HTTPService("resultado_contas", "razao=" + lerListViewRazao).execute().get();

            JSONArray json = new JSONArray(retorno);

            for(int i = 0; i< json.length();i++){
                atividadesEncontradas atividadesEncontradas = new atividadesEncontradas();

                //atividadesEncontradas.setId_user(json.getJSONObject(i).getString("razao_social"));
                atividadesEncontradas.setNomeAtividade(json.getJSONObject(i).getString("banco"));
                atividadesEncontradas.setDescricao(json.getJSONObject(i).getString("agencia"));
                atividadesEncontradas.setArea_interesse(json.getJSONObject(i).getString("conta"));



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
        bancosEncontradosAdapter = new bancosEncontradosAdapter(detalhesOngActivity.this, lista);
        lv_contas.setAdapter(bancosEncontradosAdapter);//preenchido dinamicamente
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //atividadeStatica = atividadesEncontradasAdapter.getItem(i);
        Intent novaTela = new Intent(getApplicationContext(),VisualizarAtividades.class);
        startActivity(novaTela);
    }









}
