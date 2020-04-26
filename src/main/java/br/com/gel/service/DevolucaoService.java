package br.com.gel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gel.interfaces.IService;
import br.com.gel.model.Devolucao;
import br.com.gel.repository.DevolucaoRepository;

@Service
public class DevolucaoService  implements IService<Devolucao, Long>{
   
	@Autowired
    private DevolucaoRepository repository;
    
	@Override
	public Devolucao create(Devolucao entity) {
		return repository.save(entity);
	}

	@Override
	public List<Devolucao> read() {
		return repository.findAll();
	}

	@Override
	public Devolucao read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Devolucao %d", id)));
	}

	@Override
	public Page<Devolucao> read(String nome, Pageable pageable) {
            return repository.findAll(pageable);
	}

	@Override
	public void update(Devolucao entity) {
		  repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		 repository.deleteById(id);
	}

}
