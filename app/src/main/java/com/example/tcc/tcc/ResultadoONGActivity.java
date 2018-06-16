package com.example.tcc.tcc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.AbstractList;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.tcc.tcc.PesquisarOngActivity.categAtual;

public class ResultadoONGActivity extends AppCompatActivity {

    private ListView listView;

    ArrayList id_ong = new ArrayList();
    ArrayList razao = new ArrayList();
    ArrayList categoria = new ArrayList();
    ArrayList foto = new ArrayList();
    ArrayList endereco = new ArrayList();
    ArrayList email = new ArrayList();
    ArrayList telefone = new ArrayList();
    ArrayList num = new ArrayList();
    ArrayList cidade = new ArrayList();
    ArrayList horario = new ArrayList();
    ArrayList site = new ArrayList();
    ArrayList UF = new ArrayList();
    ArrayList conta = new ArrayList();
    ArrayList agencia = new ArrayList();
    ArrayList banco = new ArrayList();
    ArrayList cnpj_conta = new ArrayList();

    ///Variaveis globais que podem ser lidas em qualquer activity do projeto///
    public static String lerListViewId;
    public static String lerListViewCat;
    public static String lerListViewRazao;
    public static String lerListViewEnd;
    public static String lerListViewEmail;
    public static String lerListViewTel;
    public static String lerListViewFoto;
    public static String lerListViewNum;
    public static String lerListViewCidade;
    public static String lerListViewHorario;
    public static String lerListViewSite;
    public static String lerListViewUF;
    public static String lerListViewConta;
    public static String lerListViewBanco;
    public static String lerListViewAgencia;
    public static String lerListViewCnpj_Conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_ong);
        listView=(ListView)findViewById(R.id.listview);
        descarregarImagem();



////////quando clicar no item irá direcionar para a activity detalhes//////
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ///de acordo com o "position" irá ler as variaveis atuais abaixo: ///
                lerListViewId =id_ong.get(position).toString();
                lerListViewCat = categoria.get(position).toString();
                lerListViewRazao = razao.get(position).toString();
                lerListViewEnd = endereco.get(position).toString();
                lerListViewNum = num.get(position).toString();
                lerListViewEmail = email.get(position).toString();
                lerListViewTel = telefone.get(position).toString();
                lerListViewFoto = foto.get(position).toString();
                lerListViewCidade = cidade.get(position).toString();
                lerListViewSite = site.get(position).toString();
                lerListViewHorario = horario.get(position).toString();
                lerListViewUF = UF.get(position).toString();
                lerListViewCnpj_Conta = cnpj_conta.get(position).toString();
                /*
                lerListViewConta = conta.get(position).toString();
                lerListViewBanco = banco.get(position).toString();

                lerListViewAgencia = agencia.get(position).toString();*/

                // depois ira abrir a tela Detalhes com as informaçoes a cima solicitadas///////
                Intent intent = new Intent(ResultadoONGActivity.this,
                        /* activity a ser chamada */ detalhesOngActivity.class);
                startActivity(intent);


            }
        });
    }

    private void descarregarImagem() {
        id_ong.clear();
        razao.clear();
        categoria.clear();
        foto.clear();
        endereco.clear();
        email.clear();
        telefone.clear();
        num.clear();
        cidade.clear();
        horario.clear();
        site.clear();
        UF.clear();
        cnpj_conta.clear();
        /*
        conta.clear();
        banco.clear();
        agencia.clear();
        */

        /*final ProgressDialog progressDialog = new ProgressDialog(ResultadoONGActivity.this);
        progressDialog.setMessage("Carregando dados...");
        progressDialog.show();*/
        AsyncHttpClient client = new AsyncHttpClient();
        /////endereço onde o php irá informar os dados para o app//
        client.get("http://35.199.87.88/api/resultado_ong.php?categ="+categAtual, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode==200){
                   // progressDialog.dismiss();
                    try {
                        ////////onde carrega dado por dado do servidor/////
                        JSONArray jArray =new JSONArray(new String(responseBody));
                        for(int i=0;i<jArray.length();i++) {

                            id_ong.add(jArray.getJSONObject(i).getString("ong_id"));
                            razao.add( jArray.getJSONObject(i).getString("razao_social") );
                            categoria.add( jArray.getJSONObject(i).getString("categoria") );
                            foto.add( jArray.getJSONObject(i).getString("ong_id") );
                            endereco.add( jArray.getJSONObject(i).getString("rua") );
                            num.add( jArray.getJSONObject(i).getString("numero") );
                            email.add( jArray.getJSONObject(i).getString("email") );
                            telefone.add( jArray.getJSONObject(i).getString("telefone") );
                            cidade.add( jArray.getJSONObject(i).getString("cidade") );
                            site.add( jArray.getJSONObject(i).getString("site") );
                            horario.add( jArray.getJSONObject(i).getString("horario") );
                            UF.add( jArray.getJSONObject(i).getString("uf") );
                            cnpj_conta.add( jArray.getJSONObject(i).getString("cnpj") );
                            /*
                            conta.add( jArray.getJSONObject(i).getString("conta") );
                            banco.add( jArray.getJSONObject(i).getString("banco") );
                            agencia.add( jArray.getJSONObject(i).getString("agencia") );

*/
                        }
                        listView.setAdapter(new ImagemAdapter(getApplicationContext()));


                    }catch (Exception e){
                        //caso não existir nenhuma ONG com o nome pesquisado.//
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ResultadoONGActivity.this);;
                        alerta.setTitle("Aviso");
                        alerta
                                .setIcon(R.mipmap.ic_aviso)
                                .setMessage("Nenhuma ONG encontrada...")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ResultadoONGActivity.this, PesquisarOngActivity.class));

                                    }
                                });
                        AlertDialog alertDialog = alerta.create();
                        alertDialog.show();

                        e.printStackTrace();

                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private class ImagemAdapter extends BaseAdapter{
        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView nome, cat;

        public ImagemAdapter(Context applicationContext) {
            this.ctx=applicationContext;
            layoutInflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return foto.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /////Insere em cada linha do listview o layout desta activity////
            ViewGroup viewGroup=(ViewGroup)layoutInflater.inflate(R.layout.menu_resultados, null);
            smartImageView=(SmartImageView)viewGroup.findViewById(R.id.foto1);
            nome=(TextView) viewGroup.findViewById(R.id.nome);
            cat=(TextView) viewGroup.findViewById(R.id.cat);

            String urlFinal="http://35.199.87.88/images/ong/"+foto.get(position).toString()+".png";

            Rect rect=new Rect(smartImageView.getLeft(), smartImageView.getTop(), smartImageView.getRight(), smartImageView.getBottom());

            smartImageView.setImageUrl(urlFinal, rect); // faz o retangulo de margem

            nome.setText(razao.get(position).toString()); //Aqui que exibe os dados no activity
            cat.setText("Categoria: "+categoria.get(position).toString()); // aqui fixei a palavra categoria:

            return viewGroup; // retorna os dados para viewGroup
        }
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
            // finish();

            //return false;
        }if (id ==R.id.action_perfil){
            startActivity(new Intent(this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
            startActivity(new Intent(this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes){
            startActivity (new Intent(this,Notificacao.class));
        }if(id==R.id.action_atividades_interessadas){
            startActivity (new Intent(this,AtividadesInteresseActivity.class));
        }if(id==R.id.logo_maos){
            startActivity(new Intent(this, TelaInicialActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
