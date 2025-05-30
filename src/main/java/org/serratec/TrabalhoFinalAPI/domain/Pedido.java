package org.serratec.TrabalhoFinalAPI.domain;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.serratec.TrabalhoFinalAPI.enuns.Status;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double valorVenda;
	private double desconto;
	private LocalDate dataPedido = LocalDate.now();

	@Enumerated(EnumType.STRING)
	private Status status = Status.PENDENTE;

    @JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

    @JsonBackReference
	@OneToOne
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;

	@JsonManagedReference
	@OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER)
	List<PedidoProduto> pedidoProdutos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<PedidoProduto> getPedidoProdutos() {
		return pedidoProdutos;
	}

	public void setPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
		this.pedidoProdutos = pedidoProdutos;
	}

	public double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(double valorVenda) {
		this.valorVenda = valorVenda;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
