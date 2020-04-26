package br.com.gel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.gel.interfaces.IRepository;
import br.com.gel.model.Funcionario;

public interface FuncionarioRepository extends IRepository<Funcionario, Long>{
	Page<Funcionario> findByNomeContainingIgnoreCaseOrderByNome(String nome, Pageable pageable);
	Page<Funcionario> findByFuncaoContainingIgnoreCaseOrderByFuncao(String funcao, Pageable pageable);
	Funcionario findByCpf(String cpf);
}
