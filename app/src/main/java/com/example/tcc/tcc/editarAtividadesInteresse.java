package com.example.tcc.tcc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import static com.example.tcc.tcc.AtividadesInteresseActivity.interesseAtividadeStatica;
import static com.example.tcc.tcc.DoacoesRealizadasActivity.doacoesStatica;

public class editarAtividadesInteresse extends AppCompatActivity {

    TextView nomeAtividade;
    TextView descricaoAtividade;
    EditText mensagemInteresse;
    Button salvarAlteracaoInteresse;
    Button excluirInteresse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_atividades_interesse);

        nomeAtividade = (TextView)findViewById(R.id.textViewNomeAtividade);
        descricaoAtividade = (TextView)findViewById(R.id.textViewDescricaoAtividade);
        mensagemInteresse = (EditText) findViewById(R.id.editTextMensagem);
        salvarAlteracaoInteresse = (Button) findViewById(R.id.buttonSalvarAlteracaoAtividadeInteresse);
        excluirInteresse = (Button) findViewById(R.id.buttonExcluirAlteracao);


        nomeAtividade.setText(interesseAtividadeStatica.getAtividade());
        descricaoAtividade.setText(interesseAtividadeStatica.getDescricao());
        mensagemInteresse.setText(interesseAtividadeStatica.getMensagem());

        salvarAlteracaoInteresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean alteracao = false;
                String id_notificacao = interesseAtividadeStatica.getId();

                if (mensagemInteresse.getText().toString().trim() != interesseAtividadeStatica.getMensagem().trim()) {
                    alteracao = true;
                }

                String parametros = "id=" + id_notificacao + "&descricao=" + mensagemInteresse.getText().toString().trim();

                if (alteracao) {
                    try {
                        String retorno = new HTTPService("EditarInteresseAtividade", parametros).execute().get();

                        if (retorno.contains("atualizado com sucesso")) {


                            Toast.makeText(getApplicationContext(), "Interesse atualizado com sucesso!", Toast.LENGTH_LONG).show();
                            Intent abreInicio = new Intent(editarAtividadesInteresse.this, TelaInicialActivity.class);
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

        excluirInteresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(editarAtividadesInteresse.this);
                builder.setMessage("Deseja excluir interesse nesta atividade?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    //escolheu opcao sim
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String id_notificacao = interesseAtividadeStatica.getId();
                        String parametros = "id=" + id_notificacao;
                        try {
                            String retorno = new HTTPService("ExcluirInteresseAtividade", parametros).execute().get();

                            if (retorno.contains("excluido com sucesso")) {

                                Toast.makeText(getApplicationContext(), "Doação excluida com sucesso!", Toast.LENGTH_LONG).show();
                                Intent abreInicio = new Intent(editarAtividadesInteresse.this, TelaInicialActivity.class);
                                startActivity(abreInicio);


                            } else {
                                Toast.makeText(getApplicationContext(), "ERRO: " + retorno, Toast.LENGTH_LONG).show();
                            }


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Interesse excluido com sucesso", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(editarAtividadesInteresse.this,TelaInicialActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener(){
                    //selecionou a opcao não
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(editarAtividadesInteresse.this,editarAtividadesInteresse.class);
                        startActivity(intent);
                    }
                });
                AlertDialog d = builder.create();
                d.setTitle("Excluir Interesse da Atividade");
                d.show();

            }
        });

    }
}
