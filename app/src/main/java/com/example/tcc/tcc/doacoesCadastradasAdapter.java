package com.example.tcc.tcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class doacoesCadastradasAdapter extends BaseAdapter {

    private Context context;
    private List<DoacoesCadastradas> list;
    private LayoutInflater layoutInflater;

    public doacoesCadastradasAdapter(Context context2, List<DoacoesCadastradas> list2){
        context = context2;
        list = list2;
        layoutInflater = LayoutInflater.from(context2);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DoacoesCadastradas getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.linha_doacao_encontrada, null);

        DoacoesCadastradas doacoes = getItem(position);

        TextView itemNomeDoacao = (TextView) view.findViewById(R.id.textViewNomeDoacao);
        TextView itemCategoriaDoacao = (TextView) view.findViewById(R.id.textViewCategoriaDoacao);
        TextView itemDescricaoDoacao = (TextView) view.findViewById(R.id.textViewDescricaoDaocao);

        itemNomeDoacao.setText(doacoes.getNome());
        itemCategoriaDoacao.setText(doacoes.getCategoria());
        itemCategoriaDoacao.setText(doacoes.getDescricao());

        return view;


    }


}
