package edu.ups.ec.siremo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import edu.ups.ec.siremo.modelo.Empresa;

/**
 * Esta clase nos sirve para realizar todos los metodos crud y poder interactuar con la BD
 * @author root
 */
@Stateless
public class EmpresaDao {
	
	//llamamos al entity manager el cual nos permite la conexion con la BD y poder realizar las persistencias
	@Inject
	private EntityManager EM;
	
	//este metodo nos permite guardar la persistencia, en caso de que exista actualiza sus datos
	public void Guardar(Empresa e) {
		Empresa empresa=Leer(e.getId());
		if(empresa==null)
			Insertar(e);
		else
			Actualizar(e);
	}
	
	public void Insertar(Empresa e) {
		EM.persist(e);
	}
	public void Actualizar(Empresa e) {
		EM.merge(e);
	}
	public void Borrar(int id) {
		EM.remove(Leer(id));
	}
	//este metodo nos sirve para buscar una Empresa en base a un id
	public Empresa Leer(int id) {
		Empresa empresa = EM.find(Empresa.class, id);
		empresa.getVestimentas().size();
		return empresa;
	}
	
	//Este metodo nos sirve para hacer un select y obtener todo las empresas
	public List<Empresa> listadoempresas() {
		String  jpql = "SELECT e FROM Empresa e";
		Query query = EM.createQuery(jpql, Empresa.class);
		List<Empresa> empresas = query.getResultList(); 
		return empresas;
	}

}
