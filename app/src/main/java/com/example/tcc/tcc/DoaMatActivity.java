package com.example.tcc.tcc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class DoaMatActivity extends AppCompatActivity {
    private Spinner spinner_obj;
    private RadioGroup radioGroup;
    private Button enviar_obj;
    private RadioButton radioEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doamat);

        //ira mostrar os itens do spinner
        spinner_obj = (Spinner) findViewById(R.id.spinner_obj);

        List<String> escolha = new ArrayList<>(Arrays.asList("Acessórios", "Alimentos", "Brinquedos", "Calçados", "Eletrodomésticos", "Eletroeletrônicos", "Livros", "Moveis", "Obejtos de Decoração", "Roupas", "Utilidades domésticas"));
//está usando o adapter para criar um array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                simple_spinner_item, escolha );
//especificar o layout para usar quando a lista de escolhas aparecer
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //simple.. é um layout (spinner) padrão
        //aplica o adapter para o spinner
        spinner_obj.setAdapter(dataAdapter);

        addListenerOnButton();
    }

    private void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radioOP);
        enviar_obj = (Button) findViewById(R.id.enviar_obj);

        enviar_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

// recebe o botão de opção selecionado do radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // encontra o radiobutton pelo ID retornado
                radioEntrega = (RadioButton) findViewById(selectedId);

                Toast.makeText(DoaMatActivity.this, radioEntrega.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
