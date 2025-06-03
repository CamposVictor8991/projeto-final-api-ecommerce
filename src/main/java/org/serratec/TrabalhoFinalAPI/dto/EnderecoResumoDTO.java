package org.serratec.TrabalhoFinalAPI.dto;

import org.serratec.TrabalhoFinalAPI.domain.Endereco;

public class EnderecoResumoDTO {

    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;

    public EnderecoResumoDTO(Endereco endereco) {
        this.cep = endereco.getCep();
        this.rua = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.uf = endereco.getUf();
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
