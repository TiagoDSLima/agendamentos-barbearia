package com.barbearia.agendamentos.repository;

import com.barbearia.agendamentos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
