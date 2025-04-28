package com.apifecha.apifecha.application.service;

import com.apifecha.apifecha.domain.model.Festivo;
import com.apifecha.apifecha.domain.repository.FestivoRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class FestivoService {

    private final FestivoRepository festivoRepository;

    public FestivoService(FestivoRepository festivoRepository) {
        this.festivoRepository = festivoRepository;
    }

    public Optional<Festivo> obtenerFestivo(LocalDate fecha) {
        List<Festivo> festivos = festivoRepository.findAll();
        int year = fecha.getYear();

        // 1) Calculamos la fecha de Domingo de Pascua
        LocalDate pascua = calcularDomingoPascua(year);

        for (Festivo festivo : festivos) {
            int tipoId = festivo.getTipo().getId();
            LocalDate observed;

            switch (tipoId) {
                case 1:
                    // Fijo, no se mueve
                    if (festivo.getDia() == fecha.getDayOfMonth() &&
                        festivo.getMes() == fecha.getMonthValue()) {
                        return Optional.of(festivo);
                    }
                    break;

                case 2:
                    // Fijo, pero se traslada al siguiente lunes
                    LocalDate fijo = LocalDate.of(year,
                                            festivo.getMes(),
                                            festivo.getDia());
                    observed = fijo.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
                    if (observed.equals(fecha)) {
                        return Optional.of(festivo);
                    }
                    break;

                case 3:
                    // Basado en Pascua, sin mover
                    LocalDate basado = pascua.plusDays(festivo.getDiasPascua());
                    if (basado.equals(fecha)) {
                        return Optional.of(festivo);
                    }
                    break;

                case 4:
                    // Basado en Pascua, trasladado al lunes
                    LocalDate base = pascua.plusDays(festivo.getDiasPascua());
                    observed = base.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
                    if (observed.equals(fecha)) {
                        return Optional.of(festivo);
                    }
                    break;

                default:
                    // otros tipos (si llegase a haber)
                    break;
            }
        }

        return Optional.empty();
    }

    public boolean esFestivo(LocalDate fecha) {
        return obtenerFestivo(fecha).isPresent();
    }

    /**
     * Calcula el Domingo de Pascua de acuerdo a la fórmula:
     *   - a = año mod 19
     *   - b = año mod 4
     *   - c = año mod 7
     *   - d = (19a + 24) mod 30
     *   - e = (2b + 4c + 6d + 5) mod 7
     *   - díasDespués = d + e
     *   - Domingo de Ramos = 15 de marzo + díasDespués
     *   - Domingo de Pascua = Domingo de Ramos + 7 días
     */
    private LocalDate calcularDomingoPascua(int year) {
        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int d = (19 * a + 24) % 30;
        int e = (2 * b + 4 * c + 6 * d + 5) % 7;
        int diasDespues = d + e;

        LocalDate domingoRamos = LocalDate.of(year, 3, 15)
                                         .plusDays(diasDespues);
        return domingoRamos.plusDays(7);
    }
}
