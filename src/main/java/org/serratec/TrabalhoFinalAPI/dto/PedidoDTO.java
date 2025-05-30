package org.serratec.TrabalhoFinalAPI.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.serratec.TrabalhoFinalAPI.domain.Cliente;
import org.serratec.TrabalhoFinalAPI.domain.Endereco;
import org.serratec.TrabalhoFinalAPI.domain.Pedido;
import org.serratec.TrabalhoFinalAPI.domain.PedidoProduto;
import org.serratec.TrabalhoFinalAPI.enuns.Status;

public class PedidoDTO {
    private Long id;
    private ClienteResumoDTO cliente;
    private EnderecoResumoDTO endereco;
    private Status status;
    private List<PedidoProdutoResumoDTO> pedidoProdutos;
    private Double total;

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = new ClienteResumoDTO(pedido.getCliente());
        this.endereco = new EnderecoResumoDTO(pedido.getEndereco());
        this.status = pedido.getStatus();
        this.total = pedido.getValorVenda();
        this.pedidoProdutos = pedido.getPedidoProdutos().stream()
            .map(PedidoProdutoResumoDTO::new)
            .toList();
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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
}
