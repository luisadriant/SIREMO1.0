package edu.ups.ec.siremo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import edu.ups.ec.siremo.modelo.Administrador;
import edu.ups.ec.siremo.modelo.Usuario;

/**
 * Esta clase nos sirve para realizar todos los metodos crud y poder interactuar con la BD
 * @author root
 */
@Stateless
public class AdministradorDao {

	//llamamos al entity manager el cual nos permite la conexion con la BD y poder realizar las persistencias
	@Inject
	private EntityManager EM;
	
	//este metodo nos permite guardar la persistencia, en caso de que exista actualiza sus datos
	public boolean Guardar(Administrador a) {
		Administrador adm=Leer(a.getId());
		if(adm==null && listadoadministradoresUN(a.getNombreusuario()).size()==0) {
			Insertar(a);
			return true;
		}else if (adm!=null){
			Actualizar(a);
			return true;
		}else
			return false;
	}
	
	public void Insertar(Administrador a) {
		EM.persist(a);
	}
	public void Actualizar(Administrador a) {
		EM.merge(a);
	}
	public void Borrar(int id) {
		EM.remove(Leer(id));
	}
	//este metodo nos sirve para buscar un Administrador en base a un id
	public Administrador Leer(int id) {
		Administrador administrador = EM.find(Administrador.class, id);
		return administrador;
	}
	//Este metodo nos sirve para hacer un select y obtener todo los administradores
	public List<Administrador> listadoadministradores() {
		String  jpql = "SELECT a FROM Administrador a";
		Query query = EM.createQuery(jpql, Administrador.class);
		List<Administrador> listado = query.getResultList(); 
		return listado;
	}
	//metodo para verificar la existencia de un usuario administrador
	public List<Administrador> listadoadministradoresUN(String un) {
		String  jpql = "SELECT a FROM Administrador a WHERE a.nombreusuario=:un";
		Query query = EM.createQuery(jpql, Administrador.class);
		query.setParameter("un", un);
		List<Administrador> listado = query.getResultList(); 
		return listado;
	}
	//metodo para verificar datos de logeo
	public List<Administrador> listadoadministradoresLog(String un, String pass) {
		String  jpql = "SELECT a FROM Administrador a WHERE a.nombreusuario=:un AND a.contrasenia=:pass";
		Query query = EM.createQuery(jpql, Administrador.class);
		query.setParameter("un", un);
		query.setParameter("pass", pass);
		List<Administrador> listado = query.getResultList();
		return listado;
	}
}
