package com.example.tcc.tcc;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import com.github.snowdream.android.widget.SmartImageView;

import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewCat;
//import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewCidade;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewCidade;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewEmail;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewEnd;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewFoto;
//import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewNum;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewRazao;
import static com.example.tcc.tcc.ResultadoONGActivity.lerListViewTel;

public class detalhesOngActivity extends AppCompatActivity {

    private TextView detalhesCategoria;
    private TextView detalhesRazao;
    private TextView detalhesEnd;
    private TextView detalhesEmail;
    private TextView detalhesTel, detalhesCidades;
    private SmartImageView smartImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_ong);

///////////Carrega todos os dados das variaveis globais//////////////////////
        detalhesCategoria = (TextView) findViewById(R.id.detCat);
        detalhesCategoria.setText("Categoria: "+lerListViewCat);
        detalhesRazao = (TextView) findViewById(R.id.detRazao);
        detalhesRazao.setText("Razão Social: "+lerListViewRazao);
        detalhesEnd = (TextView) findViewById(R.id.detEnd);
        detalhesEnd.setText("Endereço: "+lerListViewEnd);
        detalhesEmail = (TextView) findViewById(R.id.detEmail);
        detalhesEmail.setText("E-mail: "+lerListViewEmail);
        detalhesTel = (TextView) findViewById(R.id.detTel);
        detalhesTel.setText("Telefone: "+lerListViewTel);
        detalhesCidades = (TextView) findViewById(R.id.detCidade);
        detalhesCidades.setText("Cidade: "+lerListViewCidade);
        /////Seleciona a foto e exibe////////////////////
        smartImageView=(SmartImageView)findViewById(R.id.foto2);

        String urlFinal="http://35.199.87.88/api/imagens/"+"ong.jpg";
       // String urlFinal=""http://35.199.87.88/api/imagens/"+lerListViewFoto;
        Rect rect=new Rect(smartImageView.getLeft(),// faz o retangulo de margem lado esq
                smartImageView.getTop(),// faz o retangulo de margem lado dir
                smartImageView.getRight(),// faz o retangulo de margem lado sup
                smartImageView.getBottom());// faz o retangulo de margem lado inf
        smartImageView.setImageUrl(urlFinal, rect);// faz o retangulo de margem

    }
}
