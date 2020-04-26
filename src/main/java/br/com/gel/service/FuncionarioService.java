package br.com.gel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.gel.interfaces.IService;
import br.com.gel.model.Funcionario;
import br.com.gel.repository.FuncionarioRepository;

@Service
public class FuncionarioService implements IService<Funcionario, Long> {

	@Autowired
	private FuncionarioRepository repository;

	@Override
	public Funcionario create(Funcionario entity) {
		return repository.save(entity);
	}

	@Override
	public List<Funcionario> read() {
		return repository.findAll();
	}

	@Override
	public Funcionario read(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Funcionario %d", id)));
	}

	@Override
	public Page<Funcionario> read(String nome, Pageable pageable) {
		if (StringUtils.hasText(nome)) {
			return repository.findByNomeContainingIgnoreCaseOrderByNome(nome, pageable);
		} else {
			return repository.findAll(pageable);
		}
	}

	@Override
	public void update(Funcionario entity) {
		repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Funcionario buscarPorCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
}
