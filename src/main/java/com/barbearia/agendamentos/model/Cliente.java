package com.barbearia.agendamentos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank // Garante que o nome não seja nulo ou vazio
    @Size(min = 2, max = 100, message = "O nome do cliente deve ter entre 2 e 100 caracteres.")
    private String nome;

    @NotBlank // Garante que o telefone não seja nulo ou vazio
    @Pattern(regexp = "\\d{11}", message = "Telefone deve conter 11 dígitos") // Valida que o telefone tenha exatamente 11 dígitos
    private String telefone;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}