package edu.ups.ec.siremo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.ups.ec.siremo.modelo.Vestimenta;

/**
 * Esta clase nos sirve para realizar todos los metodos crud y poder interactuar con la BD
 * @author root
 */
@Stateless
public class VestimentaDao {
	
	//llamamos al entity manager el cual nos permite la conexion con la BD y poder realizar las persistencias
	@Inject
	private EntityManager EM;
	
	//este metodo nos permite guardar la persistencia, en caso de que exista actualiza sus datos
	public void Guardar(Vestimenta v) {
		Vestimenta vestimeta=Leer(v.getId());
		if(vestimeta==null)
			Insertar(v);
		else
			Actualizar(v);
	}
	
	public void Insertar(Vestimenta v) {
		EM.persist(v);
	}
	public void Actualizar(Vestimenta v) {
		EM.merge(v);
	}
	public void Borrar(int id) {
		EM.remove(Leer(id));
	} 
	//este metodo nos sirve para buscar una Vestimenta en base a un id
	public Vestimenta Leer(int id) {
		Vestimenta vestimeta = EM.find(Vestimenta.class, id);
		return vestimeta;
	}
	//Este metodo nos sirve para hacer un select y obtener todo las vestimentas
	public List<Vestimenta> listadovestimentas() {
		String  jpql = "SELECT e FROM Vestimenta e";
		Query query = EM.createQuery(jpql, Vestimenta.class);
		List<Vestimenta> vestimetas = query.getResultList(); 

		return vestimetas;
	}

}
