package br.com.fiap.lashdesigner.dto;

import java.math.BigDecimal;

public class NotaFiscalDTO {

    private Long numeroNota;
    private String dataEmissao;
    private Long idAtendimento;
    private Long idCliente;
    private String cliente;
    private String servico;
    private String dataAtendimento;
    private BigDecimal valor;
    private String statusPagamento;
    private String observacoes;

    public NotaFiscalDTO() {
    }

    public NotaFiscalDTO(Long numeroNota, String dataEmissao, Long idAtendimento, Long idCliente,
                         String cliente, String servico, String dataAtendimento,
                         BigDecimal valor, String statusPagamento, String observacoes) {
        this.numeroNota = numeroNota;
        this.dataEmissao = dataEmissao;
        this.idAtendimento = idAtendimento;
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.servico = servico;
        this.dataAtendimento = dataAtendimento;
        this.valor = valor;
        this.statusPagamento = statusPagamento;
        this.observacoes = observacoes;
    }

    public Long getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(Long numeroNota) {
        this.numeroNota = numeroNota;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Long getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(Long idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(String dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
