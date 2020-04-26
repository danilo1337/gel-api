package br.com.gel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gel.interfaces.IService;
import br.com.gel.model.Checklist;
import br.com.gel.repository.ChecklistRepository;

@Service
public class ChecklistService implements IService<Checklist, Long> {

	@Autowired
	private ChecklistRepository repository;

	@Override
	public Checklist create(Checklist entity) {
		return repository.save(entity);
	}

	@Override
	public List<Checklist> read() {
		return repository.findAll();
	}

	@Override
	public Checklist read(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Checklist %d", id)));
	}

	@Override
	public Page<Checklist> read(String nome, Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public void update(Checklist entity) {
		repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
