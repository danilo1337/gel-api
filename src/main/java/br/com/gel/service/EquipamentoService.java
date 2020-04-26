package br.com.gel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.gel.interfaces.IService;
import br.com.gel.model.Equipamento;
import br.com.gel.repository.EquipamentoRepository;

@Service
public class EquipamentoService  implements IService<Equipamento, Long>{
   
	@Autowired
    private EquipamentoRepository repository;
    
	@Override
	public Equipamento create(Equipamento entity) {
		return repository.save(entity);
	}

	@Override
	public List<Equipamento> read() {
		return repository.findAll();
	}

	@Override
	public Equipamento read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Equipamento %d", id)));
	}

	@Override
	public Page<Equipamento> read(String serial, Pageable pageable) {
        if (StringUtils.hasText(serial)) {
            return repository.findBySerialContainingIgnoreCaseOrderBySerial(serial, pageable);
        } else {
            return repository.findAll(pageable);
        }
	}

	@Override
	public void update(Equipamento entity) {
		  repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		 repository.deleteById(id);
	}
	
}
