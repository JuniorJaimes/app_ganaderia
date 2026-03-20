package com.ganaderia.infrastructure.adapter.in.web;

import com.ganaderia.application.port.in.RegistrarAdquisicionTerneroCommand;
import com.ganaderia.application.port.in.RegistrarNacimientoCommand;
import com.ganaderia.application.usecase.RegistrarAdquisicionTerneroUseCase;
import com.ganaderia.application.usecase.RegistrarNacimientoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/terneros")
public class TerneroController {

    private final RegistrarNacimientoUseCase registrarNacimientoUseCase;
    private final RegistrarAdquisicionTerneroUseCase registrarAdquisicionUseCase;

    public TerneroController(RegistrarNacimientoUseCase registrarNacimientoUseCase,
                             RegistrarAdquisicionTerneroUseCase registrarAdquisicionUseCase) {
        this.registrarNacimientoUseCase = registrarNacimientoUseCase;
        this.registrarAdquisicionUseCase = registrarAdquisicionUseCase;
    }

    @PostMapping("/nacimiento")
    public ResponseEntity<String> registrarNacimiento(@RequestBody NacimientoRequest request) {
        RegistrarNacimientoCommand command = new RegistrarNacimientoCommand(
                request.idMadre(),
                request.fechaNacimiento(),
                request.sexo()
        );
        String idGenerado = registrarNacimientoUseCase.ejecutar(command);
        return ResponseEntity.ok(idGenerado);
    }

    @PostMapping("/adquisicion")
    public ResponseEntity<String> registrarAdquisicion(@RequestBody AdquisicionRequest request) {
        RegistrarAdquisicionTerneroCommand command = new RegistrarAdquisicionTerneroCommand(
                request.fechaNacimiento(),
                request.sexo()
        );
        String idGenerado = registrarAdquisicionUseCase.ejecutar(command);
        return ResponseEntity.ok(idGenerado);
    }

    public record NacimientoRequest(
            String idMadre,
            LocalDate fechaNacimiento,
            String sexo
    ) {}

    public record AdquisicionRequest(
            LocalDate fechaNacimiento,
            String sexo
    ) {}
}
