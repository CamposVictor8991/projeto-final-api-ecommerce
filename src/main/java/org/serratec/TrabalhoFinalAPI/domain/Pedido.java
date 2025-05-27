package org.serratec.TrabalhoFinalAPI.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinColumn(name="id_cliente")
    private Cliente cliente;

    @ManyToMany
    @JoinColumn(name="id_endereco")
    private Endereco endereco;

    @JsonManagedReference
    @ManyToMany(mappedBy="pedido")
    List<Produto> produtos;

    
}
