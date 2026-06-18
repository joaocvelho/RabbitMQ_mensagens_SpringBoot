package br.com.alloha.rabbitmq.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("API RabbitMQ - Envio de E-mails")
                .description("API para envio de mensagens de e-mail utilizando fila RabbitMQ\n\n" +
                                "**Fluxo:**\n" +
                                "1. Cliente envia requisição POST com dados do e-mail\n" +
                                "2. Serviço persiste a mensagem no banco MySQL\n" +
                                "3. Produtor envia a mensagem para a fila RabbitMQ\n" +
                                "4. Consumidor escuta a fila e processa a mensagem"));
    }
}
