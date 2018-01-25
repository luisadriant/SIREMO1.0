package edu.ups.ec.siremo.service;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import edu.ups.ec.siremo.dao.AdministradorDao;
import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.modelo.Administrador;
import edu.ups.ec.siremo.modelo.Usuario;


@Path("/registro")
public class RegistroService {

	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private AdministradorDao administradorDao;
	
	@POST
	@Path("/usuario")
	@Produces("application/json")
	@Consumes("application/json")
	public Respuesta saveUsuario(Usuario usr) {
		Respuesta resp = new Respuesta();
		try {
			Date d=new Date("06/05/1994");
			usr.setFechanacimiento(d);
			boolean guardado= usuarioDao.Guardar(usr);
			if (guardado) {
				resp.setCodigo(1);
				resp.setMensaje("Registro satisfactorio");
			 }else {
				 resp.setCodigo(-1);
				 resp.setMensaje("Error en registro");
			 }
			
		}catch(Exception e) {
			resp.setCodigo(-1);
			resp.setMensaje("Error en registro");
		}			
		return resp;
	}
	
	@POST
	@Path("/administrador")
	@Produces("application/json")
	@Consumes("application/json")
	public Respuesta saveAdministrador(Administrador admin) {
		Respuesta resp = new Respuesta();
		try {
			Date d=new Date("06/05/1994");
			admin.setFechanacimiento(d);
			boolean guardado= administradorDao.Guardar(admin);
			if (guardado) {
				resp.setCodigo(1);
				resp.setMensaje("Registro satisfactorio");
			 }else {
				 resp.setCodigo(-1);
				 resp.setMensaje("Error en registro");
			 }
			
		}catch(Exception e) {
			resp.setCodigo(-1);
			resp.setMensaje("Error en registro");
		}			
		return resp;
	}
	
	@POST
	@Path("/editadministrador")
	@Produces("application/json")
	@Consumes("application/json")
	public Respuesta editAdministrador(Administrador admin, @QueryParam("id") int id) {
		Respuesta resp = new Respuesta();
		try {
			Administrador administrador=administradorDao.Leer(id);
				administrador.setNombres(admin.getNombres());
				administrador.setApellidos(admin.getApellidos());
				administrador.setNombreusuario(admin.getNombreusuario());
				administrador.setTelefono(admin.getTelefono());
				administrador.setEmail(admin.getEmail());
				administrador.setGenero(admin.getGenero());
				administrador.setContrasenia(admin.getContrasenia());
			boolean guardado= administradorDao.Guardar(administrador);
			if (guardado) {
				resp.setCodigo(1);
				resp.setMensaje("Registro satisfactorio");
			 }else {
				 resp.setCodigo(-1);
				 resp.setMensaje("Error en registro");
			 }
			
		}catch(Exception e) {
			resp.setCodigo(-1);
			resp.setMensaje("Error en registro");
		}			
		return resp;
	}
	
	

}
