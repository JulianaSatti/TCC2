package com.example.tcc.tcc;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class CadastroDoacoesONG extends AppCompatActivity {
    EditText descricao;
    Spinner spinner_necessidades;
    Button enviar_doacao;
    RadioGroup radioGroup;
    Button add_foto;
    ImageView foto;
    RadioButton radioEntrega;
    String url = "";
    String parametros = "";
    String urlUpload = "http://35.199.87.88/api/upload.php";
    Bitmap imagemDoacao;
    public static final int IMAGEM_INTERNA = 12;
    private final int PERMISSAO_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_doacoes_ong);

        spinner_necessidades = (Spinner) findViewById(R.id.spinner_necessidades);
        descricao = (EditText) findViewById(R.id.desc_obj);
        add_foto = (Button) findViewById(R.id.adicionar_foto);
        radioGroup = (RadioGroup) findViewById(R.id.radioOP);
        enviar_doacao = (Button) findViewById(R.id.enviar_doacao);
        foto = (ImageView) findViewById(R.id.foto);
    }
}
