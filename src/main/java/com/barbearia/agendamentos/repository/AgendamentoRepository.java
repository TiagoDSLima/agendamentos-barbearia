package com.barbearia.agendamentos.repository;

import com.barbearia.agendamentos.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Método para buscar agendamentos dentro de um intervalo de tempo
    List<Agendamento> findByHorarioInicioBetween(LocalDateTime start, LocalDateTime end);

    // Método para verificar se já existe um agendamento no horário específico
    boolean existsByHorarioInicio(LocalDateTime horarioInicio);
}