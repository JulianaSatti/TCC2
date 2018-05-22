package com.example.tcc.tcc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class InteresseVoluntarioAtividade extends AppCompatActivity {

    EditText DescricaoAjudaAtividade;
    Button enviarDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interesse_voluntario_atividade);

        DescricaoAjudaAtividade = (EditText) findViewById(R.id.editTextDescricaoAjudaAtividade);
        enviarDados = (Button) findViewById(R.id.buttonEnviar);


        enviarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    String editTextDescricao = DescricaoAjudaAtividade.getText().toString();
                    if(editTextDescricao.isEmpty()) {
                        //irá mostrar um alerta na tela
                        AlertDialog.Builder dlg = new AlertDialog.Builder(InteresseVoluntarioAtividade.this);
                        dlg.setTitle("Atenção");
                        dlg.setMessage("Nenhum campo pode estar vazio.");
                        dlg.setNeutralButton("ok", null);
                        dlg.show();
                    }else{
                        ////Realiza de fato a tentativa de envio do formulario///////////////////////
                        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
                        String id_user = prefs.getString("id",null);
                        String telefone = prefs.getString("telefone", null);

                        String parametros = "user_id=" + id_user + "&descricao=" + editTextDescricao + "&telefone=" + telefone;
                        String retorno = "";
                        try {
                            retorno = new HTTPService("cadastro_notificacao", parametros).execute().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        if(retorno.trim().contains("cadastro_ok")){
                            Toast.makeText(getApplicationContext(), "Seu interesse foi enviado a ONG! Aguarde o contato", Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(), "Seu interesse foi enviado a ONG!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Problemas ao realizar o cadastro!", Toast.LENGTH_LONG).show();
                        }

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                }

                startActivity(new Intent(InteresseVoluntarioAtividade.this, BuscaAtividadesActivity.class));
            }
        });

    }
}