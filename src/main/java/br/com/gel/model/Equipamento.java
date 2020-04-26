package br.com.gel.model;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Equipamento implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String serial;
	private String nPatrimonio;
	private String imei1;
	private String imei2;
	private String modelo;
	private String iccid;
	private Boolean disponivel;
	
	@ManyToOne
	private Tipo tipo;
	@ManyToOne
	private Marca marca;
	
	
}
