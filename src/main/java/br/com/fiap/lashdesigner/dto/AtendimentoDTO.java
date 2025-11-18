package br.com.fiap.lashdesigner.dto;

import br.com.fiap.lashdesigner.domain.Atendimento;
import br.com.fiap.lashdesigner.domain.Cliente;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AtendimentoDTO {

    private Long id;
    private Long clienteId;
    private String nomeCliente;
    private LocalDate dataAtendimento;
    private String tipoServico;
    private BigDecimal valor;
    private LocalDate dataRetorno;
    private String statusPagamento;
    private String observacoes;
    private String mensagemLembrete;

    public AtendimentoDTO() {
    }

    public AtendimentoDTO(Long id, Long clienteId, String nomeCliente, LocalDate dataAtendimento,
                          String tipoServico, BigDecimal valor, LocalDate dataRetorno,
                          String statusPagamento, String observacoes, String mensagemLembrete) {
        this.id = id;
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.dataAtendimento = dataAtendimento;
        this.tipoServico = tipoServico;
        this.valor = valor;
        this.dataRetorno = dataRetorno;
        this.statusPagamento = statusPagamento;
        this.observacoes = observacoes;
        this.mensagemLembrete = mensagemLembrete;
    }

    public static AtendimentoDTO fromEntity(Atendimento atendimento) {
        if (atendimento == null) {
            return null;
        }
        Cliente c = atendimento.getCliente();
        Long cid = c != null ? c.getIdCliente() : null;
        String nome = c != null ? c.getNome() : null;
        return new AtendimentoDTO(
                atendimento.getIdAtendimento(),
                cid,
                nome,
                atendimento.getDataAtendimento(),
                atendimento.getTipoServico(),
                atendimento.getValor(),
                atendimento.getDataRetorno(),
                atendimento.getStatusPagamento(),
                atendimento.getObservacoes(),
                null
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public LocalDate getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        this.dataRetorno = dataRetorno;
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

    public String getMensagemLembrete() {
        return mensagemLembrete;
    }

    public void setMensagemLembrete(String mensagemLembrete) {
        this.mensagemLembrete = mensagemLembrete;
    }
}
