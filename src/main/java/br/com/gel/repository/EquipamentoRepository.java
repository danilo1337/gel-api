package br.com.gel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.gel.interfaces.IRepository;
import br.com.gel.model.Equipamento;

public interface EquipamentoRepository extends IRepository<Equipamento, Long>{
	Page<Equipamento> findBySerialContainingIgnoreCaseOrderBySerial(String serial, Pageable pageable);
}
