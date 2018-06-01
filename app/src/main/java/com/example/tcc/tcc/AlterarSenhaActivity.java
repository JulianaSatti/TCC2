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
        Toast.makeText(getApplicationContext(), senha, Toast.LENGTH_LONG).show();

        login.setId(id_user);
        login.setSenha(senha);

        if(senhaAtual.getText().toString().trim() != login.getSenha().trim()){
            Toast.makeText(getApplicationContext(), "Senha atual incorreta! ", Toast.LENGTH_LONG).show();
        }

        if(novaSenha.getText().toString().trim() != confirmarNovaSenha.getText().toString().trim()){
            Toast.makeText(getApplicationContext(), "Senhas diferentes! ", Toast.LENGTH_LONG).show();
        }

        salvarNovaSenha.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String parametros = "id=" + login.getId() + "&senha=" + novaSenha.getText().toString().trim();

        try{
            String retorno = new HTTPService("EditarSenha", parametros).execute().get();

            if (retorno.contains("atualizado com sucesso")) {
                Toast.makeText(getApplicationContext(), "Senha alterada com sucesso!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "ERRO: " + retorno, Toast.LENGTH_LONG).show();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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

            finish();

            return true;
        }if (id ==R.id.action_perfil){
            startActivity(new Intent(AlterarSenhaActivity.this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
            startActivity(new Intent(AlterarSenhaActivity.this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes){
            startActivity(new Intent(AlterarSenhaActivity.this,Notificacao.class));
        }if(id==R.id.logo_maos){
            startActivity(new Intent(this, TelaInicialActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


}
