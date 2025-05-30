package org.serratec.TrabalhoFinalAPI.repository;

import org.serratec.TrabalhoFinalAPI.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    

}
