package com.barbearia.agendamentos.enums;

public enum UsuarioNivel {

    ADMIN("ADMIN");

    private String nivel;

    UsuarioNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }
}
