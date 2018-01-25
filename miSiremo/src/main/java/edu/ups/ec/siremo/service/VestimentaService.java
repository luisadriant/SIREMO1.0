package edu.ups.ec.siremo.service;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import edu.ups.ec.siremo.dao.EmpresaDao;
import edu.ups.ec.siremo.dao.MarcaDao;
import edu.ups.ec.siremo.dao.VestimentaDao;
import edu.ups.ec.siremo.modelo.Administrador;
import edu.ups.ec.siremo.modelo.Empresa;
import edu.ups.ec.siremo.modelo.Marca;
import edu.ups.ec.siremo.modelo.Vestimenta;

@Path("/vestimenta")
public class VestimentaService {
	
	@Inject
	private EmpresaDao empresaDao;

	@Inject
	private MarcaDao marcaDao;
	
	@Inject
	private VestimentaDao vestimentaDao;
	
	@GET
	@Path("/vervestimenta")
	@Produces("application/json")
	public Vestimenta verVestimenta(@QueryParam("id") int id) {
		Vestimenta vestimenta = vestimentaDao.Leer(id);
		return vestimenta;
	}
	
	@GET
	@Path("/delvestimenta")
	@Produces("application/json")
	public Respuesta delVestimenta(@QueryParam("id") int id) {
		Respuesta resp = new Respuesta();
		try {
			vestimentaDao.Borrar(id);
			resp.setCodigo(1);
			resp.setMensaje("Registro satisfactorio");
		}catch(Exception e) {
			resp.setCodigo(-1);
			resp.setMensaje("Error en registro");
		}			
		return resp;
	}
	
	
	
	@POST
	@Path("/editvestimenta")
	@Produces("application/json")
	@Consumes("application/json")
	public Respuesta editVestimenta(Vestimenta vestimenta, @QueryParam("id") int id) {
		Empresa empresa = empresaDao.Leer(id);
		for (Vestimenta v:empresa.getVestimentas()) {
			if(v.getId()==vestimenta.getId()) {
				v.setColor(vestimenta.getColor());
				v.setDescripcion(vestimenta.getDescripcion());
				v.setEstilo(vestimenta.getEstilo());
				v.setGenero(vestimenta.getGenero()); 
				v.setPrecio(vestimenta.getPrecio());
				v.setTipo(vestimenta.getTipo());
				v.setTalla(vestimenta.getTalla());
				v.setImagen(vestimenta.getImagen());
				v.setMarca(vestimenta.getMarca());
			}
		}
		Respuesta resp = new Respuesta();
		try {
			empresaDao.Guardar(empresa);
			resp.setCodigo(1);
			resp.setMensaje("Registro satisfactorio");
		}catch(Exception e) {
			resp.setCodigo(-1);
			resp.setMensaje("Error en registro");
		}			
		return resp;
	}
	
	@POST
	@Path("/newvestimenta")
	@Produces("application/json")
	@Consumes("application/json")
	public Respuesta newVestimenta(Vestimenta vestimenta, @QueryParam("id") int id) {
		Empresa empresa = empresaDao.Leer(id);
		if (empresa!=null) {
			System.out.println(vestimenta.getId_marca()+ ": MARCA");
			if(vestimenta.getId_marca() != 0) {
				 Marca marca=marcaDao.Leer(vestimenta.getId_marca());
				 vestimenta.addMarca(marca);
			} 
			empresa.addVestimenta(vestimenta);
		}
		Respuesta resp = new Respuesta();
		try {
			empresaDao.Guardar(empresa);
			resp.setCodigo(1);
			resp.setMensaje("Registro satisfactorio");
		}catch(Exception e) {
			resp.setCodigo(-1);
			resp.setMensaje("Error en registro");
		}			
		return resp;
	}
	
	@GET
	@Path("/listavestimentas")
	@Produces("application/json")
	public ArrayList<Vestimenta> listVestimenta(@QueryParam("id") int id) {
		Empresa e = empresaDao.Leer(id);
		//System.out.println(a.getApellidos()+":apellidos>>>"+a.getEmpresas().get(0).getContacto());
		ArrayList<Vestimenta> vestimentas= new ArrayList<Vestimenta>();//) a.getEmpresas();//empresaDao.listadoempresas();
		for (Vestimenta v: e.getVestimentas()) {
			v.setEmpresa(null);
			vestimentas.add(v);
		}
		return vestimentas;
	}
	

}