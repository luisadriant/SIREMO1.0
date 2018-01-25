package edu.ups.ec.siremo.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.persistence.JoinColumn;

/**
 * Esta clase es la entidad Usuario que contiene los getters y setters.
 * @author root
 */
@Entity
@DiscriminatorValue(value="Usuario")
public class Usuario extends Persona implements Serializable{

	@Column(name="per_estilo")
	@Pattern(regexp = "[^0-9]*", message = "no contiene numeros")
	private String estilo;

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	
	
	
	
}
