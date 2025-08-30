package com.barbearia.agendamentos.dto;

import com.barbearia.agendamentos.enums.UsuarioNivel;

public record RegisterDTO(String login, String senha, UsuarioNivel nivel) {
}
