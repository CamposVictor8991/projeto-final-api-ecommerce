package org.serratec.TrabalhoFinalAPI.service;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Categoria;
import org.serratec.TrabalhoFinalAPI.dto.CategoriaInserirDTO;
import org.serratec.TrabalhoFinalAPI.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> listarTodos() {
		return categoriaRepository.findAll();
	}

	public Categoria inserir(CategoriaInserirDTO categoria) {
		Categoria c = new Categoria(categoria);
		categoriaRepository.save(c);
		return c;

	}
	
	public Categoria atualizar(long id, CategoriaInserirDTO categoria) {
		Categoria categoriaAtualizada = categoriaRepository.findById(id).orElse(null);
		if (categoriaAtualizada != null) {
			categoriaAtualizada.setNomeCategoria(categoria.getNomeCategoria());
			categoriaAtualizada.setDescricaoCategoria(categoria.getDescricaoCategoria());
			categoriaAtualizada = categoriaRepository.save(categoriaAtualizada);
		}
		return categoriaAtualizada;
	}
}
