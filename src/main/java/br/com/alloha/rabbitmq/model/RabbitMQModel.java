package br.com.alloha.rabbitmq.model;

import br.com.alloha.rabbitmq.enums.StatusMensagemEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tb_fila_mensagem")
@Schema(description = "Entidade que representa uma mensagem de e-mail persistida no banco")

// Serializable permite que a entidade seja convertida em uma sequência de bytes para ser trafegada ou armazenada
public class RabbitMQModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da mensagem", example = "1")
    private Long id;

    @NotBlank
    @Email
    @Schema(description = "E-mail do destinatário", example = "usuario@exemplo.com")
    private String email;

    @NotBlank
    @Schema(description = "Nome do destinatário", example = "João Silva")
    private String nome;

    @NotBlank
    @Schema(description = "Conteúdo da mensagem", example = "Olá, este é um e-mail de teste.")
    private String mensagem;

    @Schema(description = "Observação opcional", example = "Envio em lote")
    private String observacao;

    @Schema(description = "Data e hora do envio", example = "2026-06-18T10:30:00")
    private LocalDateTime dataMensagem;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status da mensagem", example = "ENVIADO", allowableValues = {"ENVIADO", "ERRO_ENVIO"})
    private StatusMensagemEnum status;
}
