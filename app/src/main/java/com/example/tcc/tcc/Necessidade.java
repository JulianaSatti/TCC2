package com.example.tcc.tcc;

public class Necessidade {
    private String necessidade;


    public Necessidade(){

    }

    public Necessidade (String Necessidade) {

        this.necessidade = necessidade;
    }
    public void setNecessidade (String necessidade){

        this.necessidade = necessidade;
    }

    @Override
    public String toString(){

        return necessidade;
    }

}


