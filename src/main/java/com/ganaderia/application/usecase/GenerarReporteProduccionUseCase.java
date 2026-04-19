package com.ganaderia.application.usecase;

import com.ganaderia.application.dto.ReporteProduccionDTO;
import com.ganaderia.application.port.out.ReporteriaPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerarReporteProduccionUseCase {

    private final ReporteriaPort reporteriaPort;

    public GenerarReporteProduccionUseCase(ReporteriaPort reporteriaPort) {
        this.reporteriaPort = reporteriaPort;
    }

    public List<ReporteProduccionDTO> ejecutar(int anio, int mes) {
        return reporteriaPort.sumarProduccionPorMesYAnio(anio, mes);
    }
}
