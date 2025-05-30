package org.serratec.TrabalhoFinalAPI.dto;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.PedidoProduto;

public class PedidoInserirDTO {
    private Long clienteId;
    private Long enderecoId;
    private List<PedidoProduto> pedidoProdutos;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public List<PedidoProduto> getPedidoProdutos() {
        return pedidoProdutos;
    }

    public void setPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
        this.pedidoProdutos = pedidoProdutos;
    }

}
