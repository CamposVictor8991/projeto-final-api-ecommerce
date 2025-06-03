package org.serratec.TrabalhoFinalAPI.dto;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Pedido;
import org.serratec.TrabalhoFinalAPI.domain.Produto;
import org.serratec.TrabalhoFinalAPI.enuns.Status;

public class PedidoDTO {
    private Long id;
    private ClienteResumoDTO cliente;
    private EnderecoResumoDTO endereco;
    private Status status;
    private List<PedidoProdutoResumoDTO> pedidoProdutos;
    private Double valorVenda;
    private Double desconto;
    private Double total;
    private List<Produto> relacionados;

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = new ClienteResumoDTO(pedido.getCliente());
        this.endereco = new EnderecoResumoDTO(pedido.getEndereco());
        this.status = pedido.getStatus();
        this.valorVenda = pedido.getValorVenda();
        this.pedidoProdutos = pedido.getPedidoProdutos().stream()
            .map(PedidoProdutoResumoDTO::new)
            .toList();
        this.desconto = pedido.getDesconto();
        this.total = pedido.getTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteResumoDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteResumoDTO cliente) {
        this.cliente = cliente;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public EnderecoResumoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoResumoDTO endereco) {
        this.endereco = endereco;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<PedidoProdutoResumoDTO> getPedidoProdutos() {
        return pedidoProdutos;
    }

    public void setPedidoProdutos(List<PedidoProdutoResumoDTO> pedidoProdutos) {
        this.pedidoProdutos = pedidoProdutos;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Produto> getRelacionados() {
        return relacionados;
    }

    public void setRelacionados(List<Produto> relacionados) {
        this.relacionados = relacionados;
    }
}
