package edu.ups.ec.siremo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.ups.ec.siremo.modelo.Marca;

/**
 * Esta clase nos sirve para realizar todos los metodos crud y poder interactuar con la BD
 * @author root
 */
@Stateless
public class MarcaDao {
	
	//llamamos al entity manager el cual nos permite la conexion con la BD y poder realizar las persistencias
	@Inject
	private EntityManager EM;
	
	//este metodo nos permite guardar la persistencia, en caso de que exista actualiza sus datos
	public void Guardar(Marca m) {
		Marca marca=Leer(m.getId());
		if(marca==null)
			Insertar(m);
		else
			Actualizar(m);
	}
	
	public void Insertar(Marca m) {
		EM.persist(m);
	}
	public void Actualizar(Marca m) {
		EM.merge(m);
	}
	public void Borrar(int id) {
		EM.remove(Leer(id));
	}
	//este metodo nos sirve para buscar una Marca en base a un id
	public Marca Leer(int id) {
		Marca marca = EM.find(Marca.class, id);
		return marca;
	}
	//Este metodo nos sirve para hacer un select y obtener todo las marcas
	public List<Marca> listadomarcas() {
		String  jpql = "SELECT m FROM Marca m";
		Query query = EM.createQuery(jpql, Marca.class);
		List<Marca> marcas = query.getResultList(); 
		return marcas;
	}
	

}
