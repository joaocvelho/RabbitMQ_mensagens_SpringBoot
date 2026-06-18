package br.com.alloha.rabbitmq.repository;

import br.com.alloha.rabbitmq.model.RabbitMQModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RabbitMQRepository extends JpaRepository<RabbitMQModel, Long> {

}
