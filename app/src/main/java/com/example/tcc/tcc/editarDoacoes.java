package com.example.tcc.tcc;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.tcc.tcc.DoacoesRealizadasActivity.doacoesStatica;

public class editarDoacoes extends AppCompatActivity {

    Spinner alterarCategoria;
    EditText alterarNome;
    EditText alterarDescricao;
    RadioGroup opcaoRetirada;
    CheckBox itemDoado;
    Button alterarDoacao;
    Button excluirDoacao;
    Button envFoto;
    ArrayList<String> categorias;
    private final int PERMISSAO_REQUEST = 2;
    public static final int IMAGEM_INTERNA = 12;
    String categoriaSelecionada;
    ImageView fotodoacao;
    Bitmap imagemDoacao;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_doacoes);

        alterarCategoria = (Spinner) findViewById(R.id.spinnerAlterarCategoria);
        alterarNome = (EditText) findViewById(R.id.editTextAlterarNomeDoacao);
        alterarDescricao = (EditText) findViewById(R.id.editTextAlterarDescricaoDoacao);
        opcaoRetirada = (RadioGroup) findViewById(R.id.radioOP);
        itemDoado = (CheckBox) findViewById(R.id.checkBox_item_doado);
        alterarDoacao = (Button) findViewById(R.id.buttonAlterarDoacao);
        excluirDoacao = (Button) findViewById(R.id.buttonExcluirDoacao);
        envFoto = (Button) findViewById(R.id.alterar_foto);

        categorias = new ArrayList<String>();
        categorias.add("Acessórios");
        categorias.add("Alimentos");
        categorias.add("Brinquedos");
        categorias.add("Calçados");
        categorias.add("Eletrodomésticos");
        categorias.add("Eletroeletrônicos");
        categorias.add("Livros");
        categorias.add("Moveis");
        categorias.add("Objetos de Decoração");
        categorias.add("Roupas");
        categorias.add("Utilidades domésticas");

        new Thread() {
            public void run() {

                try {

                    URL url = new URL("http://35.199.87.88/images/doacao/" + doacoesStatica.getId() + ".jpeg");
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream input = conexao.getInputStream();
                    imagemDoacao = BitmapFactory.decodeStream(input);

                } catch (IOException e) {

                }

                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        fotodoacao = findViewById(R.id.foto);
                        fotodoacao.setImageBitmap(imagemDoacao);

                    }
                });

            }
        }.start();

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


        //mostra os itens do spinner que estao setados no string.xml
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.doacoes, android.R.layout.simple_spinner_dropdown_item);
        alterarCategoria.setAdapter(adapter);

        // radioGroup
        if (doacoesStatica.getLevar_local().equals("Sim")) {
            opcaoRetirada.check(R.id.radio_AlterarSim);
        } else {
            opcaoRetirada.check(R.id.radio_AlterarNao);
        }

        //CheckBox
        if (doacoesStatica.getItemDoado().toUpperCase().trim().equals("SIM")) {
            itemDoado.setChecked(true);
        }

        alterarNome.setText(doacoesStatica.getNome());
        alterarDescricao.setText(doacoesStatica.getDescricao());
        alterarCategoria.setSelection(categorias.indexOf(doacoesStatica.getCategoria())); // devolve a posicao
        categoriaSelecionada = doacoesStatica.getCategoria();

        alterarCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaSelecionada = categorias.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alterarDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean alteracao = false;


                if (alterarNome.getText().toString().trim() != doacoesStatica.getNome().trim()) {
                    alteracao = true;
                }

                if (alterarDescricao.getText().toString().trim() != doacoesStatica.getDescricao().trim()) {
                    alteracao = true;
                }
                String checkDoado = "Não";
                if (itemDoado.isChecked()) {
                    checkDoado = "Sim";
                }
                if (!(checkDoado.toUpperCase().equals(doacoesStatica.getItemDoado().toUpperCase().trim()))) {
                    alteracao = true;
                }
                if (doacoesStatica.getCategoria() != categoriaSelecionada) {
                    alteracao = true;
                }
                int idRadioSelecionado = opcaoRetirada.getCheckedRadioButtonId();
                RadioButton radioSelecionado = (RadioButton) findViewById(idRadioSelecionado);
                if (radioSelecionado.getText().toString() != doacoesStatica.getLevar_local()) {
                    alteracao = true;
                }

                String parametros = "id=" + doacoesStatica.getId() + "&objeto=" + alterarNome.getText().toString().trim() +
                        "&descricao=" + alterarDescricao.getText().toString().trim() +
                        "&categoria=" + categoriaSelecionada +
                        "&levar_local=" + radioSelecionado.getText().toString() +
                        "&doado=" + checkDoado;

                if (alteracao) {
                    try {
                        String retorno = new HTTPService("EditarDoacao", parametros).execute().get();

                        if (retorno.contains("atualizado com sucesso")) {


                            Toast.makeText(getApplicationContext(), "Doação atualizada com sucesso!", Toast.LENGTH_LONG).show();
                            Intent abreInicio = new Intent(editarDoacoes.this, TelaInicialActivity.class);
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
        });

        excluirDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(editarDoacoes.this);
                builder.setMessage("Deseja excluir esta doação?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    //escolheu opcao sim
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String parametros = "id=" + doacoesStatica.getId();
                        try {
                            String retorno = new HTTPService("ExcluirDoacao", parametros).execute().get();

                            if (retorno.contains("excluido com sucesso")) {

                                Toast.makeText(getApplicationContext(), "Doação excluida com sucesso!", Toast.LENGTH_LONG).show();
                                Intent abreInicio = new Intent(editarDoacoes.this, DoacoesRealizadasActivity.class);
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
                }).setNegativeButton("Não", new DialogInterface.OnClickListener(){
                    //selecionou a opcao não
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Intent intent = new Intent(editarDoacoes.this,editarDoacoes.class);
                        //startActivity(intent);
                    }
                });
                AlertDialog d = builder.create();
                d.setTitle("Excluir Doacao");
                d.show();
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
            imagemDoacao = (BitmapFactory.decodeFile(picturePath));
            fotodoacao.setImageBitmap(imagemDoacao);
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
        if(id==R.id.action_atividades_interessadas){
            startActivity (new Intent(this,AtividadesInteresseActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
