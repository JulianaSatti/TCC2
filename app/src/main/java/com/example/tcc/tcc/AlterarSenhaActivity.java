package com.example.tcc.tcc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;


public class AlterarSenhaActivity extends AppCompatActivity implements View.OnClickListener {

    EditText senhaAtual;
    EditText novaSenha;
    EditText confirmarNovaSenha;
    Button salvarNovaSenha;
    login login = new login ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        senhaAtual = (EditText) findViewById(R.id.editTextSenhaAtual);
        novaSenha = (EditText) findViewById(R.id.editTextNovaSenha);
        confirmarNovaSenha = (EditText) findViewById(R.id.editTextConfirmarNovaSenha);
        salvarNovaSenha = (Button) findViewById(R.id.buttonEditarSenha);

        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
        String id_user = prefs.getString("id", "não encontrou id");
        String senha = prefs.getString("senha", "senha");

        login.setId(id_user);
        login.setSenha(senha);

        if(novaSenha.getText().toString().trim().equals(confirmarNovaSenha.getText().toString().trim())){
            salvarNovaSenha.setOnClickListener(this);
        }
        else {
            Toast.makeText(getApplicationContext(), "senhas não conferem", Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onClick(View v) {
        boolean alteracao = true;
        String parametros = "id=" + login.getId() + "&senha=" + novaSenha.getText().toString().trim();

        if (senhaAtual.getText().toString().trim().isEmpty() || novaSenha.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Campo de senha não pode ficar em branco ", Toast.LENGTH_LONG).show();

        }
        else if(md5(senhaAtual.getText().toString().trim()) != login.getSenha().toString().trim()){
            Toast.makeText(getApplicationContext(), "Senha atual incorreta ", Toast.LENGTH_LONG).show();
        }
        else {
                try {
                    String retorno = new HTTPService("EditarSenha", parametros).execute().get();

                    if (retorno.contains("atualizado com sucesso")) {
                        Toast.makeText(getApplicationContext(), "Senha alterada com sucesso!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, TelaInicialActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "ERRO: " + retorno, Toast.LENGTH_LONG).show();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

        }


    }

    //Função para criar hash da senha informada
    public static String md5(String senha){
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        sen = hash.toString(16);
        return sen;
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
