package edu.ups.ec.siremo.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

/**
 * Esta clase es la entidad Persona que contiene los getters y setters.
 * @author root
 */
@Entity
@Table(name="sir_persona", uniqueConstraints = @UniqueConstraint(columnNames = "per_nombreusuario"))
public class Persona implements Serializable{
	
	@Id
	@Column(name="per_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) //para generar autoincrement de ID desde 1.
	private int id;
	
	@Column(name="per_nombres")
	@NotNull
	@Pattern(regexp = "[^0-9]*", message = "no contiene numeros")
	private String nombres;
	
	@Column(name="per_apellidos")
	@NotNull
	@Pattern(regexp = "[^0-9]*", message = "no contiene numeros")
	private String apellidos;
	
	@Column(name="per_nombreusuario")
	@NotNull
	private String nombreusuario;
	
    @Size(min = 7, max = 15)
    @Digits(fraction = 0, integer = 15)
	@Column(name="per_telefono", length=15)
	private String Telefono;
	
	@Column(name="per_email")
	@NotNull
	@Email
	private String email;
	
	@Column(name="per_genero")
	@NotNull
	private String genero;
	
	@Column(name="per_contrasenia")
	@NotNull
	private String contrasenia;
	
	@Temporal(TemporalType.DATE)
	@Column(name="per_fechanacimiento")
	@NotNull
	private Date fechanacimiento;
	
	
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public Date getFechanacimiento() {
		return fechanacimiento;
	}
	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNombreusuario() {
		return nombreusuario;
	}
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	
}
