package br.com.gel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.gel.interfaces.IRepository;
import br.com.gel.model.Marca;

public interface MarcaRepository extends IRepository<Marca, Long>{
	Page<Marca> findByNomeContainingIgnoreCaseOrderByNome(String nome, Pageable pageable);
}
