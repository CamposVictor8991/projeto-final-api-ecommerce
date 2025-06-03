package org.serratec.TrabalhoFinalAPI.service;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Produto;
import org.serratec.TrabalhoFinalAPI.dto.ProdutoInserirDTO;
import org.serratec.TrabalhoFinalAPI.exception.CpfException;
import org.serratec.TrabalhoFinalAPI.exception.RuntimeMensagemException;
import org.serratec.TrabalhoFinalAPI.repository.CategoriaRepository;
import org.serratec.TrabalhoFinalAPI.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}

	public Produto inserir(ProdutoInserirDTO produtoInserirDTO) throws RuntimeMensagemException {
		Produto produto = new Produto(produtoInserirDTO);
		produto.setCategoria(categoriaRepository.findById(produtoInserirDTO.getIdCategoria())
				.orElseThrow(() -> new RuntimeMensagemException("Categoria não encontrada")));

		return produtoRepository.save(produto);
	}
	public Produto atualizar(Long id, ProdutoInserirDTO produtoInserirDTO) {
		Produto produtoAtualizado = produtoRepository.findById(id).orElse(null);
		if (produtoAtualizado != null) {
			produtoAtualizado.setNomeProduto(produtoInserirDTO.getNomeProduto());
			produtoAtualizado.setDescricao(produtoInserirDTO.getDescricaoProduto());
			produtoAtualizado.setPreco(produtoInserirDTO.getPrecoProduto());
			produtoAtualizado.setCategoria(categoriaRepository.findById(produtoInserirDTO.getIdCategoria())
					.orElseThrow(() -> new RuntimeMensagemException("Categoria não encontrada")));
			produtoAtualizado = produtoRepository.save(produtoAtualizado);
		}

		return produtoAtualizado;
	}
	public List<Produto> listarRelacionados(Long produtoId) throws RuntimeMensagemException {
	    Produto produto = produtoRepository.findById(produtoId)
	        .orElseThrow(() -> new RuntimeMensagemException("Produto não encontrado"));
	    // Busca produtos da mesma categoria, exceto o próprio produto
	    return produtoRepository.findByCategoriaIdAndIdNot(produto.getCategoria().getId(), produtoId);
	}
	public boolean deletar(Long id) {
	    if (produtoRepository.existsById(id)) {
	        produtoRepository.deleteById(id);
	        return true;
	    }
	    return false;
	}




}
