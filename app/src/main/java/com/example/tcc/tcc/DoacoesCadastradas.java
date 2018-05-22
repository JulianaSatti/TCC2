package com.example.tcc.tcc;

public class DoacoesCadastradas {

    private String id;
    private String user_id;
    private String ong_id;
    private String nome;
    private String categoria;
    private String descricao;
    private String levar_local;
    private static String id_user;

    public DoacoesCadastradas() {
        this.id = "";
        this.user_id = "";
        this.ong_id = "";
        this.nome = "";
        this.categoria = "";
        this.descricao = "";
        this.levar_local = "";
    }

    public static void setId_user(String id_user) {
        DoacoesCadastradas.id_user = id_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOng_id() {
        return ong_id;
    }

    public void setOng_id(String ong_id) {
        this.ong_id = ong_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLevar_local() {
        return levar_local;
    }

    public void setLevar_local(String levar_local) {
        this.levar_local = levar_local;
    }
}
