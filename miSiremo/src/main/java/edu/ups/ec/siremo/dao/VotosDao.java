package edu.ups.ec.siremo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.modelo.Vestimenta;
import edu.ups.ec.siremo.modelo.Votos;

@Stateless
public class VotosDao {

	//llamamos al entity manager el cual nos permite la conexion con la BD y poder realizar las persistencias
	@Inject
	private EntityManager EM;
	
	
	//este metodo nos permite guardar la persistencia, en caso de que exista actualiza sus datos
		public void Guardar(Votos newVoto) {
//			Votos voto=Leer(v.getId());
			List<Votos> listadoVotosUsu = listadoVotosUsua(newVoto.getUsuario());
			
			boolean bandera = false;
			int idVoto=0;
			for (int i = 0; i < listadoVotosUsu.size(); i++) {
				Votos voto=listadoVotosUsu.get(i);
				idVoto = voto.getId();
				if(newVoto.getVestimenta().getId() == voto.getVestimenta().getId()) {
					bandera = true;
					break;
				}
				
			}
			
			if(bandera) {
				newVoto.setId(idVoto);
				Actualizar(newVoto);
			}else {
				Insertar(newVoto);
			}
		}
		
		public void Insertar(Votos m) {
			EM.persist(m);
		}
		public void Actualizar(Votos m) {
			EM.merge(m);
		}
		public void Borrar(int id) {
			EM.remove(Leer(id));
		}
		//este metodo nos sirve para buscar una Marca en base a un id
		public Votos Leer(int id) {
			Votos voto = EM.find(Votos.class, id);
			return voto;
		}
		//Este metodo nos sirve para hacer un select y obtener todo las marcas
		public List<Votos> listadoVotosUsua(Usuario usuario) {
			String  jpql = "SELECT v FROM Votos v WHERE v.usuario= :usuario";
			Query query = EM.createQuery(jpql, Votos.class);
			query.setParameter("usuario",usuario);
			
			List<Votos> votos= query.getResultList(); 
			return votos;
		}
		
		public List<Votos> listadoVotos() {
			String  jpql = "SELECT v FROM Votos v";
			Query query = EM.createQuery(jpql, Votos.class);
			List<Votos> votos= query.getResultList(); 
			return votos;
		}
		public Votos tuVotacion(Usuario usuario, Vestimenta vestimenta) {
			String  jpql = "SELECT v FROM Votos v "
							+ "WHERE v.usuario= :usuario "
							+ "AND v.vestimenta= :vestimenta ";
			Query query = EM.createQuery(jpql, Votos.class);
			query.setParameter("usuario",usuario);
			query.setParameter("vestimenta",vestimenta);
			List<Votos> votos= query.getResultList();
			
			if(votos.size()!=0) return votos.get(0);
			else return null;
		}
		
		public List<Votos> meGustaron(Usuario usuario) {
			String  jpql = "SELECT v FROM Votos v "
							+ "WHERE v.usuario= :usuario "
							+ "AND v.voto= :voto ";
			Query query = EM.createQuery(jpql, Votos.class);
			query.setParameter("usuario",usuario);
			query.setParameter("voto",5);
			
			List<Votos> meGustaron= query.getResultList();
			
			return meGustaron;
			
		}
	
}
