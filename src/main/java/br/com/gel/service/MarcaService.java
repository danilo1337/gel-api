package br.com.gel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.gel.interfaces.IService;
import br.com.gel.model.Marca;
import br.com.gel.repository.MarcaRepository;

@Service
public class MarcaService  implements IService<Marca, Long>{
   
	@Autowired
    private MarcaRepository repository;
    
	@Override
	public Marca create(Marca entity) {
		return repository.save(entity);
	}

	@Override
	public List<Marca> read() {
		return repository.findAll();
	}

	@Override
	public Marca read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Marca %d", id)));
	}

	@Override
	public Page<Marca> read(String nome, Pageable pageable) {
        if (StringUtils.hasText(nome)) {
            return repository.findByNomeContainingIgnoreCaseOrderByNome(nome, pageable);
        } else {
            return repository.findAll(pageable);
        }
	}

	@Override
	public void update(Marca entity) {
		  repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		 repository.deleteById(id);
	}

}
