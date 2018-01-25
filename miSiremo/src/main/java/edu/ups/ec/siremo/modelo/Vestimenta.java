package edu.ups.ec.siremo.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Esta clase es la entidad Vestiementa que contiene los getters y setters.
 * @author root
 */
@Entity
@Table(name="sir_vestimenta")
public class Vestimenta implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ves_id")
	private int id;
	
	@Column(name="ves_descripcion")
	@NotNull
	private String descripcion;
	
	@Column(name="ves_color")
	@NotNull
	private String color;
	
	@Column(name="ves_talla")
	@NotNull
	private String talla;
	
	@Column(name="ves_genero")
	@NotNull
	private String genero;
	
	@Column(name="ves_precio")
	@NotNull
	@DecimalMin("0.00")
	private Double precio;
	
	@Column(name="ves_tipo")
	@NotNull
	private String tipo;
	
	@Column(name="ves_estilo")
	@NotNull
	private String estilo;
	
	@Column(name="ves_imagen")
	@NotNull
	private String imagen;
	
	@Transient
	private String raiting;
	
	@Transient
	private int likes;
	
	@Transient
	private int id_marca=0;
//	
//	@Transient
//	private int voto=0;
	
	
	
	@ManyToOne
	@JoinColumn(name="ves_mar_id", referencedColumnName="mar_id")
	private Marca marca;
	
	@ManyToOne
	@JoinColumn(name="ves_emp_id", referencedColumnName="emp_id")
	private Empresa empresa;
	
//	@OneToMany(cascade= {CascadeType.ALL}, fetch=FetchType.EAGER)
//	@JoinColumn(name="vot_ves_id", referencedColumnName="ves_id")
//	private List<Votos> votos;
	
//	@ManyToMany(mappedBy="vestimentas")
//	private List<Usuario> usuarios;
	
	
//	public int getVoto() {
//		return voto;
//	}
//
//	public void setVoto(int voto) {
//		this.voto = voto;
//	}
//
	public int getId_marca() {
		return id_marca;
	}

	public void setId_marca(int id_marca) {
		this.id_marca = id_marca;
	}

//	public List<Usuario> getUsuarios() {
//		return usuarios;
//	}
//
//	public void setUsuarios(List<Usuario> usuarios) {
//		this.usuarios = usuarios;
//	}

//	public List<Votos> getVotos() {
//		return votos;
//	}
//
//	public void setVotos(List<Votos> votos) {
//		this.votos = votos;
//	}

	
	
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getRaiting() {
		return raiting;
	}

	public void setRaiting(String raiting) {
		this.raiting = raiting;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	
	public void addMarca(Marca marca) {
		this.marca=marca;
	}
	
}