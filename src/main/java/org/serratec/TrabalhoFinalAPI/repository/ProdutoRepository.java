package org.serratec.TrabalhoFinalAPI.repository;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaIdAndIdNot(Long categoriaId, Long id);

}
