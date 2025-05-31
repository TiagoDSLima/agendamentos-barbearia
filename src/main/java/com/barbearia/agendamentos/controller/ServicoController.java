package com.barbearia.agendamentos.controller;

import com.barbearia.agendamentos.model.Servico;
import com.barbearia.agendamentos.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping
    public List<Servico> listarServicos() {
        return servicoRepository.findAll();
    }
}
