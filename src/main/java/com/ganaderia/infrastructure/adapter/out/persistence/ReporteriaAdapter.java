package com.ganaderia.infrastructure.adapter.out.persistence;

import com.ganaderia.application.dto.ReporteProduccionDTO;
import com.ganaderia.application.port.out.ReporteriaPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReporteriaAdapter implements ReporteriaPort {

    private final SpringDataRegistroOrdenoRepository springDataRegistroOrdenoRepository;

    public ReporteriaAdapter(SpringDataRegistroOrdenoRepository springDataRegistroOrdenoRepository) {
        this.springDataRegistroOrdenoRepository = springDataRegistroOrdenoRepository;
    }

    @Override
    public List<ReporteProduccionDTO> sumarProduccionPorMesYAnio(int anio, int mes) {
        return springDataRegistroOrdenoRepository.sumarProduccionPorMesYAnio(anio, mes);
    }
}
