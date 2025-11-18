package br.com.fiap.lashdesigner.dto;

import br.com.fiap.lashdesigner.domain.Cliente;
import java.time.LocalDate;

public class ClienteDTO {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private LocalDate dataCadastro;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String telefone, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public static ClienteDTO fromEntity(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return new ClienteDTO(
                cliente.getIdCliente(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getDataCadastro()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
