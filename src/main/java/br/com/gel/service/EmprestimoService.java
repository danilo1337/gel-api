package br.com.gel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gel.interfaces.IService;
import br.com.gel.model.Emprestimo;
import br.com.gel.repository.EmprestimoRepository;

@Service
public class EmprestimoService  implements IService<Emprestimo, Long>{
   
	@Autowired
    private EmprestimoRepository repository;
    
	@Override
	public Emprestimo create(Emprestimo entity) {
		return repository.save(entity);
	}

	@Override
	public List<Emprestimo> read() {
		return repository.findAll();
	}

	@Override
	public Emprestimo read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Emprestimo %d", id)));
	}

	@Override
	public Page<Emprestimo> read(String nome, Pageable pageable) {
            return repository.findAll(pageable);
	}

	@Override
	public void update(Emprestimo entity) {
		  repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		 repository.deleteById(id);
	}

}
