package com.example.tcc.tcc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

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
    ArrayList<String> categorias;
    String categoriaSelecionada;

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {
            //ao clicar em sair irá sair do aplicativo, apenas o finish irá sair apenas da tela atual e não do aplicativo.
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;

        }
        if (id == R.id.action_perfil) {
            startActivity(new Intent(this, EditarPerfilActivity.class));
        }
        if (id == R.id.action_alterar_senha) {
            startActivity(new Intent(this, AlterarSenhaActivity.class));
        }
        if (id == R.id.action_notificacoes) {
            startActivity(new Intent(this, Notificacao.class));
        }
        if(id==R.id.action_atividades_interessadas) {
            startActivity(new Intent(this, AtividadesInteresseActivity.class));
        }
        if (id == R.id.logo_maos) {
            startActivity(new Intent(this, TelaInicialActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
