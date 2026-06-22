package br.com.alloha.rabbitmq.controller;

import br.com.alloha.rabbitmq.dto.RabbitMQDTO;
import br.com.alloha.rabbitmq.handler.ErroResposta;
import br.com.alloha.rabbitmq.model.RabbitMQModel;
import br.com.alloha.rabbitmq.service.RabbitMQService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Mensagens", description = "Endpoints para envio e consulta de mensagens via RabbitMQ")
public class RabbitMQController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @Operation(summary = "Enviar e-mail para a fila", description = "Recebe os dados do e-mail, persiste no banco e envia para a fila RabbitMQ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mensagem enviada com sucesso",
                    content = @Content(schema = @Schema(implementation = RabbitMQModel.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos - erro de validação",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "503", description = "RabbitMQ indisponível - falha de conexão ou envio",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    @PostMapping("/envio-email")
    public ResponseEntity<RabbitMQModel> envioEmail(@RequestBody @Valid RabbitMQDTO rabbitMQDTO) {
        RabbitMQModel rabbitMQModel = new RabbitMQModel();
        BeanUtils.copyProperties(rabbitMQDTO, rabbitMQModel, "id");
        rabbitMQService.enviarEmail(rabbitMQModel);
        return new ResponseEntity<>(rabbitMQModel, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar mensagens com paginação", description = "Retorna mensagens persistidas no banco MySQL com paginação. Parâmetros: page, size, sort")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de mensagens retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class)))
    })
    @GetMapping("/mensagens")
    public ResponseEntity<Page<RabbitMQModel>> listarMensagens(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        Page<RabbitMQModel> mensagens = rabbitMQService.listarMensagens(pageable);
        return ResponseEntity.ok(mensagens);
    }
}
