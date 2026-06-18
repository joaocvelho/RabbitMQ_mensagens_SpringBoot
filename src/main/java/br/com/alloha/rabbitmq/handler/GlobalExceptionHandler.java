package br.com.alloha.rabbitmq.handler;

import br.com.alloha.rabbitmq.exception.ConexaoException;
import br.com.alloha.rabbitmq.exception.MensagemNaoEnviadaException;
import br.com.alloha.rabbitmq.handler.ErroResposta.ErroValidacao;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MensagemNaoEnviadaException.class)
    public ResponseEntity<ErroResposta> handleMensagemNaoEnviada(MensagemNaoEnviadaException ex, WebRequest request) {
        ErroResposta erro = ErroResposta.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .erro("Service Unavailable")
                .mensagem(ex.getMessage())
                .caminho(getCaminho(request))
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(erro);
    }

    @ExceptionHandler(ConexaoException.class)
    public ResponseEntity<ErroResposta> handleConexaoException(ConexaoException ex, WebRequest request) {
        ErroResposta erro = ErroResposta.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .erro("Service Unavailable")
                .mensagem(ex.getMessage())
                .caminho(getCaminho(request))
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(erro);
    }

    @ExceptionHandler(AmqpConnectException.class)
    public ResponseEntity<ErroResposta> handleAmqpConnectException(AmqpConnectException ex, WebRequest request) {
        ErroResposta erro = ErroResposta.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .erro("Service Unavailable")
                .mensagem("Conexão com o RabbitMQ recusada. Verifique se o servidor RabbitMQ está em execução.")
                .caminho(getCaminho(request))
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(erro);
    }

    @ExceptionHandler(AmqpException.class)
    public ResponseEntity<ErroResposta> handleAmqpException(AmqpException ex, WebRequest request) {
        ErroResposta erro = ErroResposta.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .erro("Internal Server Error")
                .mensagem("Erro de comunicação com o RabbitMQ: " + ex.getMessage())
                .caminho(getCaminho(request))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        List<ErroValidacao> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ErroValidacao.builder()
                        .campo(fieldError.getField())
                        .mensagem(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ErroResposta erro = ErroResposta.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Bad Request")
                .mensagem("Erro de validação nos campos enviados")
                .caminho(getCaminho(request))
                .errosValidacao(erros)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenerico(Exception ex, WebRequest request) {
        ErroResposta erro = ErroResposta.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .erro("Internal Server Error")
                .mensagem("Ocorreu um erro inesperado: " + ex.getMessage())
                .caminho(getCaminho(request))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    private String getCaminho(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
