package com.ganaderia.application.usecase;

import com.ganaderia.application.dto.ProyeccionPartoDTO;
import com.ganaderia.domain.model.Vaca;
import com.ganaderia.domain.model.enums.EstadoReproductivo;
import com.ganaderia.domain.repository.VacaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenerarProyeccionPartosUseCase {

    private final VacaRepository vacaRepository;

    public GenerarProyeccionPartosUseCase(VacaRepository vacaRepository) {
        this.vacaRepository = vacaRepository;
    }

    public List<ProyeccionPartoDTO> ejecutar() {
        List<Vaca> vacasCargadas = vacaRepository.listarVacasPorEstadoReproductivo(EstadoReproductivo.CARGADA);

        return vacasCargadas.stream()
                .filter(v -> v.getFechaUltimaInseminacion() != null)
                .map(v -> {
                    LocalDate fechaProbableParto = v.getFechaUltimaInseminacion().plusDays(Vaca.DIAS_GESTACION);
                    LocalDate fechaSugeridaSecado = fechaProbableParto.minusMonths(2); // Usualmente se secan 2 meses
                                                                                       // antes del parto
                    return new ProyeccionPartoDTO(
                            v.getId().value(),
                            v.getNumeroArete(),
                            v.getFechaUltimaInseminacion(),
                            fechaProbableParto,
                            fechaSugeridaSecado);
                })
                .sorted(Comparator.comparing(ProyeccionPartoDTO::getFechaProbableParto))
                .collect(Collectors.toList());
    }
}
