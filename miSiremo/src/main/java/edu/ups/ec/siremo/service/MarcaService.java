package edu.ups.ec.siremo.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import edu.ups.ec.siremo.dao.MarcaDao;
import edu.ups.ec.siremo.modelo.Marca;

@Path("/marca")
public class MarcaService {
	
	@Inject
	private MarcaDao marcaDao;
	@POST
	@Path("/newmarca")
	@Produces("application/json")
	@Consumes("application/json")
	public Respuesta newMarca(Marca marca) {
		Respuesta resp = new Respuesta();
		try {
			marcaDao.Guardar(marca);
			resp.setCodigo(1);
			resp.setMensaje("Registro satisfactorio");
		}catch(Exception e) {
			resp.setCodigo(-1);
			resp.setMensaje("Error en registro");
		}			
		return resp;
	}
	
	@GET
	@Path("/getmarcas")
	@Produces("application/json")
	public List<Marca> getMarcas() {
		List<Marca> marcas = marcaDao.listadomarcas();	
		return marcas;
	}

}
