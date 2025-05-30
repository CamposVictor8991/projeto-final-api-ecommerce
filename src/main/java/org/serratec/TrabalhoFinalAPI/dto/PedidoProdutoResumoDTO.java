package org.serratec.TrabalhoFinalAPI.dto;

import org.serratec.TrabalhoFinalAPI.domain.PedidoProduto;

public class PedidoProdutoResumoDTO {

    private String nomeProduto;
    private Double preco;
    private Integer quantidade;

    public PedidoProdutoResumoDTO(PedidoProduto pedidoProduto) {
        this.nomeProduto = pedidoProduto.getProduto().getNomeProduto();
        this.preco = pedidoProduto.getProduto().getPreco();
        this.quantidade = pedidoProduto.getQuantidade();
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
