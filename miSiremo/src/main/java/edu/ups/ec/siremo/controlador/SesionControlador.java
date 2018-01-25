package edu.ups.ec.siremo.controlador;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import edu.ups.ec.siremo.dao.AdministradorDao;
import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.modelo.Administrador;
import edu.ups.ec.siremo.modelo.Empresa;
import edu.ups.ec.siremo.modelo.Usuario;

@ManagedBean
@SessionScoped
public class SesionControlador {
	
	//instancia de la entidad de negocio Usuario
	private Usuario usuario;
	//instalcia del administrador
	private Administrador administrador;
	//id de un usuario
	private int id;
	//empresa elegida
	private Empresa selectempresa;
	//empresa nueva
	private Empresa newempresa;
	
	//instanciamos en objeto de acceso a datos para poder injectar los metodos crud correspondiente al Usuario
		@Inject
		private UsuarioDao UDAO;
	//instanciamos en objeto de acceso a datos para poder injectar los metodos crud correspondiente al Usuario
		@Inject
		private AdministradorDao ADAO;
		
		@PostConstruct
		public void init() {
			usuario=new Usuario();
			administrador=new Administrador();
			newempresa=new Empresa();
		}
		
		
		public Empresa getSelectempresa() {
			return selectempresa;
		}


		public void setSelectempresa(Empresa selectempresa) {
			this.selectempresa = selectempresa;
		}
		

		public Empresa getNewempresa() {
			return newempresa;
		}


		public void setNewempresa(Empresa newempresa) {
			this.newempresa = newempresa;
		}


		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
			loadDatosUsuarioL(id);
		}
		
		public Usuario getUsuario() {
			return usuario;
		}
		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}
		public Administrador getAdministrador() {
			return administrador;
		}
		public void setAdministrador(Administrador administrador) {
			this.administrador = administrador;
		}
		//este metodo nos sirve para cargar la pagina y editar los datos de Usuario
		public String loadDatosUsuarioL(int id) {
			usuario = UDAO.Leer(id);
			administrador=ADAO.Leer(id);
			System.out.println(administrador.getEmpresas().get(0).getNombre()+"<<<<<<<<");
			return "";
		}
		public String EditarEmpresa(int id) throws IOException {
			for (Empresa e:administrador.getEmpresas()) {
				if(e.getId()==id) {
					e.setContacto(selectempresa.getContacto());
					e.setDescripcion(selectempresa.getDescripcion());
					e.setDireccion(selectempresa.getDireccion());
					e.setNombre(selectempresa.getNombre());
					e.setRuc(selectempresa.getRuc());
				}
			}
			ADAO.Guardar(administrador);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect("misEmpresas_face.xhtml?id="+administrador.getId());
			return "";
		}
		public String NuevaEmpresa() throws IOException {
			if (newempresa!=null) {
				System.out.println(newempresa.getDescripcion()+"<<<<<<empresa<<<<<<<");
				administrador.addEmpresa(newempresa);
				ADAO.Guardar(administrador);
				newempresa=new Empresa();
			}
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect("misEmpresas_face.xhtml?id="+administrador.getId());
			return "";
		}
		public String Cancelar(int id) throws IOException {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect("misEmpresas_face.xhtml?id="+id);
			return "";
		}

}
