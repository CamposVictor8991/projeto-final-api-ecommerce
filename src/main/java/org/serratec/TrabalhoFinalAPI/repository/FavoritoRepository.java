package org.serratec.TrabalhoFinalAPI.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.TrabalhoFinalAPI.domain.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
  List<Favorito> findByClienteId(Long clienteId);
  boolean existsByClienteIdAndProdutoId(Long clienteId, Long produtoId);
  void deleteByClienteIdAndProdutoId(Long clienteId, Long produtoId);
	
  Optional<Favorito> findByClienteIdAndProdutoId(Long clienteId, Long produtoId);

  
}
