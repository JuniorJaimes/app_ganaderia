package com.ganaderia.application.port.out;

import com.ganaderia.application.dto.ReporteProduccionDTO;
import java.util.List;

public interface ReporteriaPort {
    List<ReporteProduccionDTO> sumarProduccionPorMesYAnio(int anio, int mes);
}
