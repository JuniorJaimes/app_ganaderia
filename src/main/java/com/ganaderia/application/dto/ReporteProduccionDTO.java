package com.ganaderia.application.dto;

public class ReporteProduccionDTO {
    private String vacaId;
    private String numeroArete;
    private Double totalLitros;

    public ReporteProduccionDTO(String vacaId, String numeroArete, Double totalLitros) {
        this.vacaId = vacaId;
        this.numeroArete = numeroArete;
        this.totalLitros = totalLitros;
    }

    public String getVacaId() {
        return vacaId;
    }

    public String getNumeroArete() {
        return numeroArete;
    }

    public Double getTotalLitros() {
        return totalLitros;
    }
}
