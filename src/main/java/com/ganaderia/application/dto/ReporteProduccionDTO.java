package com.ganaderia.application.dto;

import java.math.BigDecimal;

public class ReporteProduccionDTO {
    private String vacaId;
    private String numeroArete;
    private BigDecimal totalLitros;

    public ReporteProduccionDTO(String vacaId, String numeroArete, BigDecimal totalLitros) {
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

    public BigDecimal getTotalLitros() {
        return totalLitros;
    }
}
