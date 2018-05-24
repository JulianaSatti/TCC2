package com.example.tcc.tcc;

import android.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EditarPerfilActivity extends AppCompatActivity {

    EditText alterarNome;
    EditText alterarEmail;
    EditText alterarTelefone;
    Button envFoto;
    ImageView fotoperfil;
    Bitmap imagemPerfil;
    String url = "";
    String parametros = "";
    String urlUpload = "http://35.199.87.88/api/upload.php";
    public static final int IMAGEM_INTERNA = 12;
    private final int PERMISSAO_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        EditText alterarNome = (EditText) findViewById(R.id.alterar_nome);
        EditText alterarEmail = (EditText) findViewById(R.id.alterar_email);
        EditText alterarTelefone = (EditText) findViewById(R.id.alterar_telefone);
        Button salvarAlteracao = (Button) findViewById(R.id.btn_salvarEdicaoPerfil);


        envFoto = (Button) findViewById(R.id.btn_alterar_foto);
        fotoperfil = (ImageView) findViewById(R.id.foto);

        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
        final String id_user = prefs.getString("id",null);
        String nome = prefs.getString("nome",null);
        String email = prefs.getString("email",null);
        String telefone = prefs.getString("telefone",null);

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

            finish();
            return true;

        }if (id ==R.id.action_perfil){
            startActivity(new Intent(this,EditarPerfilActivity.class));
        }if (id==R.id.action_alterar_senha){
            startActivity(new Intent(this,AlterarSenhaActivity.class));
        }if(id==R.id.action_notificacoes){
            startActivity(new Intent(this,Notificacao.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
