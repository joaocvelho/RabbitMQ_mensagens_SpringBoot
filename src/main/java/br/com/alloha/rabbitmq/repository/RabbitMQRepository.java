package br.com.alloha.rabbitmq.repository;

import br.com.alloha.rabbitmq.model.RabbitMQModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RabbitMQRepository extends JpaRepository<RabbitMQModel, Long> {

    Page<RabbitMQModel> findAllByOrderByIdDesc(Pageable pageable);
}
