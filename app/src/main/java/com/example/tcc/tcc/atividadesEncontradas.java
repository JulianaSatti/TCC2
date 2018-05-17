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
    private String data;
    private String hora_inicio;
    private String hora_termino;
    private String local;
    private String voluntario;
    private String qtd_voluntario;


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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_termino() {
        return hora_termino;
    }

    public void setHora_termino(String hora_termino) {
        this.hora_termino = hora_termino;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(String voluntario) {
        this.voluntario = voluntario;
    }

    public String getQtd_voluntario() {
        return qtd_voluntario;
    }

    public void setQtd_voluntario(String qtd_voluntario) {
        this.qtd_voluntario = qtd_voluntario;
    }
}
