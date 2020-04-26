package br.com.gel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.gel.interfaces.IRepository;
import br.com.gel.model.Regiao;

public interface RegiaoRepository extends IRepository<Regiao, Long>{
	Page<Regiao> findByNomeContainingIgnoreCaseOrderByNome(String nome, Pageable pageable);
}
