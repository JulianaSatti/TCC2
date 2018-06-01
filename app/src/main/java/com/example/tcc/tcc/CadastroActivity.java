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
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroActivity extends AppCompatActivity  {
    EditText nome, email, senha, outra_sen, telefone;
    Button envFoto;
    ImageView foto;
    String url = "";
    String urlUpload = "http://35.199.87.88/api/upload.php";
    String parametros = "";
    Bitmap imagemInicial;
    public static final int IMAGEM_INTERNA = 12;
    private final int PERMISSAO_REQUEST = 2;
    public String emailValido;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //sobreescrevendo - cria a tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Tranferir dados do layout para variaveis locais
        nome = (EditText) findViewById(R.id.cadastro_nome);
        email = (EditText) findViewById(R.id.cadastro_email);
        telefone = (EditText) findViewById(R.id.alterar_telefone);
        senha = (EditText) findViewById(R.id.cadastro_senha);
        outra_sen = (EditText) findViewById(R.id.cadastro_repetirsenha);
        envFoto = (Button) findViewById(R.id.Cadastro_btn_foto);
        foto = (ImageView) findViewById(R.id.cadastro_foto);

///////////////////////////////////////////////////Pede permissao em tempo real (API 23...)/////////////////////////////////////////////////

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSAO_REQUEST);
            }
        }

/////////////////////////////////seleciona a foto/////////////////////////
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

                if (networkInfo != null && networkInfo.isConnected()) {

                    String tex_nome = nome.getText().toString();
                    String tex_email = email.getText().toString();
                    String tex_telefone = telefone.getText().toString();
                    String tex_senha = senha.getText().toString();
                    String outra_senha = outra_sen.getText().toString();

                    if (tex_nome.isEmpty() || tex_email.isEmpty() || tex_senha.isEmpty() || tex_telefone.isEmpty()) {

                        //irá mostrar um alerta na tela
                        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                        dlg.setTitle("Aviso");
                        dlg.setIcon(R.mipmap.ic_aviso);
                        dlg.setMessage("Nenhum campo pode estar vazio.");
                        dlg.setNeutralButton("ok",null);
                        dlg.show();

                    } else {
                        validarEmail();

                        if (emailValido.contains("true")) {

                            //ira verificar o tamanho da senha, caso for menor de 6 ele mostrarar de mensagem
                            if (tex_senha.length()>=6) {

                                //ira comparar as senhas, se tiver iguais salva se não mostra erro de senhas não conferem
                                if (tex_senha.equals(outra_senha))
                                {
                                    ////Realiza de fato a tentativa de envio do formulario///////////////////////
                                    url =  "http://35.199.87.88/api/registrar.php";
                                    parametros = "nome=" + tex_nome + "&email=" + tex_email + "&senha=" + tex_senha + "&telefone=" + tex_telefone;
                                    new SolicitaDados().execute(url);
                                    /////////////////////////////////////////////////////////////////////////////
                                } else {
                                    Toast.makeText(CadastroActivity.this, "Senhas não Conferem", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(CadastroActivity.this, "Senha deve ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //ira verificar os dados no bd.
    private class SolicitaDados extends AsyncTask<String, Void, String> {
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
                    public void onResponse(String response) {}

                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        String imageData = imageToString(imagemInicial);
                        params.put("image", imageData);
                        String nomeFoto = "perfil";
                        params.put("tipo", nomeFoto);

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


    //validação de email, irá veerificar se tem @  no email
    private void validarEmail () {
        String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                "\\@" +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                "(" +

                "\\." +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                ")+";

        String tex_email = email.getText().toString();
//matcher corresponde uma sequencia de caracteres interpretando pelo o Pattern que é uma expressão regular
        Matcher matcher = Pattern.compile(validemail).matcher(tex_email);

        if (matcher.matches()) {
            emailValido = "true";
        } else {
            emailValido = "false";
            Toast.makeText(getApplicationContext(), "Email Inválido", Toast.LENGTH_LONG).show();
        }

    }
}

