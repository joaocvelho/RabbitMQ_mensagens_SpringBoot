package br.com.alloha.rabbitmq.exception;

public class ConexaoException extends RuntimeException {
    public ConexaoException(String mensagem) {
        super(mensagem);
    }

    public ConexaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
