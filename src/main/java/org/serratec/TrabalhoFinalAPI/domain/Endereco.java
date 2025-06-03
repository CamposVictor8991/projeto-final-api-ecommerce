package org.serratec.TrabalhoFinalAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.serratec.TrabalhoFinalAPI.dto.EnderecoViaCepDTO;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    @Size(max = 2)
    private String uf;

    @Size(max = 100, message = "Preencha até ${max} caracteres.")
    private String complemento;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public Endereco() {
    }

    public Endereco(EnderecoViaCepDTO dto) {
        this.cep = dto.getCep();
        this.logradouro = dto.getLogradouro();
        this.bairro = dto.getBairro();
        this.cidade = dto.getLocalidade();
        this.uf = dto.getUf();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return logradouro + ", n° " + numero + " - " + bairro + ", " + cidade + " - " + uf + ", CEP: " + cep
            + (complemento != null ? ", Complemento: " + complemento : ".");

    }

}
