package com.ganaderia.infrastructure.adapter.in.web;

import com.ganaderia.application.dto.RegistrarOrdenoDTO;
import com.ganaderia.application.usecase.RegistrarOrdenoUseCase;
import com.ganaderia.domain.model.IdVaca;
import com.ganaderia.domain.model.RegistroOrdeno;
import com.ganaderia.domain.repository.RegistroOrdenoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordenos")
public class ProduccionController {

    private final RegistrarOrdenoUseCase registrarOrdenoUseCase;
    private final RegistroOrdenoRepository ordenoRepository;

    public ProduccionController(RegistrarOrdenoUseCase registrarOrdenoUseCase,
                                 RegistroOrdenoRepository ordenoRepository) {
        this.registrarOrdenoUseCase = registrarOrdenoUseCase;
        this.ordenoRepository = ordenoRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registrar(@RequestBody RegistrarOrdenoDTO dto) {
        String id = registrarOrdenoUseCase.ejecutar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id, "mensaje", "Ordeño registrado exitosamente"));
    }

    @GetMapping("/vaca/{vacaId}")
    public ResponseEntity<List<Map<String, Object>>> listarPorVaca(@PathVariable String vacaId) {
        List<Map<String, Object>> registros = ordenoRepository.listarPorVaca(new IdVaca(vacaId)).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(registros);
    }

    private Map<String, Object> toResponse(RegistroOrdeno registro) {
        return Map.of(
                "id", registro.getId(),
                "vacaId", registro.getVacaId().value(),
                "fecha", registro.getFecha().toString(),
                "turno", registro.getTurno().name(),
                "litros", registro.getVolumen().litros()
        );
    }
}
