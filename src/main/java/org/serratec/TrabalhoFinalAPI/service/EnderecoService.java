package org.serratec.TrabalhoFinalAPI.service;

import org.serratec.TrabalhoFinalAPI.domain.Endereco;
import org.serratec.TrabalhoFinalAPI.dto.EnderecoViaCepDTO;
import org.serratec.TrabalhoFinalAPI.exception.CepException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class EnderecoService {

   public Endereco adicionarEndereco(String cep) {
       RestTemplate restTemplate = new RestTemplate();
       String url = "http://viacep.com.br/ws/" + cep + "/json/";
       Optional<EnderecoViaCepDTO> enderecoViaOtp = Optional.ofNullable(restTemplate.getForObject(url, EnderecoViaCepDTO.class));
       if (enderecoViaOtp.isPresent() && enderecoViaOtp.get().getCep() != null) {
           Endereco endereco = new Endereco(enderecoViaOtp.get());
           String cepSemTraco = endereco.getCep().replaceAll("-", "");
           endereco.setCep(cepSemTraco);
           return endereco;
       } else {
           throw new CepException("Busca de endereço por CEP na api não retornou resultados!");
       }
   }


}
