package com.example.tcc.tcc;

public class Categoria {
    private String categoria;


    public Categoria(){

    }

    public Categoria(String categoria)
    {

        this.categoria = categoria;
    }
    public void setCategoria (String categoria){

        this.categoria = categoria;
    }

    @Override
    public String toString(){

        return categoria;
    }


}