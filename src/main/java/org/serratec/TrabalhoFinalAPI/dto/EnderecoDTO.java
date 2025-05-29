package org.serratec.TrabalhoFinalAPI.dto;

import org.serratec.TrabalhoFinalAPI.domain.Endereco;

public class EnderecoDTO {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String uf;
    private String cidade;

    public EnderecoDTO(Endereco endereco) {
        this.cep = endereco.getCep();
        this.logradouro = endereco.getLogradouro();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.uf = endereco.getUf();
        this.cidade = endereco.getCidade();
    }
}
