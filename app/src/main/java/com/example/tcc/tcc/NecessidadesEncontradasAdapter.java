package com.example.tcc.tcc;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Juliana on 24/05/2018.
 */
public class NecessidadesEncontradasAdapter extends BaseAdapter {

    private Context ctx;
    private List<Necessidade> lista;
    private LayoutInflater mInflater;

    public NecessidadesEncontradasAdapter(Context ctx2, List<Necessidade> lista2){//recebe os dados do contexto e da lista, quando for instancia passa esse contrutor
        ctx = ctx2;
        lista = lista2;
        mInflater = LayoutInflater.from(ctx2);
    }

    @Override
    public int getCount() { //saber o tamanho da lista

        return lista.size();
    }

    @Override
    public Necessidade getItem(int position) {// sabe capturar cada item da lista, cmo se fosse um loop

        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.linha_necessidades_ong, null);

        Necessidade necessidade = getItem(position);

        TextView itemNomeNecessidade = (TextView) convertView.findViewById(R.id.lh_obj_necessidade);
        TextView itemDescricaoNecessidade = (TextView )  convertView.findViewById(R.id.lh_descricao_necessidade);


        itemNomeNecessidade.setText(necessidade.getNecessidade());
        itemDescricaoNecessidade.setText(necessidade.getDescricao());


        return convertView;
    }
}
