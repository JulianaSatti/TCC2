package com.example.tcc.tcc;

public class Necessidade {


    private String id;
    private String ong_id;
    private String necessidade;
    private String descricao;


    public Necessidade(){
        this.id = id;
        this.ong_id = ong_id;
        this.necessidade = necessidade;
        this.descricao = descricao;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOng_id() {
        return ong_id;
    }

    public void setOng_id(String ong_id) {
        this.ong_id = ong_id;
    }

    public String getNecessidade() {
        return necessidade;
    }

    public void setNecessidade(String necessidade) {
        this.necessidade = necessidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}


