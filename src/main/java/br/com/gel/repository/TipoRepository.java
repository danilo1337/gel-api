package br.com.gel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.gel.interfaces.IRepository;
import br.com.gel.model.Tipo;

public interface TipoRepository extends IRepository<Tipo, Long>{
	Page<Tipo> findByNomeContainingIgnoreCaseOrderByNome(String nome, Pageable pageable);
}
