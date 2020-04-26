package br.com.gel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.gel.interfaces.IService;
import br.com.gel.model.Tipo;
import br.com.gel.repository.TipoRepository;

@Service
public class TipoService  implements IService<Tipo, Long>{
   
	@Autowired
    private TipoRepository repository;
    
	@Override
	public Tipo create(Tipo entity) {
		return repository.save(entity);
	}

	@Override
	public List<Tipo> read() {
		return repository.findAll();
	}

	@Override
	public Tipo read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tipo %d", id)));
	}

	@Override
	public Page<Tipo> read(String nome, Pageable pageable) {
        if (StringUtils.hasText(nome)) {
            return repository.findByNomeContainingIgnoreCaseOrderByNome(nome, pageable);
        } else {
            return repository.findAll(pageable);
        }
	}

	@Override
	public void update(Tipo entity) {
		  repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		 repository.deleteById(id);
	}

}
