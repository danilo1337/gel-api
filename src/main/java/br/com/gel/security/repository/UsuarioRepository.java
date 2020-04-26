package br.com.gel.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gel.security.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
 
   @EntityGraph(attributePaths = "papeis")
   Optional<Usuario> findOneWithPapelByCpf(String cpf);

   @EntityGraph(attributePaths = "papeis")
   Optional<Usuario> findOneWithPapelByEmailIgnoreCase(String email);
}
