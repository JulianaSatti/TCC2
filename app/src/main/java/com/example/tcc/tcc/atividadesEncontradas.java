package com.example.tcc.tcc;

/**
 * Created by Juliana on 01/05/2018.
 */

public class atividadesEncontradas {

    private String id;
    private String id_user;
    private String nomeOng;
    private String nomeAtividade;
    private String descricao;
    private String area_interesse;


    public atividadesEncontradas(){
        this.id = id;
        this.nomeOng = nomeOng;
        this.nomeAtividade = nomeAtividade;
        this.descricao = descricao;
        this.area_interesse = area_interesse;
        this.id_user = id_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getNomeOng() {

        return nomeOng;
    }

    public void setNomeOng(String nomeOng) {

        this.nomeOng = nomeOng;
    }

    public String getNomeAtividade() {

        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {

        this.nomeAtividade = nomeAtividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getArea_interesse() {
        return area_interesse;
    }

    public void setArea_interesse(String area_interesse) {
        this.area_interesse = area_interesse;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
