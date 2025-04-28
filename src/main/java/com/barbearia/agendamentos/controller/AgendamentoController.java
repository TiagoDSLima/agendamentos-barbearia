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

    // Lista horários disponíveis de um determinado dia, levando em conta o serviço a ser feito
    @GetMapping("/horarios-disponiveis")
    public List<LocalDateTime> listarHorariosDisponiveis(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam("servico") int servico
    ) {
        List<Agendamento> agendamentos = agendamentoRepository.findByHorarioInicioBetween(data.atStartOfDay(), data.atTime(23, 59, 59));
        //Pegando todos os horários do dia (sendo ocupados ou não, apenas gerando os horários) através do método da classe HorarioUtils que faz a validação do dia da semana
        List<LocalDateTime> horariosDisponiveis = com.barbearia.agendamentos.utils.HorarioUtils.gerarHorarios(data);
        Set<LocalDateTime> horariosABloquear = new HashSet<>();

        //verifica os horários anteriores, por exemplo se tem um horário marcado às 10h, um corte de cabelo (0) não pode ser marcado às 9h45
        for(Agendamento agendamento: agendamentos){
            if(servico == 2 || servico == 3) {
                horariosABloquear.add(agendamento.getHorarioInicio());
            } else if(servico == 1) {
                horariosABloquear.add(agendamento.getHorarioInicio());
                horariosABloquear.add(agendamento.getHorarioInicio().minusMinutes(15));
            } else if(servico == 0 || servico == 4) {
                horariosABloquear.add(agendamento.getHorarioInicio());
                horariosABloquear.add(agendamento.getHorarioInicio().minusMinutes(15));
                horariosABloquear.add(agendamento.getHorarioInicio().minusMinutes(30));
            }
        }

        horariosDisponiveis.removeIf(horariosABloquear::contains);

        return horariosDisponiveis;
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