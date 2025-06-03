package org.serratec.TrabalhoFinalAPI.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.TrabalhoFinalAPI.domain.Cliente;
import org.serratec.TrabalhoFinalAPI.domain.Favorito;
import org.serratec.TrabalhoFinalAPI.domain.Produto;
import org.serratec.TrabalhoFinalAPI.exception.RuntimeMensagemException;
import org.serratec.TrabalhoFinalAPI.repository.ClienteRepository;
import org.serratec.TrabalhoFinalAPI.repository.FavoritoRepository;
import org.serratec.TrabalhoFinalAPI.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void adicionarFavorito(Long clienteId, Long produtoId) {
        if (favoritoRepository.existsByClienteIdAndProdutoId(clienteId, produtoId)) {
            throw new RuntimeException("Produto já está nos favoritos.");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeMensagemException("Cliente não encontrado."));
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new RuntimeMensagemException("Produto não encontrado."));

        Favorito favorito = new Favorito();
        favorito.setCliente(cliente);
        favorito.setProduto(produto);

        favoritoRepository.save(favorito);
    }

//deleta um item do favorido
    public void removerFavorito(Long clienteId, Long produtoId) {
    Favorito favorito = favoritoRepository.findByClienteIdAndProdutoId(clienteId, produtoId)
        .orElseThrow(() -> new RuntimeMensagemException("Favorito não encontrado."));
    favoritoRepository.delete(favorito);
    }
    

    public List<Produto> listarFavoritos(Long clienteId) {
        List<Favorito> favoritos = favoritoRepository.findByClienteId(clienteId);
        return favoritos.stream()
            .map(Favorito::getProduto)
            .collect(Collectors.toList());
    }
}

