package br.com.fiap.lashdesigner.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FaturamentoDiaDTO {

    private LocalDate dia;
    private BigDecimal totalDia;

    public FaturamentoDiaDTO() {
    }

    public FaturamentoDiaDTO(LocalDate dia, BigDecimal totalDia) {
        this.dia = dia;
        this.totalDia = totalDia;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public BigDecimal getTotalDia() {
        return totalDia;
    }

    public void setTotalDia(BigDecimal totalDia) {
        this.totalDia = totalDia;
    }
}
