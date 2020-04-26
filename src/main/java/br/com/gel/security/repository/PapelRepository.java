package br.com.gel.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gel.security.model.Papel;

/**
 * Spring Data JPA repository for the {@link Papel} entity.
 */
public interface PapelRepository extends JpaRepository<Papel, String> {
}
