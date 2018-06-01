package com.example.tcc.tcc;

public class NotificacoesEncontradas {

    private String id;
    private String ong_id;
    private String id_user;
    private String nomeOng;
    private String tipoId;
    private String descricao;
    private String tipo;
    private String data;
    private String hora_inicio;
    private String telefone;
    private String visualizada;
    private String voluntario;
    private String qtd_voluntario;


    public NotificacoesEncontradas(){
        this.id = id;
        this.nomeOng = nomeOng;
        this.tipoId = tipoId;
        this.descricao = descricao;
        this.tipo = tipo;
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

    public String getTipoId() {

        return tipoId;
    }

    public void setTipoId(String tipoId) {

        this.tipoId = tipoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getVisualizada() {
        return visualizada;
    }

    public void setVisualizada(String visualizada) {
        this.visualizada = visualizada;
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

    public String getOng_id() {
        return ong_id;
    }

    public void setOng_id(String ong_id) {
        this.ong_id = ong_id;
    }
}