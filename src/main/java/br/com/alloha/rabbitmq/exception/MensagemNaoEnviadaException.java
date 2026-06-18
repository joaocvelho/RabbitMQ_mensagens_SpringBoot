package br.com.alloha.rabbitmq.exception;

public class MensagemNaoEnviadaException extends RuntimeException {
    public MensagemNaoEnviadaException(String mensagem) {
        super(mensagem);
    }

    public MensagemNaoEnviadaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
