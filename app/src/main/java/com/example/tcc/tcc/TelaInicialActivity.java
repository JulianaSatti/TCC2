package com.example.tcc.tcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.tcc.tcc.LoginActivity.enderFoto;

public class TelaInicialActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ////////////////////////////imagem inicial//////////////////////////////////
        new Thread(){
            public void run(){

                try {

                    URL url = new URL("http://35.199.87.88/api/imagens/" + enderFoto + ".jpg");
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

        atividade = (Button) findViewById(R.id.btn_atividades);
        atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialActivity.this, BuscaAtividadesActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) { //seta o que quer fazer em cada um dos elementos
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.perfil_btn) {
            // Handle the camera action
            Intent intent = new Intent(this, VisualizarPerfil.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
