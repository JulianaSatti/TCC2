package com.example.tcc.tcc;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity  {
    EditText nome;
    EditText email;
    EditText senha;
    EditText outra_sen;
    Button envFoto;
    ImageView foto;
    String url = "";
    String urlUpload = "http://35.199.87.88/api/upload.php";
    String parametros = "";
    Bitmap imagemInicial;
    public static final int IMAGEM_INTERNA = 12;
    private final int PERMISSAO_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //sobreescrevendo - cria a tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Tranferir dados do layout para variaveis locais
        nome = (EditText) findViewById(R.id.cadastro_nome);
        email = (EditText) findViewById(R.id.cadastro_email);
        senha = (EditText) findViewById(R.id.cadastro_senha);
        outra_sen = (EditText) findViewById(R.id.cadastro_repetirsenha);
        envFoto = (Button) findViewById(R.id.Cadastro_btn_foto);
        foto = (ImageView) findViewById(R.id.cadastro_foto);

        //Pede permissao em tempo real (API 23...)//
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSAO_REQUEST);
            }
        }

         //seleciona a foto//
        envFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });
    }

    private boolean validaCampos(){

        boolean res = true;

        String val_nome = nome.getText().toString();
        String val_email = email.getText().toString();
        String val_senha = senha.getText().toString();
        String val_outra_senha = outra_sen.getText().toString();


        if (isCampoVazio(val_nome)){
            nome.requestFocus();
            res = false;
        }
        else
            if( !isEmailValido(val_email)) {
                email.requestFocus();
                res = false;
            }
            else
                if(isCampoVazio(val_senha)) {
                    senha.requestFocus();
                    res = false;
                }
                else
                    if (isCampoVazio(val_outra_senha)) {
                        outra_sen.requestFocus();
                        res = false;
                    }

        if(!res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Atenção");
            dlg.setMessage("Há campos vazios ou dados incorretos");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        return res;
    }

    private boolean isCampoVazio(String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;

    }

    private boolean isEmailValido (String val_email){
        boolean resultado = (!isCampoVazio(val_email) && Patterns.EMAIL_ADDRESS.matcher(val_email).matches());
        return resultado;
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
            imagemInicial = (BitmapFactory.decodeFile(picturePath));
            foto.setImageBitmap(imagemInicial);
        }}

         //////////////////////// caso aceite a permissao em tempo real, libera para acessar sua foto API 23...//////////
        @Override
        public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
            if(requestCode== PERMISSAO_REQUEST) {
                if(grantResults.length> 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED){// A permissão foi concedida. Pode continuar

                } else{
                    // A permissão foi negada. Precisa ver o que deve ser desabilitado
                }return;
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {// esse metodo que manda na action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cadastro_ok:

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


                 String tex_nome = nome.getText().toString();
                 String tex_email = email.getText().toString();
                String tex_senha = senha.getText().toString();
                String outra_senha = outra_sen.getText().toString();

                boolean result = validaCampos();

               boolean passwordIsOk = tex_senha.equals(outra_senha);

               if (!passwordIsOk) {
                   Toast.makeText(CadastroActivity.this, "Senhas não Conferem", Toast.LENGTH_SHORT).show();
               }

                        if(result && passwordIsOk){

                            ////Realiza de fato a tentativa de envio do formulario///////////////////////
                            url = "http://35.199.87.88/api/registrar.php";
                            parametros = "nome=" + tex_nome + "&email=" + tex_email + "&senha=" + tex_senha;
                            new SolicitaDados().execute(url);
                            /////////////////////////////////////////////////////////////////////////////
                        }
        }

        return super.onOptionsItemSelected(item);
    }

    //ira verificar os dados no bd.
    class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){

            return Conexao.postDados(urls[0], parametros);
        }

        @Override
        protected void onPostExecute(String resultado){

            //aqui ira verificar se os dados já existem.
            if (resultado.contains("registro_ok")){

///////////////////////Envia a imagem para a pasta no servidor///////////////////////////////////////////
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpload, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }

                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "error: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        String imageData = imageToString(imagemInicial);
                        params.put("image", imageData);
                        String nomeFoto = email.getText().toString();
                        params.put("nome", nomeFoto);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(CadastroActivity.this);
                requestQueue.add(stringRequest);

                        //////////////////////////termina aqui o envio de imagem para o servidor///////////////////////////////////////////
                Toast.makeText(getApplicationContext(),"Registro efetuado com sucesso!", Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(abreInicio);
            }else {
                Toast.makeText(getApplicationContext(),"Ocorreu um erro! Usuário existente!", Toast.LENGTH_LONG).show();
            }

        }
    }

    private String imageToString (Bitmap imagemInicial){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imagemInicial.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}

