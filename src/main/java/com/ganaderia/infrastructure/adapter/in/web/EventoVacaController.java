package com.ganaderia.infrastructure.adapter.in.web;

import com.ganaderia.application.dto.RegistrarPartoDTO;
import com.ganaderia.application.dto.SecarVacaDTO;
import com.ganaderia.application.usecase.RegistrarPartoUseCase;
import com.ganaderia.application.usecase.SecarVacaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vacas")
public class EventoVacaController {

    private final RegistrarPartoUseCase registrarPartoUseCase;
    private final SecarVacaUseCase secarVacaUseCase;

    public EventoVacaController(RegistrarPartoUseCase registrarPartoUseCase, SecarVacaUseCase secarVacaUseCase) {
        this.registrarPartoUseCase = registrarPartoUseCase;
        this.secarVacaUseCase = secarVacaUseCase;
    }

    @PostMapping("/{id}/parto")
    public ResponseEntity<Map<String, String>> registrarParto(@PathVariable String id, @RequestBody RegistrarPartoDTO dto) {
        // Asegurar que el vacaId del DTO coincida con el path
        RegistrarPartoDTO comandoFinal = new RegistrarPartoDTO(id, dto.fechaParto());
        String idTernero = registrarPartoUseCase.ejecutar(comandoFinal);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("idTernero", idTernero, "mensaje", "Parto registrado exitosamente")
        );
    }

    @PostMapping("/{id}/secar")
    public ResponseEntity<Map<String, String>> secar(@PathVariable String id, @RequestBody SecarVacaDTO dto) {
        SecarVacaDTO comandoFinal = new SecarVacaDTO(id, dto.fechaSecado());
        secarVacaUseCase.ejecutar(comandoFinal);
        return ResponseEntity.ok(Map.of("mensaje", "Vaca secada exitosamente"));
    }
}
