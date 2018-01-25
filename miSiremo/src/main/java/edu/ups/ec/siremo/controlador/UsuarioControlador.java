package edu.ups.ec.siremo.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.util.ErrorsController;

/**
 * Esta clase sirve para enlazar la vista del archivo xhtml con el controlador del usuario 
 * @author root
 *
 */
@ManagedBean
@SessionScoped
public class UsuarioControlador {
	
	// Instanciamos la clase que controla los errores con su respectivo inject.
	ErrorsController error = new ErrorsController();
	@Inject
	private FacesContext facesContext;
	//lista para los generos
	private List<SelectItem> generos;
	//instancia de la entidad de negocio Usuario
	private Usuario usuario;
	//variables necesarias
	private List<Usuario> usuarios;
	//id de un usuario
	private int id;
	//validar guardado;
	private boolean guadado;
	//validar usuario repetido;
	private boolean usrrepetido;
	//rcontrase;a
	private String rcontrasenia;
	//contrase;as Diferentes
	private boolean passDiferentes;
	
	
	//instanciamos en objeto de acceso a datos para poder injectar los metodos crud correspondiente al Usuario
	@Inject
	private UsuarioDao usuarioDAO;
	
	//este metodo se inicia cada vez que se construye la clase
		@PostConstruct
		public void init() {
			usuario=new Usuario();
			loadUsuarios();
		}
		
	
		
	public boolean isUsrrepetido() {
			return usrrepetido;
	}
	public void setUsrrepetido(boolean usrrepetido) {
		this.usrrepetido = usrrepetido;
	}
	public boolean isPassDiferentes() {
		return passDiferentes;
	}
	public void setPassDiferentes(boolean passDiferentes) {
		this.passDiferentes = passDiferentes;
	}
	public String getRcontrasenia() {
		return rcontrasenia;
	}
	public void setRcontrasenia(String rcontrasenia) {
		this.rcontrasenia = rcontrasenia;
	}
	public boolean isGuadado() {
		return guadado;
	}
	public void setGuadado(boolean guadado) {
		this.guadado = guadado;
	}
	public List<SelectItem> getGeneros() {
		loadGeneros();
		return generos;
	}
	public void setGeneros(List<SelectItem> generos) {
		this.generos = generos;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
		loadDatoseditar(id);
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	//este metodo nos sirve para guardar un Usuario 
	public String Guardar() {
		try { 
			
		 if(usuario.getContrasenia().equals(rcontrasenia)) {
			 passDiferentes=false;
			 guadado=usuarioDAO.Guardar(usuario);
			 loadUsuarios();
			 if (guadado) {
				 usuario = new Usuario();
				 rcontrasenia="";
				 return"login";
			 }else {
				 usrrepetido=true;
			 }
		 }else
			 passDiferentes=true;
		 

		}catch (Exception e) {
			String errorMessage = error.getRootErrorMessage(e);
		    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
		    facesContext.addMessage(null, m);
		}
	return "";
	}
	//este metodo nos sirve para eliminar un Usuario y a la vez mostra en pantalla los usuarios que sobran 
	public String Eliminar(int id) {
		usuarioDAO.Borrar(id);
		loadUsuarios();
		return "listaUsuario";
	}
	//este metodo nos sirve para cargar los datos de todos los Usuarios que existen en la BD
	private void loadUsuarios() {
		usuarios=usuarioDAO.listadousuarios();
	}
	//este metodo nos sirve para cargar la pagina y editar los datos de Usuario
	public String loadDatoseditar(int id) {
		usuario = usuarioDAO.Leer(id);
		return "editarUsuario";
	}
	
	private void loadGeneros() {
		generos=new ArrayList<SelectItem>();
		generos.add(new SelectItem("",""));
		generos.add(new SelectItem("Masculino","Masculino"));
		generos.add(new SelectItem("Femenino","Femenino"));

	}
}
