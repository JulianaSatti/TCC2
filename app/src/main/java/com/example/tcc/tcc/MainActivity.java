package com.example.tcc.tcc;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import static android.app.PendingIntent.getActivity;

public class MainActivity extends Activity {
    private Button c_email;
    private Button dados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**intent - enviar uma ação, new é para instanciar uma classe e o this
         * se referente que estamos na classe atual */
        //c_email irá chamar a tela para entrar com email e senha
        c_email = (Button) findViewById(R.id.conta_email);
        c_email.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        //------------Cadastro-----------

        dados = (Button) findViewById(R.id.cadastro);
        dados.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });

        //Manter logado
        /*SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
        boolean jaLogou = prefs.getBoolean("estaLogado", false);

        if(jaLogou) {
            // chama a tela inicial
            startActivity(new Intent(MainActivity.this, TelaInicialActivity.class));
        }
        //else {
            // chama a tela de login
            //startActivity(new Intent(MainActivity.this,LoginActivity.class));
        //}*/


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
