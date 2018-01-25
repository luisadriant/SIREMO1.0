package edu.ups.ec.siremo.service;

import java.util.ArrayList;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import edu.ups.ec.siremo.dao.AdministradorDao;
import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.modelo.Administrador;
import edu.ups.ec.siremo.modelo.Usuario;


@Path("/login")
public class LoginService {
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private AdministradorDao administradorDao;
	
	@GET
	@Path("/usuario")
	@Produces("application/json")
	public Usuario getUsuario(@QueryParam("usuario") String nombreusuario,@QueryParam("pass") String password) {
		if(nombreusuario!=null && password!=null) {
			ArrayList<Usuario> usuarios=(ArrayList<Usuario>) usuarioDao.listadousuarioLog(nombreusuario, password);
			if(usuarios.size()>0) {
				Usuario usuario = usuarioDao.Leer(usuarios.get(0).getId());
				return usuario;
			}
		}
		return new Usuario();
	}
	
	@GET
	@Path("/administrador")
	@Produces("application/json")
	public Administrador getAdministrador(@QueryParam("usuario") String nombreusuario,@QueryParam("pass") String password) {
		if(nombreusuario!=null && password!=null) {
			ArrayList<Administrador> administradores=(ArrayList<Administrador>) administradorDao.listadoadministradoresLog(nombreusuario, password);
			if(administradores.size()>0) {
				Administrador administrador = administradorDao.Leer(administradores.get(0).getId());
				System.out.println(administrador.getNombreusuario()+"<<<ADMIN>>>");
				administrador.setEmpresas(null);
				return administrador;
			}
		}
		return new Administrador();
	}
	
	@GET
	@Path("/datosadministrador")
	@Produces("application/json")
	public Administrador getAdministradordatos(@QueryParam("id") int id) {
				Administrador administrador = administradorDao.Leer(id);
				//System.out.println(administrador.getNombreusuario()+"<<<ADMIN>>>");
				administrador.setEmpresas(null);
				return administrador;
	}

	
}
