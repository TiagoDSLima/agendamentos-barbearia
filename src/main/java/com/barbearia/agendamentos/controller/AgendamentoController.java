package com.barbearia.agendamentos.controller;

import com.barbearia.agendamentos.model.Agendamento;
import com.barbearia.agendamentos.model.Cliente;
import com.barbearia.agendamentos.repository.AgendamentoRepository;
import com.barbearia.agendamentos.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Retorna todos os agendamentos existentes
    @GetMapping
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    // Cria um novo agendamento
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Agendamento agendamento) {
        try {
            boolean ocupado = true;

            if(agendamento.getServico() == 2 || agendamento.getServico() == 3) {
                ocupado = agendamentoRepository.existsByHorarioInicio(agendamento.getHorarioInicio());
            } else if(agendamento.getServico() == 1) {
                ocupado = agendamentoRepository.existsByHorarioInicio(agendamento.getHorarioInicio()) || agendamentoRepository.existsByHorarioInicio(agendamento.getHorarioInicio().plusMinutes(15));
            } else if(agendamento.getServico() == 0 || agendamento.getServico() == 4) {
                ocupado = agendamentoRepository.existsByHorarioInicio(agendamento.getHorarioInicio()) || agendamentoRepository.existsByHorarioInicio(agendamento.getHorarioInicio().plusMinutes(15)) || agendamentoRepository.existsByHorarioInicio(agendamento.getHorarioInicio().plusMinutes(30));
            }

            if (ocupado) throw new RuntimeException("Horário já agendado.");

            Cliente cliente = agendamento.getCliente();
            Optional<Cliente> clienteExistente = clienteRepository.findByTelefone(cliente.getTelefone());

            if (clienteExistente.isPresent()) {
                agendamento.setCliente(clienteExistente.get());
            } else {
                cliente = clienteRepository.save(cliente);
                agendamento.setCliente(cliente);
            }

            List<Agendamento> agendamentosCriados = new ArrayList<>();

            if(agendamento.getServico() == 2 || agendamento.getServico() == 3) {
                Agendamento criado = new Agendamento();
                criado = agendamentoRepository.save(agendamento);
                agendamentosCriados.add(criado);
            } else if(agendamento.getServico() == 1) {
                for (int i = 0; i < 2; i++) {
                    Agendamento criado = new Agendamento();
                    Agendamento novoAgendamento = new Agendamento();
                    novoAgendamento.setHorarioInicio(agendamento.getHorarioInicio());
                    novoAgendamento.setCliente(agendamento.getCliente());
                    novoAgendamento.setServico(agendamento.getServico());

                    criado = agendamentoRepository.save(novoAgendamento);
                    agendamentosCriados.add(criado);

                    agendamento.setHorarioInicio(agendamento.getHorarioInicio().plusMinutes(15));
                }
            } else if(agendamento.getServico() == 0 || agendamento.getServico() == 4) {
                for (int i = 0; i < 3; i++) {
                    Agendamento criado = new Agendamento();
                    Agendamento novoAgendamento = new Agendamento();
                    novoAgendamento.setHorarioInicio(agendamento.getHorarioInicio());
                    novoAgendamento.setCliente(agendamento.getCliente());
                    novoAgendamento.setServico(agendamento.getServico());

                    criado = agendamentoRepository.save(novoAgendamento);
                    agendamentosCriados.add(criado);

                    agendamento.setHorarioInicio(agendamento.getHorarioInicio().plusMinutes(15));
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(agendamentosCriados);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", e.getMessage()));
        }
    }

    // Lista horários disponíveis de um determinado dia
    @GetMapping("/horarios-disponiveis")
    public List<Agendamento> listarHorariosDisponiveis(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        List<Agendamento> agendamentos = agendamentoRepository.findByHorarioInicioBetween(
                data.atStartOfDay(), data.atTime(23, 59, 59)
        );

        return agendamentos;
    }

    // Lista agendamentos de um dia específico
    @GetMapping("/agendamentos-dia")
    public List<Agendamento> listarAgendamentosPorData(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        LocalDateTime inicioDoDia = data.atStartOfDay();
        LocalDateTime fimDoDia = data.atTime(23, 59, 59);

        return agendamentoRepository.findByHorarioInicioBetween(inicioDoDia, fimDoDia);
    }

    // Exclui um agendamento com base no ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        if (!agendamentoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Agendamento não encontrado."));
        }

        agendamentoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}