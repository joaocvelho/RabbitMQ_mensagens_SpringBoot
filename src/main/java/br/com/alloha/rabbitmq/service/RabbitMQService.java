package br.com.alloha.rabbitmq.service;

import br.com.alloha.rabbitmq.enums.StatusMensagemEnum;
import br.com.alloha.rabbitmq.exception.ConexaoException;
import br.com.alloha.rabbitmq.exception.MensagemNaoEnviadaException;
import br.com.alloha.rabbitmq.model.RabbitMQModel;
import br.com.alloha.rabbitmq.produtor.RabbitMQProdutor;
import br.com.alloha.rabbitmq.repository.RabbitMQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitMQRepository rabbitMQRepository;

    @Autowired
    private RabbitMQProdutor rabbitMQProdutor;

    @Transactional(noRollbackFor = {MensagemNaoEnviadaException.class, ConexaoException.class})
    public RabbitMQModel enviarEmail(RabbitMQModel rabbitMQModel) {
        rabbitMQModel.setDataMensagem(LocalDateTime.now());
        rabbitMQModel.setStatus(StatusMensagemEnum.ENVIADO);

        RabbitMQModel saved = rabbitMQRepository.save(rabbitMQModel);

        try {
            rabbitMQProdutor.enviarParaFila(saved);
        } catch (MensagemNaoEnviadaException | ConexaoException e) {
            saved.setStatus(StatusMensagemEnum.ERRO_ENVIO);
            rabbitMQRepository.save(saved);
            throw e;
        }

        return saved;
    }

    public Page<RabbitMQModel> listarMensagens(Pageable pageable) {
        return rabbitMQRepository.findAllByOrderByIdDesc(pageable);
    }
}
