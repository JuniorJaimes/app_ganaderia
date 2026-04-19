package com.ganaderia.infrastructure.adapter.in.web;

import com.ganaderia.application.dto.ProyeccionPartoDTO;
import com.ganaderia.application.dto.ReporteProduccionDTO;
import com.ganaderia.application.usecase.GenerarProyeccionPartosUseCase;
import com.ganaderia.application.usecase.GenerarReporteProduccionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final GenerarReporteProduccionUseCase generarReporteProduccionUseCase;
    private final GenerarProyeccionPartosUseCase generarProyeccionPartosUseCase;

    public ReporteController(GenerarReporteProduccionUseCase generarReporteProduccionUseCase,
                             GenerarProyeccionPartosUseCase generarProyeccionPartosUseCase) {
        this.generarReporteProduccionUseCase = generarReporteProduccionUseCase;
        this.generarProyeccionPartosUseCase = generarProyeccionPartosUseCase;
    }

    @GetMapping("/produccion")
    public ResponseEntity<List<ReporteProduccionDTO>> obtenerProduccionMensual(
            @RequestParam int anio,
            @RequestParam int mes) {
        List<ReporteProduccionDTO> reporte = generarReporteProduccionUseCase.ejecutar(anio, mes);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/proyecciones-parto")
    public ResponseEntity<List<ProyeccionPartoDTO>> obtenerProyeccionesPartos() {
        List<ProyeccionPartoDTO> proyecciones = generarProyeccionPartosUseCase.ejecutar();
        return ResponseEntity.ok(proyecciones);
    }
}
