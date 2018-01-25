package edu.ups.ec.siremo.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Esta clase es la entidad Administrador que contiene los getters y setters.
 * @author root
 */
@Entity
@DiscriminatorValue(value="Administrador")
public class Administrador extends Persona implements Serializable {

	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="emp_per_id", referencedColumnName="per_id")
	private List<Empresa> empresas;

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	public void addEmpresa(Empresa e) {
		if (empresas==null)
			empresas=new ArrayList<Empresa>();
		empresas.add(e);
	}
	
}
