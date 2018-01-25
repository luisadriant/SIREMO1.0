package edu.ups.ec.siremo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.ups.ec.siremo.modelo.PrediccionesFCM;


@Stateless
public class PrediccionesDao {

	
	@Inject
	private EntityManager EM;
	
	public void Insertar(PrediccionesFCM pre) {
		EM.persist(pre);
	}
	public void Actualizar(PrediccionesFCM pre) {
		EM.merge(pre);
	}
	public void Borrar(PrediccionesFCM pred) {
		EM.remove(pred);
	}
	//este metodo nos sirve para buscar una Marca en base a un id
	public PrediccionesFCM Leer(int id) {
		PrediccionesFCM centroide = EM.find(PrediccionesFCM.class, id);
		return centroide;
	}
	//Este metodo nos sirve para hacer un select y obtener todo las marcas
	public List<PrediccionesFCM> listadoPredicciones() {
		String  jpql = "SELECT pre FROM PrediccionesFCM pre";
		Query query = EM.createQuery(jpql, PrediccionesFCM.class);
		List<PrediccionesFCM> predicciones= query.getResultList(); 
		return predicciones;
	}
	
	//Este metodo nos sirve para hacer un select y obtener todo las marcas
	public List<PrediccionesFCM> listPrediccionesUsua(int idUsuario) {
		String  jpql = "SELECT pre FROM PrediccionesFCM pre WHERE pre.usuario= :idUsuario";
		Query query = EM.createQuery(jpql, PrediccionesFCM.class);
		query.setParameter("idUsuario", idUsuario);
		
		List<PrediccionesFCM> predicciones= query.getResultList(); 
		return predicciones;
	}
	
}
