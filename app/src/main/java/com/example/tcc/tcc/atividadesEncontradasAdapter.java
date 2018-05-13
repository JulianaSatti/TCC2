package com.example.tcc.tcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Juliana on 01/05/2018.
 */

public class atividadesEncontradasAdapter extends BaseAdapter {

    private Context ctx;
    private List<atividadesEncontradas> lista;

    public atividadesEncontradasAdapter(Context ctx2, List<atividadesEncontradas> lista2){//recebe os dados do contexto e da lista, quando for instancia passa esse contrutor
        ctx = ctx2;
        lista = lista2;
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
        View v = null;

        if(convertView == null){
            LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
            v = inflater.inflate(R.layout.linha_ativ_encontrada, null);
        }else{
            v = convertView;
        }

        atividadesEncontradas atividade = getItem(position);

        TextView itemNomeONG = (TextView) v.findViewById(R.id.lh_nome_ong);
        TextView itemNomeAtividade = (TextView )  v.findViewById(R.id.lh_nome_atividade);

        itemNomeONG.setText(atividade.getNomeOng());
        itemNomeAtividade.setText(atividade.getNomeAtividade());

        return v;
    }
}

