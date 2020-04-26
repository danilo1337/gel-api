package br.com.gel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.gel.interfaces.IService;
import br.com.gel.model.Regiao;
import br.com.gel.repository.RegiaoRepository;

@Service
public class RegiaoService  implements IService<Regiao, Long>{
   
	@Autowired
    private RegiaoRepository repository;
    
	@Override
	public Regiao create(Regiao entity) {
		return repository.save(entity);
	}

	@Override
	public List<Regiao> read() {
		return repository.findAll();
	}

	@Override
	public Regiao read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Regiao %d", id)));
	}

	@Override
	public Page<Regiao> read(String nome, Pageable pageable) {
        if (StringUtils.hasText(nome)) {
            return repository.findByNomeContainingIgnoreCaseOrderByNome(nome, pageable);
        } else {
            return repository.findAll(pageable);
        }
	}

	@Override
	public void update(Regiao entity) {
		  repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		 repository.deleteById(id);
	}

}
