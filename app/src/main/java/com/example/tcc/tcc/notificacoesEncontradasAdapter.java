package com.example.tcc.tcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class notificacoesEncontradasAdapter extends BaseAdapter {

    private Context ctx;
    private List<NotificacoesEncontradas> lista;
    private LayoutInflater mInflater;

    public notificacoesEncontradasAdapter(Context ctx2,List<NotificacoesEncontradas> lista2){//recebe os dados do contexto e da lista, quando for instancia passa esse contrutor
        ctx = ctx2;
        lista = lista2;
        mInflater = LayoutInflater.from(ctx2);
    }

    @Override
    public int getCount() { //saber o tamanho da lista

        return lista.size();
    }

    @Override
    public NotificacoesEncontradas getItem(int position) {// sabe capturar cada item da lista, cmo se fosse um loop

        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.notificacoes_ong, null);

        NotificacoesEncontradas notificacao = getItem(position);

        //   TextView itemNome = (TextView) convertView.findViewById(R.id.ntNome);
        TextView itemDescricao = (TextView )  convertView.findViewById(R.id.ntDesc);
        TextView itemTelefone = (TextView )  convertView.findViewById(R.id.ntTel);
        TextView itemTipo = (TextView )  convertView.findViewById(R.id.ntTipo);
        TextView itemData = (TextView )  convertView.findViewById(R.id.ntData);

        //  itemNome.setText(" "+notificacao.getNomeNotif());
        itemDescricao.setText(" "+notificacao.getDescricao());
        itemTelefone.setText(" "+notificacao.getTelefone());
        itemTipo.setText("Tipo: "+notificacao.getTipo());
        itemData.setText(" "+notificacao.getData());

        return convertView;
    }
}

