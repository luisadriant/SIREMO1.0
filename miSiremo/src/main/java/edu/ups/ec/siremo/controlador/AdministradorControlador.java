package edu.ups.ec.siremo.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import edu.ups.ec.siremo.dao.AdministradorDao;
import edu.ups.ec.siremo.modelo.Administrador;
import edu.ups.ec.siremo.modelo.Empresa;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.util.ErrorsController;

/**
 * Esta clase sirve para enlazar la vista del archivo xhtml con el controlador del administrador 
 * @author root
 */
@ManagedBean
@ViewScoped
public class AdministradorControlador {
	
	// Instanciamos la clase que controla los errores con su respectivo inject.
	ErrorsController error = new ErrorsController();
	@Inject
    private FacesContext facesContext;
	
	//instancia de la entidad de negocio Administrador
	private Administrador administrador;
	//variables necesarias
	private List<Administrador> administradores;
	//id de un administrador
	private int id;
	//lista para los generos
	private List<SelectItem> generos;
	//validar guardado;
	private boolean guardado;
	//validar usuario repetido;
	private boolean admrepetido;
	//rcontrase;a
	private String rcontrasenia;
	//contrase;as Diferentes
	private boolean passDiferentes;
	//1 empresa nueva
	private Empresa empresa;
	
	//instanciamos en objeto de acceso a datos para poder injectar los metodos crud correspondiente al administra 
	@Inject
	private AdministradorDao ADAO;

	
	//este metodo se inicia cada vez que se construye la clase 
		@PostConstruct
		public void init() {
			empresa=new Empresa();
			administrador=new Administrador();
			administrador.addEmpresa(new Empresa());
			loadAdministradores();
		}
	
	
	public Empresa getEmpresa() {
			return empresa;
		}


		public void setEmpresa(Empresa empresa) {
			this.empresa = empresa;
		}


	public List<SelectItem> getGeneros() {
		loadGeneros();
		return generos;
	}
	public void setGeneros(List<SelectItem> generos) {
		this.generos = generos;
	}
	public boolean isGuardado() {
		return guardado;
	}
	public void setGuardado(boolean guardado) {
		this.guardado = guardado;
	}
	public boolean isAdmrepetido() {
		return admrepetido;
	}
	public void setAdmrepetido(boolean admrepetido) {
		this.admrepetido = admrepetido;
	}
	public String getRcontrasenia() {
		return rcontrasenia;
	}
	public void setRcontrasenia(String rcontrasenia) {
		this.rcontrasenia = rcontrasenia;
	}
	public boolean isPassDiferentes() {
		return passDiferentes;
	}
	public void setPassDiferentes(boolean passDiferentes) {
		this.passDiferentes = passDiferentes;
	}
	public List<Administrador> getAdministradores() {
		return administradores;
	}

	public void setAdministradores(List<Administrador> administradores) {
		this.administradores = administradores;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		loadDatoseditar(id);
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
	
	//este metodo nos sirve para guardar un administrador 
	public String Guardar() {
		 try {		 
		 if(administrador.getContrasenia().equals(rcontrasenia)) {
			 passDiferentes=false;
			 guardado=ADAO.Guardar(administrador);
			 loadAdministradores();
			 if (guardado) {
				 administrador = new Administrador();
				 rcontrasenia="";
				 return"login";
			 }else {
				 admrepetido=true;
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
	public String Guardar2() {
		 try {		 
		 if(administrador.getContrasenia().equals(rcontrasenia)) {
			 passDiferentes=false;
			 guardado=ADAO.Guardar(administrador);
			 loadAdministradores();
			 if (guardado) {
				 administrador = new Administrador();
				 rcontrasenia="";
				 return"misEmpresas_face.xhtml";
			 }else {
				 admrepetido=true;
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
	//este metodo nos sirve para eliminar un Administrador y a la vez mostra en pantalla los administradores que sobran
	public String Eliminar(int id) {
		ADAO.Borrar(id);
		loadAdministradores();
		return null;
	}
	//este metodo nos sirve para cargar los datos de todos los administradores que existen en la BD
	private void loadAdministradores() {
		administradores=ADAO.listadoadministradores();
	}
	//este metodo nos sirve para cargar la pagina y editar los datos de administrador
	public String loadDatoseditar(int id) {
		administrador = ADAO.Leer(id);
		return "editarAdministrador";
	}
	//este metodo nos sirve para agregar una o mas empresas q pertenecen al administrador
	public String addEmpresa() {
		administrador.addEmpresa(new Empresa());
		return null;
	}
	private void loadGeneros() {
		generos=new ArrayList<SelectItem>();
		generos.add(new SelectItem("",""));
		generos.add(new SelectItem("Masculino","Masculino"));
		generos.add(new SelectItem("Femenino","Femenino"));

	}

}
