package com.barbearia.agendamentos.model;

import com.barbearia.agendamentos.enums.ServicoTipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento ManyToOne com a entidade Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotBlank(message = "O serviço não pode ser vazio") // Validação de campo não vazio
    @Enumerated(EnumType.STRING)
    private ServicoTipo servico;

    @NotNull(message = "O horário de início não pode ser nulo") // Validação de horário de início
    private LocalDateTime horarioInicio;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ServicoTipo getServico() {
        return servico;
    }

    public void setServico(ServicoTipo servico) {
        this.servico = servico;
    }

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
}