package edu.ups.ec.siremo.service;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.dao.VestimentaDao;
import edu.ups.ec.siremo.dao.VotosDao;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.modelo.Vestimenta;
import edu.ups.ec.siremo.modelo.Votos;

@Path("descripcion")
public class VotoDescService {


	private Usuario usuario;
	private Vestimenta vestimenta;
	
	@Inject
	private VotosDao votosDao;
	@Inject
	private VestimentaDao vestimentaDao;
	@Inject
	private UsuarioDao usuarioDao;
	
	@GET
	@Path("/guardarVoto")
	@Produces("application/json")
	@Consumes("application/json")
	public void guardarVoto(@QueryParam("voto") int vot, @QueryParam("idUsuario") int idUsuario,
			@QueryParam("idVestimenta") int idVestimenta) {
		
		System.out.println("entro a insertar el nuevo voto!");
		Vestimenta vest=vestimentaDao.Leer(idVestimenta);
		Usuario usr=usuarioDao.Leer(idUsuario);
		Votos voto=new Votos();
		voto.setUsuario(usr);
		voto.setVestimenta(vest);
		voto.setVoto(vot);
		votosDao.Guardar(voto);
	}

	@GET
	@Path("/tuVotacion")
	@Produces("application/json")
	@Consumes("application/json")
	public int tuVotacionVesti(@QueryParam("idUsuario") int idUsuario,@QueryParam("idVestimenta") int idVestimenta){
		
		int voto = 0;
		
		loadUsuario(idUsuario);
		loadVestimenta(idVestimenta);
		
		Votos vot = votosDao.tuVotacion(usuario,vestimenta);
		
		if(vot != null) {
			voto = vot.getVoto();
		}else {
			voto=0;
		}
	
		return voto;
	
	}
	
	
	public void loadUsuario(int idUsuario) {
		
		usuario = usuarioDao.Leer(idUsuario);
		
	}
	
	public void loadVestimenta(int idVestimenta) {
		
		vestimenta = vestimentaDao.Leer(idVestimenta);
		
	}
	
}
