package edu.ups.ec.siremo.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Esta clase es la entidad Votos que contiene los getters y setters.
 * @author root
 */
@Entity
@Table(name="sir_votos")
public class Votos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vot_id")
	private int id;
	
	@NotNull
	@Column(name="vot_voto")
	private int voto;
	
	@ManyToOne
	@JoinColumn(name="vot_per_id", referencedColumnName="per_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name="vot_vest_id", referencedColumnName="ves_id")
	private Vestimenta vestimenta;

	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Vestimenta getVestimenta() {
		return vestimenta;
	}

	public void setVestimenta(Vestimenta vestimenta) {
		this.vestimenta = vestimenta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}
	
	
	
}