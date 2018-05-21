package com.example.tcc.tcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class bancosEncontradosAdapter extends BaseAdapter {

    private Context ctx;
    private List<atividadesEncontradas> lista;
    private LayoutInflater mInflater;

    public bancosEncontradosAdapter(Context ctx2, List<atividadesEncontradas> lista2){//recebe os dados do contexto e da lista, quando for instancia passa esse contrutor
        ctx = ctx2;
        lista = lista2;
        mInflater = LayoutInflater.from(ctx2);
    }

    @Override
    public int getCount() { //saber o tamanho da lista

        return lista.size();
    }

    @Override
    public atividadesEncontradas getItem(int position) {// sabe capturar cada item da lista, cmo se fosse um loop

        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.banco_encontrado, null);

        atividadesEncontradas atividade = getItem(position);

        TextView itemBanco = (TextView) convertView.findViewById(R.id.txt_banco);
        TextView itemAgencia = (TextView )  convertView.findViewById(R.id.txt_agencia);
        TextView itemConta = (TextView )  convertView.findViewById(R.id.txt_conta);

        itemBanco.setText("Banco: "+atividade.getNomeAtividade());
        itemAgencia.setText("Agência: "+atividade.getDescricao());
        itemConta.setText("Conta: "+atividade.getArea_interesse());

        return convertView;
    }
}

