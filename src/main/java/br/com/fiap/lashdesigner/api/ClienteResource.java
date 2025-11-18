package br.com.fiap.lashdesigner.api;

import br.com.fiap.lashdesigner.domain.Cliente;
import br.com.fiap.lashdesigner.dto.ClienteDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Path("/clientes")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    EntityManager em;

    @GET
    public List<ClienteDTO> listarTodos() {
        List<Cliente> lista = em.createQuery("SELECT c FROM Cliente c ORDER BY c.nome", Cliente.class)
                .getResultList();
        return lista.stream()
                .map(ClienteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ClienteDTO.fromEntity(cliente)).build();
    }

    @POST
    @Transactional
    public Response criar(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        cliente.setDataCadastro(LocalDate.now());
        em.persist(cliente);
        return Response.status(Response.Status.CREATED).entity(ClienteDTO.fromEntity(cliente)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, ClienteDTO dto) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        cliente.setNome(dto.getNome());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        return Response.ok(ClienteDTO.fromEntity(cliente)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        em.remove(cliente);
        return Response.noContent().build();
    }
}
