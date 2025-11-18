package br.com.fiap.lashdesigner.api;

import br.com.fiap.lashdesigner.domain.Atendimento;
import br.com.fiap.lashdesigner.domain.Cliente;
import br.com.fiap.lashdesigner.dto.AtendimentoDTO;
import br.com.fiap.lashdesigner.dto.FaturamentoDiaDTO;
import br.com.fiap.lashdesigner.dto.NotaFiscalDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Path("/atendimentos")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtendimentoResource {

    @Inject
    EntityManager em;

    private String gerarMensagemLembrete(String nomeCliente, LocalDate dataRetorno, String tipoServico) {
        if (dataRetorno == null) {
            return "Oi, " + nomeCliente + "! Aqui é do estúdio de cílios, obrigada pelo atendimento de '" + tipoServico + "'. Até a próxima!";
        }
        String data = dataRetorno.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "Oi, " + nomeCliente + "! Seu retorno do serviço '" + tipoServico + "' está agendado para " + data + ". Qualquer dúvida é só chamar!";
    }

    @GET
    public List<AtendimentoDTO> listarTodos() {
        List<Atendimento> lista = em.createQuery("SELECT a FROM Atendimento a ORDER BY a.dataAtendimento DESC", Atendimento.class)
                .getResultList();
        return lista.stream()
                .map(AtendimentoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Atendimento atendimento = em.find(Atendimento.class, id);
        if (atendimento == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        AtendimentoDTO dto = AtendimentoDTO.fromEntity(atendimento);
        return Response.ok(dto).build();
    }

    @POST
    @Transactional
    public Response criar(AtendimentoDTO dto) {
        Cliente cliente = em.find(Cliente.class, dto.getClienteId());
        if (cliente == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Cliente não encontrado").build();
        }
        Atendimento atendimento = new Atendimento();
        atendimento.setCliente(cliente);
        atendimento.setDataAtendimento(dto.getDataAtendimento());
        atendimento.setTipoServico(dto.getTipoServico());
        atendimento.setValor(dto.getValor());
        atendimento.setDataRetorno(dto.getDataRetorno());
        atendimento.setStatusPagamento(dto.getStatusPagamento());
        atendimento.setObservacoes(dto.getObservacoes());
        em.persist(atendimento);
        AtendimentoDTO resposta = AtendimentoDTO.fromEntity(atendimento);
        return Response.status(Response.Status.CREATED).entity(resposta).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, AtendimentoDTO dto) {
        Atendimento atendimento = em.find(Atendimento.class, id);
        if (atendimento == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (dto.getClienteId() != null) {
            Cliente cliente = em.find(Cliente.class, dto.getClienteId());
            if (cliente == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Cliente não encontrado").build();
            }
            atendimento.setCliente(cliente);
        }
        if (dto.getDataAtendimento() != null) {
            atendimento.setDataAtendimento(dto.getDataAtendimento());
        }
        if (dto.getTipoServico() != null) {
            atendimento.setTipoServico(dto.getTipoServico());
        }
        if (dto.getValor() != null) {
            atendimento.setValor(dto.getValor());
        }
        if (dto.getDataRetorno() != null) {
            atendimento.setDataRetorno(dto.getDataRetorno());
        }
        if (dto.getStatusPagamento() != null) {
            atendimento.setStatusPagamento(dto.getStatusPagamento());
        }
        if (dto.getObservacoes() != null) {
            atendimento.setObservacoes(dto.getObservacoes());
        }
        AtendimentoDTO resposta = AtendimentoDTO.fromEntity(atendimento);
        return Response.ok(resposta).build();
    }

    @POST
    @Path("/{id}/cobranca")
    @Transactional
    public Response cobrarEEmitirNota(@PathParam("id") Long id) {
        Atendimento atendimento = em.find(Atendimento.class, id);
        if (atendimento == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if ("PAGO".equalsIgnoreCase(atendimento.getStatusPagamento())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Atendimento já está marcado como PAGO").build();
        }
        atendimento.setStatusPagamento("PAGO");

        Cliente c = atendimento.getCliente();
        Long idCliente = c != null ? c.getIdCliente() : null;
        String nomeCliente = c != null ? c.getNome() : null;

        String dataEmissao = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String dataAt = atendimento.getDataAtendimento() != null
                ? atendimento.getDataAtendimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : null;

        NotaFiscalDTO nota = new NotaFiscalDTO(
                atendimento.getIdAtendimento(),
                dataEmissao,
                atendimento.getIdAtendimento(),
                idCliente,
                nomeCliente,
                atendimento.getTipoServico(),
                dataAt,
                atendimento.getValor(),
                atendimento.getStatusPagamento(),
                atendimento.getObservacoes()
        );

        return Response.ok(nota).build();
    }

    @GET
    @Path("/retornos")
    public Response listarRetornos(
            @QueryParam("de") String de,
            @QueryParam("ate") String ate
    ) {
        if (de == null || ate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetros 'de' e 'ate' são obrigatórios").build();
        }
        LocalDate dataDe;
        LocalDate dataAte;
        try {
            dataDe = LocalDate.parse(de);
            dataAte = LocalDate.parse(ate);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Formato de data inválido, use yyyy-MM-dd").build();
        }
        List<Atendimento> lista = em.createQuery(
                        "SELECT a FROM Atendimento a WHERE a.dataRetorno BETWEEN :de AND :ate ORDER BY a.dataRetorno",
                        Atendimento.class)
                .setParameter("de", dataDe)
                .setParameter("ate", dataAte)
                .getResultList();

        List<AtendimentoDTO> dtos = lista.stream().map(a -> {
            AtendimentoDTO dto = AtendimentoDTO.fromEntity(a);
            Cliente c = a.getCliente();
            String nome = c != null ? c.getNome() : "Cliente";
            String msg = gerarMensagemLembrete(nome, a.getDataRetorno(), a.getTipoServico());
            dto.setMensagemLembrete(msg);
            return dto;
        }).collect(Collectors.toList());

        return Response.ok(dtos).build();
    }

    @GET
    @Path("/faturamento")
    public Response faturamentoPorDia(
            @QueryParam("de") String de,
            @QueryParam("ate") String ate
    ) {
        if (de == null || ate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Parâmetros 'de' e 'ate' são obrigatórios").build();
        }
        LocalDate dataDe;
        LocalDate dataAte;
        try {
            dataDe = LocalDate.parse(de);
            dataAte = LocalDate.parse(ate);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Formato de data inválido, use yyyy-MM-dd").build();
        }

        List<Object[]> rows = em.createQuery(
                        "SELECT a.dataAtendimento, SUM(a.valor) " +
                                "FROM Atendimento a " +
                                "WHERE a.dataAtendimento BETWEEN :de AND :ate " +
                                "AND UPPER(a.statusPagamento) = 'PAGO' " +
                                "GROUP BY a.dataAtendimento " +
                                "ORDER BY a.dataAtendimento",
                        Object[].class)
                .setParameter("de", dataDe)
                .setParameter("ate", dataAte)
                .getResultList();

        List<FaturamentoDiaDTO> dtos = rows.stream()
                .map(r -> new FaturamentoDiaDTO(
                        (LocalDate) r[0],
                        r[1] != null ? (BigDecimal) r[1] : BigDecimal.ZERO
                ))
                .collect(Collectors.toList());

        return Response.ok(dtos).build();
    }

    @GET
    @Path("/pendentes")
    public List<AtendimentoDTO> listarPendentes() {
        List<Atendimento> lista = em.createQuery(
                        "SELECT a FROM Atendimento a " +
                                "WHERE UPPER(a.statusPagamento) = 'PENDENTE' " +
                                "ORDER BY a.dataAtendimento",
                        Atendimento.class)
                .getResultList();
        return lista.stream()
                .map(AtendimentoDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
