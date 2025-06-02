package org.serratec.TrabalhoFinalAPI.service;

import java.util.List;

import org.serratec.TrabalhoFinalAPI.domain.Perfil;
import org.serratec.TrabalhoFinalAPI.dto.PerfilInserirDTO;
import org.serratec.TrabalhoFinalAPI.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public List<Perfil> listarPefis() {
		return perfilRepository.findAll();
	}

    public Perfil atualizarPerfil(long id, PerfilInserirDTO perfil) {
		Perfil perfilAtualizado = perfilRepository.findById(id).orElse(null);
		if (perfilAtualizado != null) {
			perfilAtualizado.setNome(perfil.getNome());
			perfilAtualizado = perfilRepository.save(perfilAtualizado);
		}
		return perfilAtualizado;
	}
}
