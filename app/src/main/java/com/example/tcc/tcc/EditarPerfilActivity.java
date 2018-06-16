package com.example.tcc.tcc;

import android.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EditarPerfilActivity extends AppCompatActivity implements View.OnClickListener {

    EditText alterarNome;
    EditText alterarEmail;
    EditText alterarTelefone;
    Button salvarAlteracao;
    Button envFoto;
    ImageView fotoperfil;
    String url = "";
    public static final int IMAGEM_INTERNA = 12;
    private final int PERMISSAO_REQUEST = 2;
    login login = new login();
    Bitmap imagemPerfil;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        alterarNome = (EditText) findViewById(R.id.alterar_nome);
        alterarEmail = (EditText) findViewById(R.id.alterar_email);
        alterarTelefone = (EditText) findViewById(R.id.alterar_telefone);
        salvarAlteracao = (Button) findViewById(R.id.btn_salvarEdicaoPerfil);
        envFoto = (Button) findViewById(R.id.btn_alterar_foto);
        fotoperfil = (ImageView) findViewById(R.id.foto);

        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
        final String id_user = prefs.getString("id",null);
        String nome = prefs.getString("nome",null);
        String email = prefs.getString("email",null);
        String telefone = prefs.getString("telefone",null);

        new Thread() {
            public void run() {

                try {

                    URL url = new URL("http://35.199.87.88/images/user/" + id_user + ".jpeg");
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream input = conexao.getInputStream();
                    imagemPerfil = BitmapFactory.decodeStream(input);

                } catch (IOException e) {

                }

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        fotoperfil = findViewById(R.id.foto);
                        fotoperfil.setImageBitmap(imagemPerfil);

                    }
                });

            }
        }.start();




        login.setId(id_user);
        login.setNome(nome);
        login.setEmail(email);
        login.setTelefone(telefone);

        alterarNome.setText(nome);
        alterarEmail.setText(email);
        alterarTelefone.setText(telefone);

        //permissão para abrir galeria
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else{
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSAO_REQUEST);
            }
        }
        ///selecionar foto
        envFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });

        salvarAlteracao.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        boolean alteracao = false;
        String parametros = "nome=" + alterarNome.getText().toString().trim() +
                "&telefone=" + alterarTelefone.getText().toString().trim() +
                "&email=" + alterarEmail.getText().toString().trim() +
                "&id=" + login.getId();

        if (alterarNome.getText().toString().trim() != login.getNome().trim()) {
            alteracao = true;
        }

        if (alterarEmail.getText().toString().trim() != login.getEmail().trim()) {
            alteracao = true;
        }

        if (alterarTelefone.getText().toString().trim() != login.getTelefone().trim()) {
            alteracao = true;
        }

        if (alteracao) {
            try {
                String retorno = new HTTPService("EditarPerfil", parametros).execute().get();

                if (retorno.contains("atualizado com sucesso")) {
                    //grava a a informacao do usuario logado
                    SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    //Toast.makeText(getApplicationContext(),resultado, Toast.LENGTH_LONG).show();


                    editor.remove("nome");
                    editor.remove("telefone");
                    editor.remove("email");

                    editor.putString("nome",alterarNome.getText().toString().trim());
                    editor.putString("telefone",alterarTelefone.getText().toString().trim());
                    editor.putString("email",alterarEmail.getText().toString().trim());

                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Perfil atualizado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent abreInicio = new Intent(EditarPerfilActivity.this, TelaInicialActivity.class);
                    startActivity(abreInicio);


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

            /////////////////////////////////////Exibe a foto na tela//////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK && requestCode== 1) {Uri selectedImage= data.getData();
            String[] filePath= { MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex= c.getColumnIndex(filePath[0]);
            String picturePath= c.getString(columnIndex);
            c.close();
            imagemPerfil = (BitmapFactory.decodeFile(picturePath));
            fotoperfil.setImageBitmap(imagemPerfil);
        }}

    //////////////////////// caso aceite a permissao em tempo real, libera para acessar sua foto API 23...//////////
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSAO_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {// A permissão foi concedida. Pode continuar

            } else {
                // A permissão foi negada. Precisa ver o que deve ser desabilitado
            }
            return;
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
            startActivity(new Intent(this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
            startActivity(new Intent(this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes) {
            startActivity(new Intent(this, Notificacao.class));
        }if(id==R.id.action_atividades_interessadas){
                startActivity (new Intent(this,AtividadesInteresseActivity.class));
        }if(id==R.id.logo_maos){
            startActivity(new Intent(this, TelaInicialActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, TelaInicialActivity.class);
        startActivity(intent);

        // não chame o super desse método, assim a tecla voltar fica inutil nesta tela
    }


}
