package com.example.tcc.tcc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.tcc.tcc.LoginActivity.enderFoto;

public class TelaInicialActivity extends AppCompatActivity {

    private Button ong;
    private Button doacoes;
    private Button atividade;
    ImageView foto2;
    Bitmap imgInicial;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
        String id_user = prefs.getString("id","id");

        //Toast.makeText(getApplicationContext(),id_user, Toast.LENGTH_LONG).show();


        ////////////////////////////imagem inicial//////////////////////////////////
        new Thread() {
            public void run() {

                try {

                    URL url = new URL("http://35.199.87.88/api/imagens/" + enderFoto + ".jpg");
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream input = conexao.getInputStream();
                    imgInicial = BitmapFactory.decodeStream(input);

                } catch (IOException e) {

                }

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        foto2 = findViewById(R.id.inicio_foto);
                        foto2.setImageBitmap(imgInicial);

                    }
                });

            }
        }.start();

        doacoes = findViewById(R.id.btn_doacoes);//aqui que faz transicao de tela ao clique no btn Doações
        doacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialActivity.this, DoacoesActivity.class));
            }
        });

        //ira acessar a tela pesquisar ONG
        ong = findViewById(R.id.btn_ongs);
        ong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialActivity.this, PesquisarOngActivity.class));
            }
        });

        atividade = findViewById(R.id.btn_atividades);
        atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialActivity.this, BuscaAtividadesActivity.class));
            }
        });
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
            startActivity(new Intent(TelaInicialActivity.this,MainActivity.class));
           // finish();

            return false;
        }if (id ==R.id.action_perfil){
            startActivity(new Intent(TelaInicialActivity.this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
            startActivity(new Intent(TelaInicialActivity.this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes){
            startActivity (new Intent(TelaInicialActivity.this,Notificacao.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


        // não chame o super desse método, assim a tecla voltar fica inutil nesta tela
    }

}

