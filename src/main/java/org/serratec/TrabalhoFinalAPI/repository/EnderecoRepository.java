package org.serratec.TrabalhoFinalAPI.repository;

import org.serratec.TrabalhoFinalAPI.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    public Endereco findByCep(String cep);
}
