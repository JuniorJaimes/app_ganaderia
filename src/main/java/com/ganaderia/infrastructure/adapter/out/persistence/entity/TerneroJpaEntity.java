package com.ganaderia.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "terneros")
public class TerneroJpaEntity {

    @Id
    private String id;
    private String idMadre;
    private LocalDate fechaNacimiento;
    private String sexo;
    private String tipoIngreso;

    protected TerneroJpaEntity() {
    }

    public TerneroJpaEntity(String id, String idMadre, LocalDate fechaNacimiento, String sexo, String tipoIngreso) {
        this.id = id;
        this.idMadre = idMadre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.tipoIngreso = tipoIngreso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMadre() {
        return idMadre;
    }

    public void setIdMadre(String idMadre) {
        this.idMadre = idMadre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(String tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }
}
