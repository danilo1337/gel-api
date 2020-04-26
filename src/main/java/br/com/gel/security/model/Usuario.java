package br.com.gel.security.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "USUARIO")
public class Usuario {

    @JsonIgnore
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "DataCadastro", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    @Column(name = "CPF", length = 50, unique = true, updatable = false)
    @NotNull
    @Size(min = 4, max = 50)
    private String cpf;

    @JsonIgnore
    @Column(name = "SENHA", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String senha;

    @Column(name = "NOME", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String nome;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @JsonIgnore
    @Column(name = "ATIVADO", nullable = false)
    @NotNull
    private boolean ativado = false;

    @ManyToMany
    @JoinTable(name = "USUARIO_PAPEL", joinColumns = {
            @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
                    @JoinColumn(name = "PAPEL_NOME", referencedColumnName = "NOME") })
    @BatchSize(size = 20)
    private Set<Papel> papeis = new HashSet<>();

    @PrePersist
    private void prePersist() {
        this.dataCadastro = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario user = (Usuario) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
