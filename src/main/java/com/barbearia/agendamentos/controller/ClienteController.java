package com.barbearia.agendamentos.controller;

import com.barbearia.agendamentos.model.Cliente;
import com.barbearia.agendamentos.repository.ClienteRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Endpoint para listar todos os clientes. Apenas usuários com a role 'ADMIN' podem acessar.
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // Endpoint para criar um novo cliente. Apenas usuários com a role 'ADMIN' podem acessar.
    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody @Valid Cliente cliente) {
        // Verifica se já existe um cliente com o mesmo número de telefone
        if (clienteRepository.existsByTelefone(cliente.getTelefone())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null); // Retorna status 409 (CONFLICT) se o telefone já estiver cadastrado
        }

        // Salva o cliente no banco de dados
        Cliente clienteSalvo = clienteRepository.save(cliente);

        // Retorna o cliente salvo com o status 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }
}