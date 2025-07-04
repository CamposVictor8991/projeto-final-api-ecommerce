package org.serratec.TrabalhoFinalAPI.repository;

import org.serratec.TrabalhoFinalAPI.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
