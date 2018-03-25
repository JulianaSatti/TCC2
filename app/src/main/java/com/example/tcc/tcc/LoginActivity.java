package com.example.tcc.tcc;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    Button r_senha, btnEntrar;
    EditText loginEmail , loginSenha;

    String url = "";
    String parametros = "";
    public static String enderFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//botão recuperar senha irá a tela para recuperar a senha
        r_senha = (Button) findViewById(R.id.recuperar_senha);
        r_senha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResertActivity.class));
            }
        });

        //login com email
        loginEmail =(EditText) findViewById(R.id.email);
        loginSenha =(EditText) findViewById(R.id.add_senha);
        btnEntrar = (Button) findViewById(R.id.entrar);

        btnEntrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//verifica o status da rede
                if (networkInfo != null && networkInfo.isConnected()){

                    String email = loginEmail.getText().toString();
                    String senha = loginSenha.getText().toString();

                    //irá verificar se os campos estão vazios e se tiver tudo certo loga.
                   if (email.isEmpty() || senha.isEmpty()){
                       Toast.makeText(getApplicationContext(),"Nenhum campo pode estar vazio", Toast.LENGTH_LONG).show();
                   }else{
                       //ira fazer a comunicação com o arquivo logar.php que está dentro do servidor (bd).
                       url = "https://bd-linny.000webhostapp.com/logar.php";

                       parametros = "email=" + email + "&senha=" + senha;
                       enderFoto = loginEmail.getText().toString();


                       new SolicitaDados().execute(url);
                   }
                }
                    else {
                    Toast.makeText(getApplicationContext(),"Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                     }
            }
        });



    }

//ira verificar os dados informadors
    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){

            return Conexao.postDados(urls[0], parametros);

        }
//se o usuário exister irá entrar, caso contrario ira mostrar uma mensagem de erro.
        @Override
        protected void onPostExecute(String resultado) {
            //loginEmail.setText(resultado);
            if (resultado.contains("login_ok")){

                Intent abreInicio = new Intent(LoginActivity.this, TelaInicialActivity.class);
                startActivity(abreInicio);
            }else {
                Toast.makeText(getApplicationContext(),"Usuario ou senha estão incorretos!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
