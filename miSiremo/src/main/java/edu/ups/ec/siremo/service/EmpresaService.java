package edu.ups.ec.siremo.service;

import java.awt.List;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import edu.ups.ec.siremo.dao.AdministradorDao;
import edu.ups.ec.siremo.dao.EmpresaDao;
import edu.ups.ec.siremo.dao.MarcaDao;
import edu.ups.ec.siremo.modelo.Administrador;
import edu.ups.ec.siremo.modelo.Empresa;

@Path("/empresa")
public class EmpresaService {
	
	@Inject
	private EmpresaDao empresaDao;

	@Inject
	private MarcaDao marcaDao;
	
	@Inject
	private AdministradorDao administradorDao;
	
	@GET
	@Path("/verempresa")
	@Produces("application/json")
	public Empresa verEmpresa(@QueryParam("id") int id) {
		Empresa empresa = empresaDao.Leer(id);
		for (int i=0 ; i<empresa.getVestimentas().size(); i++) {			
			 if(empresa.getVestimentas().get(i).getId_marca() == 0) {
				 empresa.getVestimentas().get(i).setId_marca(empresa.getVestimentas().get(i).getMarca().getId());
			 } 
		}
		for (int i=0 ; i<empresa.getVestimentas().size(); i++) {
			empresa.getVestimentas().get(i).setEmpresa(null);
		}
		return empresa;
	}
	
	@POST
	@Path("/editempresa")
	@Produces("application/json")
	@Consumes("application/json")
	public Respuesta editEmpresa(Empresa empresa, @QueryParam("id") int id) {
		Administrador administrador = administradorDao.Leer(id);
		System.out.println("compara: "+empresa.getId()+"-"+administrador.getId());
		for (Empresa e:administrador.getEmpresas()) {
			System.out.println("compara: "+e.getId()+"-"+empresa.getId());
			if(e.getId()==empresa.getId()) {
				e.setContacto(empresa.getContacto());
				e.setDescripcion(empresa.getDescripcion());
				e.setDireccion(empresa.getDireccion());
				e.setNombre(empresa.getNombre());
				e.setRuc(empresa.getRuc());
			}
		}
		Respuesta resp = new Respuesta();
		try {
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
	
	@POST
	@Path("/newempresa")
	@Produces("application/json")
	@Consumes("application/json")
	public Respuesta newEmpresa(Empresa empresa, @QueryParam("id") int id) {
		Administrador administrador = administradorDao.Leer(id);
		if (empresa!=null) {
			administrador.addEmpresa(empresa);
		}
		Respuesta resp = new Respuesta();
		try {
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
	
	@GET
	@Path("/delempresa")
	@Produces("application/json")
	public Respuesta delVestimenta(@QueryParam("id") int id) {
		Respuesta resp = new Respuesta();
		try {
			empresaDao.Borrar(id);
			resp.setCodigo(1);
			resp.setMensaje("Registro satisfactorio");
		}catch(Exception e) {
			resp.setCodigo(-1);
			resp.setMensaje("Error en registro");
		}			
		return resp;
	}
	
	@GET
	@Path("/listaempresas")
	@Produces("application/json")
	public ArrayList<Empresa> listEmpresa(@QueryParam("id") int id) {
		Administrador a = administradorDao.Leer(id);
		System.out.println(a.getApellidos()+":apellidos>>>"+a.getEmpresas().get(0).getContacto());
		ArrayList<Empresa> empresas= new ArrayList<Empresa>();//) a.getEmpresas();//empresaDao.listadoempresas();
		for (Empresa e: a.getEmpresas()) {
			e.setVestimentas(null);
			empresas.add(e);
		}
		return empresas;
	}
	
	
}