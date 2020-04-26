package br.com.gel.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.Data;

@Data
@Entity
public class Emprestimo implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date data;
	private String motivo;
	private String observacao;
	
	@ManyToOne
	private Checklist checklist;
	
	@ManyToOne
	private Equipamento equipamento;
	
	@ManyToOne
	private Funcionario funcionario; 

}
