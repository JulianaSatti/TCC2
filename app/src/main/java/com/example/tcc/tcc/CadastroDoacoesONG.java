package com.example.tcc.tcc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewId;// aqui
import static com.example.tcc.tcc.NecessidadesONGActivity.necessidades;


public class CadastroDoacoesONG extends AppCompatActivity {
    EditText descricao;
    TextView objeto;
    RadioGroup radioGroup;
    Button enviar_doacao;
    Button add_foto;
    ImageView foto;
    RadioButton radioEntrega;
    String url = "";
    String parametros = "";
    String urlUpload = "http://35.199.87.88/api/upload.php";
    Bitmap imagemDoacao;
    public static final int IMAGEM_INTERNA = 12;
    private final int PERMISSAO_REQUEST = 2;
    private AsyncHttpClient ongNecessidade;//aqui

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_doacoes_ong);

        //Tranferir dados do layout para variaveis locais

        objeto = (TextView) findViewById (R.id.objeto);
        descricao = (EditText) findViewById(R.id.desc_obj);
        add_foto = (Button) findViewById(R.id.alterar_foto);
        radioGroup = (RadioGroup) findViewById(R.id.radioOP);
        enviar_doacao = (Button) findViewById(R.id.enviar_doacao);
        foto = (ImageView) findViewById(R.id.foto);

        objeto.setText(necessidades.getNecessidade());

        this.ongNecessidade = new AsyncHttpClient();

        //mostra os itens do spinner que estao setados no string.xml
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.doacoes, android.R.layout.simple_spinner_dropdown_item);
        //spinner_necessidade.setAdapter(adapter);



        enviar_doacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {


                    String text_objeto = necessidades.getNecessidade().toString();
                    String text_descricao = descricao.getText().toString();
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    // encontra o radiobutton pelo ID retornado
                    radioEntrega = (RadioButton) findViewById(selectedId);
                    String rd_retirada = radioEntrega.getText().toString();


                    if (text_descricao.isEmpty()) {

                        //irá mostrar um alerta na tela
                        AlertDialog.Builder dlg = new AlertDialog.Builder(CadastroDoacoesONG.this);
                        dlg.setTitle("Atenção");
                        dlg.setMessage("Nenhum campo pode estar vazio.");
                        dlg.setNeutralButton("ok", null);
                        dlg.show();

                    } else {
                        ////Realiza de fato a tentativa de envio do formulario///////////////////////
                        SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
                        String id_user = prefs.getString("id",null);
                        url = "http://35.199.87.88/api/cadastro_doacoes.php";
                        //ong
                        parametros = "ong_id="+lerListViewId+"&user_id=" + id_user + "&nome=" + text_objeto  + "&descricao=" + text_descricao + "&retirada=" +rd_retirada + "&foto";
                        new SolicitaDados().execute(url);
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                }

            }
        });


        ///////////////////////////////////////////////////Pede permissao em tempo real (API 23...)/////////////////////////////////////////////////
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }

        /////////////////////////////////seleciona a foto/////////////////////////
        add_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });

    }

    /////////////////////////////////////Exibe a foto na tela//////////////////////////////////
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            imagemDoacao = (BitmapFactory.decodeFile(picturePath));
            foto.setImageBitmap(imagemDoacao);
        }
    }

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

    //ira verificar os dados no bd.
    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return Conexao.postDados(urls[0], parametros);
        }

        @Override
        protected void onPostExecute(String resultado) {

            //aqui ira verificar se os dados já existem.
            if (resultado.contains("cadastro_ok")) {

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
                        String imageData = imageToString(imagemDoacao);
                        params.put("image", imageData);
                        String nomeFoto = "doacao";
                        params.put("tipo", nomeFoto);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(CadastroDoacoesONG.this);
                requestQueue.add(stringRequest);

                //termina aqui o envio de imagem para o servidor//
                Toast.makeText(getApplicationContext(),"Doação cadastrada com sucesso!", Toast.LENGTH_LONG).show();
                Intent voltadoacoes = new Intent(CadastroDoacoesONG.this, PesquisarOngActivity.class);
                startActivity(voltadoacoes);
            }else {
                Toast.makeText(getApplicationContext(),resultado, Toast.LENGTH_LONG).show();
            }

        }
    }
    private String imageToString (Bitmap imagemDoacao){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imagemDoacao.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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
}

