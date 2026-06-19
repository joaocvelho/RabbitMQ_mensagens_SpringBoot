package br.com.alloha.rabbitmq.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "DTO de entrada para envio de e-mail")
public class RabbitMQDTO {

    // Schema descreve cada campo dos modelos/DTOs que aparecem no Swagger UI
    @Schema(description = "ID gerado automaticamente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Preencher os campos obrigatórios")
    @Email(message = "Preencher os campos obrigatórios")
    @Schema(description = "E-mail do remetente", example = "usuario@exemplo.com")
    private String email;

    @NotBlank(message = "Preencher os campos obrigatórios")
    @Schema(description = "Nome do remetente", example = "João Silva")
    private String nome;

    @NotBlank(message = "Preencher os campos obrigatórios")
    @Email(message = "Preencher os campos obrigatórios")
    @Schema(description = "E-mail do destinatário", example = "usuario@exemplo.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email_destinatario;

    @NotBlank(message = "Preencher os campos obrigatórios")
    @Schema(description = "Nome do destinatário", example = "José Souza", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome_destinatario;

    @NotBlank(message = "Preencher os campos obrigatórios")
    @Schema(description = "Conteúdo da mensagem", example = "Olá, este é um e-mail de teste.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mensagem;

    @Schema(description = "Observação opcional", example = "Envio para todos")
    private String observacao;
}
