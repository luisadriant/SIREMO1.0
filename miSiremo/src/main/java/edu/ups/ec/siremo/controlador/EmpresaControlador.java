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
import javax.servlet.http.Part;

import edu.ups.ec.siremo.dao.EmpresaDao;
import edu.ups.ec.siremo.dao.MarcaDao;
import edu.ups.ec.siremo.modelo.Empresa;
import edu.ups.ec.siremo.modelo.Marca;
import edu.ups.ec.siremo.modelo.Vestimenta;
import edu.ups.ec.siremo.util.ErrorsController;

/**
 * Esta clase sirve para enlazar la vista del archivo xhtml con el controlador de la Empresa
 * @author root
 *
 */
@ManagedBean
@ViewScoped
public class EmpresaControlador {
	

	// Instanciamos la clase que controla los errores con su respectivo inject.
	ErrorsController error = new ErrorsController();
	@Inject
	private FacesContext facesContext;
	//variables necesarias
	private Empresa empresa;
	private List<Empresa> empresas;
	private List<Marca> marcas;
	private List<SelectItem> camposMarcas;
	private int id_marca;
	private Marca marca;
	
	private Vestimenta newVestimenta;
	private Vestimenta selectVestimenta;
	private List<Vestimenta> filtVestimenta;

	private int id;
	
	//instanciamos en objeto de acceso a datos para poder injectar los metodos crud correspondiente al Empresa
	@Inject
	private EmpresaDao EDAO;

  @Inject
	private MarcaDao MDAO;

  
		//lista para los colores
		private List<SelectItem> colores;
		//lista para los estilos
		private List<SelectItem> estilos;
		//lista para los generos
		private List<SelectItem> generos;
		//lista para los tipos
		private List<SelectItem> tipos;
		//lista para los tallas
		private List<SelectItem> tallas;
		//
		private Part image;
		public Part getImage() {
			return image;
		}
		public void setImage(Part image) {
			this.image = image;
		}
		
		
		public Vestimenta getNewVestimenta() {
			return newVestimenta;
		}
		public void setNewVestimenta(Vestimenta newVestimenta) {
			this.newVestimenta = newVestimenta;
		}
		public List<Vestimenta> getFiltVestimenta() {
			return filtVestimenta;
		}
		public void setFiltVestimenta(List<Vestimenta> filtVestimenta) {
			this.filtVestimenta = filtVestimenta;
		}
		public void setSelectVestimenta(Vestimenta selectVestimenta) {
			this.selectVestimenta = selectVestimenta;
		}
		public Vestimenta getSelectVestimenta() {
			return selectVestimenta;
		}
		public List<SelectItem> getColores() {
			loadColores(); 
			return colores;
		}
		public void setColores(List<SelectItem> colores) {
			this.colores = colores;
		}
		public List<SelectItem> getEstilos() {
			loadEstilos();
			return estilos;
		}
		public void setEstilos(List<SelectItem> estilos) {
			this.estilos = estilos;
		}
		public List<SelectItem> getGeneros() {
			loadGeneros();
			return generos;
		}
		public void setGeneros(List<SelectItem> generos) {
			this.generos = generos;
		}
		public List<SelectItem> getTipos() {
			loadTipos();
			return tipos;
		}
		public void setTipos(List<SelectItem> tipos) {
			this.tipos = tipos;
		}
		public List<SelectItem> getTallas() {
			loadTallas();
			return tallas;
		}
		public void setTallas(List<SelectItem> tallas) {
			this.tallas = tallas;
		}
		private void loadColores() {
			colores=new ArrayList<SelectItem>();
			colores.add(new SelectItem("",""));
			colores.add(new SelectItem("Claro","Claro"));
			colores.add(new SelectItem("Obscuro","Obscuro"));

		}
		private void loadEstilos() {
			estilos=new ArrayList<SelectItem>();
			estilos.add(new SelectItem("",""));
			estilos.add(new SelectItem("Rocker","Rocker"));
			estilos.add(new SelectItem("Casual","Casual"));

		}
		private void loadGeneros() {
			generos=new ArrayList<SelectItem>();
			generos.add(new SelectItem("",""));
			generos.add(new SelectItem("Masculino","Masculino"));
			generos.add(new SelectItem("Femenino","Femenino"));

		}
		private void loadTipos() {
			tipos=new ArrayList<SelectItem>();
			tipos.add(new SelectItem("",""));
			tipos.add(new SelectItem("Torso","Torso"));
			tipos.add(new SelectItem("Piernas","Piernas"));

		}
		private void loadTallas() {
			tallas=new ArrayList<SelectItem>();
			tallas.add(new SelectItem("",""));
			tallas.add(new SelectItem("S","S"));
			tallas.add(new SelectItem("M","M"));
			tallas.add(new SelectItem("L","L"));
			tallas.add(new SelectItem("XL","XL"));

		}

	
	//este metodo se inicia cada vez que se construye la clase
		@PostConstruct
	public void init() {
		empresa=new Empresa();
		marca=new Marca();
		empresa.addVestimenta(new Vestimenta());
		newVestimenta=new Vestimenta();
//		loadEmpresas();
		loadMarcas();
		
	}
	
