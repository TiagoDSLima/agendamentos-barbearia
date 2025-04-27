package com.barbearia.agendamentos.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioUtils {

    private HorarioUtils() {
    }

    //Gera horário de 9h às 20h caso seja dia de semana, de 8h às 17h caso seja sábado e retorna a lista vazia caso seja domingo
    public static List<LocalDateTime> gerarHorarios(LocalDate data) {
        List<LocalDateTime> horarios = new ArrayList<>();

        if(data.getDayOfWeek() == DayOfWeek.SATURDAY){
            return horarios;
        }

        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fim = LocalTime.of(20, 0);

        if (data.getDayOfWeek() == DayOfWeek.SATURDAY) {
            inicio = LocalTime.of(8, 0);
            fim = LocalTime.of(17, 0);
        }

        LocalTime horarioAtual = inicio;
        while (!horarioAtual.isAfter(fim)) {
            horarios.add(LocalDateTime.of(data, horarioAtual));
            horarioAtual = horarioAtual.plusMinutes(15);
        }

        return horarios;
    }
}
