package com.example.tcc.tcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AtividadesInteresseAdapter extends BaseAdapter {

    private Context context;
    private List<InteresseAtividade> list;
    private LayoutInflater layoutInflater;

    public AtividadesInteresseAdapter(Context context2, List<InteresseAtividade> list2){
        context = context2;
        list = list2;
        layoutInflater = LayoutInflater.from(context2);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public InteresseAtividade getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.linha_atividades_interesse_encontradas, null);

        InteresseAtividade interesse = getItem(position);

        TextView itemNomeAtividade = (TextView) view.findViewById(R.id.textViewNomeAtividadeInteresse);
        TextView itemDescricaoAtividade = (TextView) view.findViewById(R.id.textViewDescricaoAtividadeInteresse);

        itemNomeAtividade.setText(interesse.getAtividade());
        itemDescricaoAtividade.setText(interesse.getDescricao());





        return view;
    }
}
