package com.ganaderia.application.dto;

import java.time.LocalDate;

public class ProyeccionPartoDTO {
    private String vacaId;
    private String numeroArete;
    private LocalDate fechaUltimaInseminacion;
    private LocalDate fechaProbableParto;
    private LocalDate fechaSugeridaSecado;

    public ProyeccionPartoDTO(String vacaId, String numeroArete, LocalDate fechaUltimaInseminacion, LocalDate fechaProbableParto, LocalDate fechaSugeridaSecado) {
        this.vacaId = vacaId;
        this.numeroArete = numeroArete;
        this.fechaUltimaInseminacion = fechaUltimaInseminacion;
        this.fechaProbableParto = fechaProbableParto;
        this.fechaSugeridaSecado = fechaSugeridaSecado;
    }

    public String getVacaId() { return vacaId; }
    public String getNumeroArete() { return numeroArete; }
    public LocalDate getFechaUltimaInseminacion() { return fechaUltimaInseminacion; }
    public LocalDate getFechaProbableParto() { return fechaProbableParto; }
    public LocalDate getFechaSugeridaSecado() { return fechaSugeridaSecado; }
}
