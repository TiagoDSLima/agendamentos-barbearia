package com.barbearia.agendamentos.controller;

import com.barbearia.agendamentos.model.Agendamento;
import com.barbearia.agendamentos.model.Cliente;
import com.barbearia.agendamentos.model.Servico;
import com.barbearia.agendamentos.repository.AgendamentoRepository;
import com.barbearia.agendamentos.repository.ClienteRepository;

import com.barbearia.agendamentos.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    // Retorna todos os agendamentos existentes
    @GetMapping
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    // Cria um novo agendamento
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Agendamento agendamento) {
        try {
            Cliente cliente = agendamento.getCliente();
            Optional<Cliente> clienteExistente = clienteRepository.findByTelefone(cliente.getTelefone());
            boolean agendamentoExistente = agendamentoRepository.existsByHorarioInicio(agendamento.getHorarioInicio());

            if(agendamentoExistente) throw new IllegalArgumentException("Horário já marcado!");

            if (clienteExistente.isPresent()) {
                agendamento.setCliente(clienteExistente.get());
            } else {
                cliente = clienteRepository.save(cliente);
                agendamento.setCliente(cliente);
            }

            Agendamento agendamentoNovo = agendamentoRepository.save(agendamento);

            return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoNovo);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", e.getMessage()));
        }
    }

    // Lista horários disponíveis de um determinado dia, levando em conta o serviço a ser feito
    @GetMapping("/horarios-disponiveis")
    public List<LocalDateTime> listarHorariosDisponiveis(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam("servico") Long idServico
    ) {
        if(data.getDayOfWeek() == DayOfWeek.SUNDAY){
            return new ArrayList<>();
        }

        Servico servico = servicoRepository.findById(idServico).get();

        List<Agendamento> agendamentos = agendamentoRepository.findByHorarioInicioBetween(data.atStartOfDay(), data.atTime(23, 59, 59));
        List<LocalDateTime> horariosDisponiveis = com.barbearia.agendamentos.utils.HorarioUtils.gerarHorarios(data);
        Set<LocalDateTime> horariosABloquear = com.barbearia.agendamentos.utils.HorarioUtils.gerarHorariosBloqueados(agendamentos, servico);

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