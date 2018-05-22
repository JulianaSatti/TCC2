package com.example.tcc.tcc;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EditarPerfilActivity extends AppCompatActivity {

    EditText alterarNome;
    EditText alterarEmail;
    EditText alterarTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        EditText alterarNome = (EditText) findViewById(R.id.alterar_nome);
        EditText alterarEmail = (EditText) findViewById(R.id.alterar_email);
        EditText alterarTelefone = (EditText) findViewById(R.id.alterar_telefone);
        Button salvarAlteracao = (Button) findViewById(R.id.btn_salvarEdicaoPerfil);

        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
        final String id_user = prefs.getString("id",null);
        String nome = prefs.getString("nome",null);
        String email = prefs.getString("email",null);
        String telefone = prefs.getString("telefone",null);

        alterarNome.setText(nome);
        alterarEmail.setText(email);
        alterarTelefone.setText(telefone);


    }

}
