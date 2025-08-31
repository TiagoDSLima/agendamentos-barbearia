package com.barbearia.agendamentos.utils;

import com.barbearia.agendamentos.model.Agendamento;
import com.barbearia.agendamentos.model.Servico;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HorarioUtils {

    private HorarioUtils() {
    }

    //Gera horário de 9h às 20h
    public static List<LocalDateTime> gerarHorarios(LocalDate data) {
        List<LocalDateTime> horarios = new ArrayList<>();
        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fim = LocalTime.of(19, 45);

        LocalTime horarioAtual = inicio;
        while (!horarioAtual.isAfter(fim)) {
            horarios.add(LocalDateTime.of(data, horarioAtual));
            horarioAtual = horarioAtual.plusMinutes(15);
        }

        return horarios;
    }

    //verifica os horários anteriores e posteriores, por exemplo se tem um horário marcado às 10h para barba, um corte de cabelo (0) não pode ser marcado às 9h45, nem nenhum outro agendamento pode ser marcado antes de 10h30
    public static Set<LocalDateTime> gerarHorariosBloqueados(List<Agendamento> agendamentos, Servico servico) {
        Set<LocalDateTime> horariosABloquear = new HashSet<>();

        for(Agendamento agendamento: agendamentos){
            horariosABloquear.add(agendamento.getHorarioInicio());

            for(int i = 15; i < servico.getDuracao(); i += 15){
                horariosABloquear.add(agendamento.getHorarioInicio().minusMinutes(i));
            }

            for(int i = 15; i < agendamento.getServico().getDuracao(); i += 15){
                horariosABloquear.add(agendamento.getHorarioInicio().plusMinutes(i));
            }


        }

        return horariosABloquear;
    }
}
