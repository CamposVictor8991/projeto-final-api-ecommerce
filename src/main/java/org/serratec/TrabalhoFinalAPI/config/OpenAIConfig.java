package org.serratec.TrabalhoFinalAPI.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAIConfig {

    @Value("http://localhost:8080")
    private String devUrl;

    @Value("http://localhost:8080")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor de desenvolvimento");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("URL do servidor de produção");

        Contact contact = new Contact();
        contact.setEmail("contato@serratec.org");
        contact.setName("Iara Continho, Bruno Gross, Victor Campos, Laryssa Peixoto, Daniel Lopes, Arthur Gomes.");
        contact.setUrl("https://www.serratec.org");

        License apacheLicense = new License()
                .name("Apache License")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("API para Ecommerce")
                .version("1.0")
                .contact(contact)
                .description("API para gerenciamento de um ecommerce, desenvolvida como trabalho final do curso de Especialização em Desenvolvimento de Software da Serratec.")
                .termsOfService("https://www.meudominio.com.br/termos")
                .license(apacheLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
