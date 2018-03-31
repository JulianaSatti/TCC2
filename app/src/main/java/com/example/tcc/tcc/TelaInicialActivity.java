package com.example.tcc.tcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.tcc.tcc.LoginActivity.enderFoto;

public class TelaInicialActivity extends AppCompatActivity{
    private Button ong;
   private Button doacoes;
   ImageView foto2;
    Bitmap imgInicial;
    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) { //sobreescrevendo - cria a tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);//acesso a pasta
////////////////////////////imagem inicial//////////////////////////////////

        new Thread(){
            public void run(){

                try {

                    URL url = new URL("https://bd-linny.000webhostapp.com/imagens/" + enderFoto + ".jpg");
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream input = conexao.getInputStream();
                    imgInicial = BitmapFactory.decodeStream(input);

                }catch (IOException e){

                }

                handler.post(new Runnable(){

                    @Override
                    public void run() {

                        foto2 = (ImageView) findViewById(R.id.inicio_foto);
                        foto2.setImageBitmap(imgInicial);

                    }
                });

            }
        }.start();
/////////////////////////////////////////////////////////////////////////////


        doacoes = (Button) findViewById(R.id.btn_doacoes);//aqui que faz transicao de tela ao clique no btn Doações
        doacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialActivity.this, DoacoesActivity.class));
            }
        });

        //ira acessar a tela pesquisar ONG
        ong = (Button) findViewById(R.id.btn_ongs);
        ong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialActivity.this, PesquisarOngActivity.class));
            }
        });

    }
    //irá permanecer logado em segundo plano.
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        // não chame o super desse método, assim a tecla voltar fica inutil nesta tela
    }
}