	public List<SelectItem> getCamposMarcas() {
		loadCamposMarcas();
		return camposMarcas;
	}

	public void setCamposMarcas(List<SelectItem> camposMarcas) {
		this.camposMarcas = camposMarcas;
	}
	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}

	public int getId_marca() {
		return id_marca;
	}

	public void setId_marca(int id_marca) {
		this.id_marca = id_marca;
		loadDatosMarca(id_marca);
	}

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

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		loadDatoseditar(id);
	}
	//este metodo nos sirve para guardar una Empresa 
//	public String Guardar() {
//
//    try {
//		 for (int i=0 ; i<empresa.getVestimentas().size(); i++) {
//			 if(empresa.getVestimentas().get(i).getId_marca() != 0) {
//				 loadDatosMarca(empresa.getVestimentas().get(i).getId_marca());
//				 empresa.getVestimentas().get(i).addMarca(marca);
//			 } 
//		 }
//		 EDAO.Guardar(empresa);
//		 loadEmpresas();
//      }catch (Exception e) {
//		String errorMessage = error.getRootErrorMessage(e);
//	    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
//	    facesContext.addMessage(null, m);
//		}
//		 return "misVestimentas_face";
//
//	}
	//este metodo nos sirve para eliminar una Empresa y a la vez mostra en pantalla las empresas que sobran
	public String Eliminar(int id, int idAdmin) throws IOException {
		System.out.println(id+"<+<+<+<+<+<+<+<+<+<+<+<");
		EDAO.Borrar(id);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("misEmpresas_face.xhtml?id="+idAdmin);
		//loadEmpresas();
		return "";
	}
	//este metodo nos sirve para cargar los datos de todos las Empresas que existen en la BD
//	private void loadEmpresas() {
//		empresas=EDAO.listadoempresas();
//		
//	}
	private void loadMarcas() {
		// TODO Auto-generated method stub
		marcas=MDAO.listadomarcas();
	}
	//este metodo nos sirve para cargar la pagina y editar los datos de la empresa
	public String loadDatoseditar(int id) {
		empresa = EDAO.Leer(id);
		System.out.println("el tamano de las vestimentas="+empresa.getVestimentas().size()+" id="+id);
		
		for (int i=0 ; i<empresa.getVestimentas().size(); i++) {
			 if(empresa.getVestimentas().get(i).getId_marca() == 0) {
				 empresa.getVestimentas().get(i).setId_marca(empresa.getVestimentas().get(i).getMarca().getId());
			 } 
		}
		
		return "editarEmpresa";
	}
	public String loadDatosMarca(int id) {
		marca = MDAO.Leer(id);
		return "editarEmpresa";
	}
	//este metodo nos sirve para cargar la pagina y editar los datos de empresa
	public String addVestimenta() {
		empresa.addVestimenta(new Vestimenta());
		return null;
	}

	private void loadCamposMarcas() {
		loadMarcas();
		camposMarcas=new ArrayList<SelectItem>();
		 for (int i=0 ; i<marcas.size(); i++) {
			 camposMarcas.add(new SelectItem(marcas.get(i).getId()+"",marcas.get(i).getNombre()+""));
		 }
	}
	
	public String Cancelar(int id) throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("misVestimentas_face.xhtml?id="+id);
		return "";
	}
	
	public String EditarVestimenta(int id) throws IOException {
		for (Vestimenta v:empresa.getVestimentas()) {
			if(v.getId()==id) {
				v.setColor(selectVestimenta.getColor());
				v.setDescripcion(selectVestimenta.getDescripcion());
				v.setEstilo(selectVestimenta.getEstilo());
				v.setGenero(selectVestimenta.getGenero());
				if(selectVestimenta.getId_marca() != 0) {
					 loadDatosMarca(selectVestimenta.getId_marca());
					 v.addMarca(marca);
				} 
				v.setPrecio(selectVestimenta.getPrecio());
				v.setTipo(selectVestimenta.getTipo());
				v.setTalla(selectVestimenta.getTalla());
				v.setImagen(selectVestimenta.getImagen());
			}
		}
		EDAO.Guardar(empresa);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("misVestimentas_face.xhtml?id="+empresa.getId());
		return "";
	}
	public String NuevaVestimenta() throws IOException {
		if (newVestimenta!=null) {
			if(newVestimenta.getId_marca() != 0) {
				 loadDatosMarca(newVestimenta.getId_marca());
				 newVestimenta.addMarca(marca);
			} 
			System.out.println("---"+newVestimenta);
			
			empresa.addVestimenta(newVestimenta);
			EDAO.Guardar(empresa);
			newVestimenta=new Vestimenta();
		}
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("misVestimentas_face.xhtml?id="+empresa.getId());
		return "";
	}
}
