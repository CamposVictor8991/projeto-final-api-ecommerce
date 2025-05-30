package org.serratec.TrabalhoFinalAPI.dto;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.PedidoProduto;

public class PedidoInserirDTO {
    private Long enderecoId;
    private List<PedidoProdutoDTO> pedidoProdutos;


    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public List<PedidoProdutoDTO> getPedidoProdutos() {
        return pedidoProdutos;
    }

    public void setPedidoProdutos(List<PedidoProdutoDTO> pedidoProdutos) {
        this.pedidoProdutos = pedidoProdutos;
    }

}
