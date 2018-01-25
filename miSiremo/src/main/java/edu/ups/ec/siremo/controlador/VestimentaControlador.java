package edu.ups.ec.siremo.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;

import javax.inject.Inject;

import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.dao.VestimentaDao;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.modelo.Vestimenta;
import edu.ups.ec.siremo.modelo.Votos;
import edu.ups.ec.siremo.util.ErrorsController;

/**
 * Esta clase sirve para enlazar la vista del archivo xhtml con el controlador de la vestimenta 
 * @author root
 *
 */
@ManagedBean
@ViewScoped
public class VestimentaControlador {
	

	// Instanciamos la clase que controla los errores con su respectivo inject.
	ErrorsController error = new ErrorsController();
	@Inject
	private FacesContext facesContext;	
	
	//instancia de la entidad de negocio Vestimenta
	
	private Usuario usuario;

	private Vestimenta vestimenta;
	//variables necesarias
	private List<Vestimenta> vestimentas;
	private List<Vestimenta> datosVesti;
	private List<SelectItem> camposVotos;
	private List<Votos> votos;
	private int id;
	
	//instanciamos en objeto de acceso a datos para poder injectar los metodos crud correspondiente al Vestimenta
	@Inject
	private VestimentaDao VDAO;
	
	@Inject
	private UsuarioDao UDAO;
	

	
	public List<SelectItem> getCamposVotos() {
		loadCamposVotos();
		return camposVotos;
	}

	public void setCamposVotos(List<SelectItem> camposVotos) {
		this.camposVotos = camposVotos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	//este metodo se inicia cada vez que se construye la clase
		@PostConstruct
	public void init() {
		vestimenta=new Vestimenta();
		usuario=new Usuario();
		//loadVestimentas();
	}
		
	public Vestimenta getVestimenta() {
		return vestimenta;
	}

	public void setVestimenta(Vestimenta vestimenta) {
		this.vestimenta = vestimenta;
	}

	public List<Vestimenta> getVestimentas() {
		return vestimentas;
	}

	public void setVestimentas(List<Vestimenta> vestimentas) {
		this.vestimentas = vestimentas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
//		loadVestimentas();
		loadDatoUsuario(id);
	}
	
	//este metodo nos sirve para guardar una Vestimenta 
//	public String Guardar() {
//
//  try {
//		loadDatoUsuario(id);
//		 for (int i=0; i< vestimentas.size(); i++) {
//			 
//				 Votos v=new Votos();
//				 v.setUsuario(usuario);
//				 v.setVoto(vestimentas.get(i).getVoto());
//				 if(vestimentas.get(i).getVotos()==null) 
//				 vestimentas.get(i).setVotos(new ArrayList<Votos>());
//				 vestimentas.get(i).getVotos().add(v);
//				 VDAO.Guardar(vestimentas.get(i));
//
//		 }		
//		 loadVestimentas();
//      }catch (Exception e) {
//			String errorMessage = error.getRootErrorMessage(e);
//		    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
//		    facesContext.addMessage(null, m);
//		}
//		 return "listaUsuariosVotar";
//	}
	//este metodo nos sirve para eliminar una Vestimenta y a la vez mostra en pantalla las vestimentas que sobran
	public String Eliminar(int id) {
		VDAO.Borrar(id);
		//loadVestimentas();
		return "misVestimentas_face";
	}
	//este metodo nos sirve para cargar los datos de todas las Vestimentas que existen en la BD
//	private void loadVestimentas() {
//		// TODO Auto-generated method stub
//		vestimentas=VDAO.listadovestimentas();
//		loadDatoUsuario(id);
//		 for (int i=0; i< vestimentas.size(); i++) {
//			 for(int j=0; j< vestimentas.get(i).getVotos().size(); j++) {
//				 if(vestimentas.get(i).getVotos().get(j)!=null) {
//				 if(vestimentas.get(i).getVotos().get(j).getUsuario().getId()==usuario.getId()) {
//					 //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++"+vestimentas.get(i).getVotos().get(j).getUsuario().getId());
//					 vestimentas.get(i).setVoto(vestimentas.get(i).getVotos().get(j).getVoto());
//					 }
//				 }
//			 }
//		 }
//			}
	//este metodo nos sirve para eliminar una Vestimenta y a la vez mostra en pantalla las vestimentas que sobran
		public String Eliminar(int id, int idEmp) throws IOException {
			System.out.println("datos: id "+id+" emp: "+idEmp);
			VDAO.Borrar(id);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect("misVestimentas_face.xhtml?id="+idEmp);
			//loadVestimentas();
			return "";
		}
		
	public void cargarDatosVestimenta(int codUser, int codEmp){
		datosVesti= VDAO.listadovestimentas();
		loadDatoUsuario(id);
		    for (int i = 0; i <datosVesti.size(); i++) {
		    	if(codUser == 1 && codEmp ==3){
		    	 datosVesti.get(i).getDescripcion();
		    	 datosVesti.get(i).getColor();
		    	 datosVesti.get(i).getEstilo();
		    	 datosVesti.get(i).getGenero();
		    		
		    	}
				
			}
		
		
	}
	

	
	
	//este metodo nos sirve para cargar la pagina y editar los datos de Vestimentas
	public String loadDatoseditar(int id) {
		vestimenta = VDAO.Leer(id);
		System.out.println(vestimenta.getId());
		return "editarUsuario";
	}
	public void loadDatoUsuario(int id) {
		usuario = UDAO.Leer(id);
	}
	private void loadCamposVotos() {
		camposVotos=new ArrayList<SelectItem>();
		camposVotos.add(new SelectItem("1","1"));
		camposVotos.add(new SelectItem("2","2"));
		camposVotos.add(new SelectItem("3","3"));
		camposVotos.add(new SelectItem("4","4"));
		camposVotos.add(new SelectItem("5","5"));
		camposVotos.add(new SelectItem("0","0"));
	}
	
	
	
}
