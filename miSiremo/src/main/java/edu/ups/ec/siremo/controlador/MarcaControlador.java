package edu.ups.ec.siremo.controlador;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import edu.ups.ec.siremo.dao.MarcaDao;
import edu.ups.ec.siremo.modelo.Marca;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.util.ErrorsController;

/**
 * Esta clase sirve para enlazar la vista del archivo xhtml con el controlador de la Marca
 * @author root
 *
 */
@ManagedBean
@ViewScoped
public class MarcaControlador {

	// Instanciamos la clase que controla los errores con su respectivo inject.
	ErrorsController error = new ErrorsController();
	@Inject
	private FacesContext facesContext;	
	
	//instancia de la entidad de negocio Marca
	private Marca marca;
	//variables necesarias
	private List<Marca> marcas;
	private int id;
	
	//instanciamos en objeto de acceso a datos para poder injectar los metodos crud correspondiente al Marca
	@Inject
	private MarcaDao MDAO;

	
	//este metodo se inicia cada vez que se construye la clase
		@PostConstruct
		public void init() {
			marca=new Marca();
			loadMarcas();
		}
		
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		loadDatoseditar(id);
	}
	//este metodo nos sirve para guardar una Marca 
	public String Guardar() {
		try {
		 MDAO.Guardar(marca);
		 loadMarcas();

		}catch (Exception e) {
		String errorMessage = error.getRootErrorMessage(e);
	    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
	    facesContext.addMessage(null, m);
		}
		 return "listarMarca";

	}
	//este metodo nos sirve para eliminar una Marca y a la vez mostra en pantalla las marcas que sobran
	public String Eliminar(int id) {
		MDAO.Borrar(id);
		loadMarcas();
		return "listarMarca";
	}
	//este metodo nos sirve para cargar los datos de todos las Marca que existen en la BD
	private void loadMarcas() {
		marcas=MDAO.listadomarcas();
	}
	//este metodo nos sirve para cargar la pagina y editar los datos de Marca
	public String loadDatoseditar(int id) {
		marca = MDAO.Leer(id);
		return "editarMarca";
	}
	
}
