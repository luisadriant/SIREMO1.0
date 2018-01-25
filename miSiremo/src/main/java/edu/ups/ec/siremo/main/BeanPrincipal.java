package edu.ups.ec.siremo.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import edu.ups.ec.siremo.algoritmo.FuzzyCMeans;
import edu.ups.ec.siremo.algoritmo.Recomendacion;
import edu.ups.ec.siremo.dao.PrediccionesDao;
import edu.ups.ec.siremo.dao.EmpresaDao;
import edu.ups.ec.siremo.dao.MarcaDao;
import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.dao.VestimentaDao;
import edu.ups.ec.siremo.dao.VotosDao;
import edu.ups.ec.siremo.modelo.Empresa;
import edu.ups.ec.siremo.modelo.Marca;
import edu.ups.ec.siremo.modelo.PrediccionesFCM;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.modelo.Vestimenta;
import edu.ups.ec.siremo.modelo.Votos;

/**
 * Este bean es necesario para cuando se alza el servidor mandar a correr el codigo del cluster recomendador.
 * @author root
 *
 */
@Startup
@Singleton
public class BeanPrincipal {

	@Inject
	private UsuarioDao usuarioDAO;

	@Inject
	private VotosDao votosDAO;
	
	@Inject
	private VestimentaDao vestimentaDAO;
	
	@Inject
	private EmpresaDao empresaDAO;
	
	@Inject
	private MarcaDao marcaDAO;
	
	@Inject
	private PrediccionesDao prediccionesDAO;
	
	
	
	
	private List<Votos> listVotos;
	private List<Usuario> listUsuarios;
	private List<Vestimenta> listVestimentas;

	
	@PostConstruct
	public void init() {
		
		listVotos= votosDAO.listadoVotos();
		listUsuarios= usuarioDAO.listadousuarios();
		listVestimentas= vestimentaDAO.listadovestimentas();

		//guardarMisDatos();
		
//		System.out.println("EEEE "+listVotos.get(0).getUsuario().getNombres());
//		
		FuzzyCMeans fcm = new FuzzyCMeans(listVotos, listUsuarios, listVestimentas);
		
		List<Hashtable> prediccionesFcm = fcm.prediccionesUsuarios();

		PrediccionesFCM pre;
		
		List<PrediccionesFCM> listaPredicciones = prediccionesDAO.listadoPredicciones();
		
//		System.out.println("Elllllll "+listaPredicciones.get(0).getId()+" tam="+listaPredicciones.size());
		
		for (int i = 0; i <listaPredicciones.size(); i++) {
			prediccionesDAO.Borrar(listaPredicciones.get(i));
		}
		
		for (int i = 0; i < prediccionesFcm.size(); i++) {
			for (int j = 0; j < prediccionesFcm.get(i).size(); j++) {
				
				if((int) prediccionesFcm.get(i).get(j)!=0) {
					pre = new PrediccionesFCM();
					
					pre.setItem(j+1);
					pre.setUsuario(i+1);
					pre.setPrediccion((int) prediccionesFcm.get(i).get(j));
					
//					pre.setPredicciones(prediccionesFcm);

					prediccionesDAO.Insertar(pre);
	
				}
									
			}
		}
		

	}
	
public void guardarMisDatos() {
		System.out.println("ENtro a guardar mis datos en la BD");
		List<Vestimenta> misVestimentas = new ArrayList();
		
//		List<Vestimenta> vestiAdidas= new ArrayList();
//		List<Vestimenta> vestiNike= new ArrayList();
//		List<Vestimenta> vestiTommy= new ArrayList();
//		List<Vestimenta> vestiReebok= new ArrayList();
//		List<Vestimenta> vestiMarathon= new ArrayList();
		
		Marca adidas = new Marca();
//		adidas.setId(1);
		adidas.setNombre("adidas");
		
		Marca nike= new Marca();
//		nike.setId(2);
		nike.setNombre("nike");
		
		Marca tommy = new Marca();
//		tommy.setId(3);
		tommy.setNombre("tommy");
		
		Marca reebok = new Marca();
//		reebok.setId(4);
		reebok.setNombre("reebok");
		
		Marca marathon = new Marca();
//		marathon.setId(5);
		marathon.setNombre("marathon");
		
		
		
		Empresa emp = new Empresa();
//		emp.setId(1);
		emp.setDescripcion("alguna descripcion");
		emp.setDireccion("cuenca");
		emp.setNombre("Shoping");
		emp.setContacto("21554541");
		emp.setRuc("0705642494111");
		
		
		System.out.println("viene po aquiii vetimentasBoris "+emp.getNombre()+" "); 

	empresaDAO.Insertar(emp);
//      
      marcaDAO.Guardar(adidas);
      marcaDAO.Guardar(nike);
      marcaDAO.Guardar(tommy);
      marcaDAO.Guardar(reebok);
      marcaDAO.Guardar(marathon);
		
		
		
		Random ale= new Random();
		
		for (int i = 0; i < 22; i++) {
			
			Vestimenta vesA = new Vestimenta();
			
			int desc=ale.nextInt(7);
			if(desc==0) {
				vesA.setDescripcion("Camisa Ran"+i);
				vesA.setColor("azul");
				vesA.setTalla("10");
				vesA.setGenero("masculino");
				vesA.setPrecio(44.1);
				vesA.setTipo("nose");
				vesA.setEstilo("Skater");
				vesA.setImagen("images/camisa1.jpg");
				
			}else if(desc==1) {
				vesA.setDescripcion("Pantaln Jen"+i);
				vesA.setColor("azul");
				vesA.setTalla("9");
				vesA.setGenero("masculino");
				vesA.setPrecio(44.1);
				vesA.setTipo("nose");
				vesA.setEstilo("Trendy");
				vesA.setImagen("images/pantalon.jpg");
				
			}else if(desc==2) {
				vesA.setDescripcion("Chompa "+i);
				vesA.setColor("Negro");
				vesA.setTalla("8");
				vesA.setGenero("Masculino");
				vesA.setPrecio(44.1);
				vesA.setTipo("nose");
				vesA.setEstilo("Casual");
				vesA.setImagen("images/chompa.jpg");
				
			}else if(desc==3) {
				vesA.setDescripcion("Saco "+i);
				vesA.setColor("Negro");
				vesA.setTalla("11");
				vesA.setGenero("Masculino");
				vesA.setPrecio(44.1);
				vesA.setTipo("nose");
				vesA.setEstilo("StreetWear");
				vesA.setImagen("images/leva.jpg");
				
			}else if(desc==4) {
				vesA.setDescripcion("Pantaloneta "+i);
				vesA.setColor("Blanco");
				vesA.setTalla("9");
				vesA.setGenero("Masculino");
				vesA.setPrecio(44.1);
				vesA.setTipo("nose");
				vesA.setEstilo("Hispter");
				vesA.setImagen("images/pantaloneta.jpg");
				
			}else if(desc==5) {
				vesA.setDescripcion("Zapatos"+i);
				vesA.setColor("Amarillo");
				vesA.setTalla("11");
				vesA.setGenero("Masculino");
				vesA.setPrecio(44.1);
				vesA.setTipo("nose");
				vesA.setEstilo("Elegante");
				vesA.setImagen("images/zapatos.jpg");
				
			}else if(desc==6) {
				vesA.setDescripcion("Camisa X"+i);
				vesA.setColor("Negro");
				vesA.setTalla("10");
				vesA.setGenero("Masculino");
				vesA.setPrecio(44.1);
				vesA.setTipo("nose");
				vesA.setEstilo("Rocker");
				vesA.setImagen("images/camiseta.jpg");
				
			}else if(desc==7) {
				vesA.setDescripcion("Vermuda "+i);
				vesA.setColor("Gris");
				vesA.setTalla("16");
				vesA.setGenero("Masculino");
				vesA.setPrecio(11.1);
				vesA.setTipo("nose");
				vesA.setEstilo("Casual");
				vesA.setImagen("images/vermuda.jpg");
			}
			
			

			vesA.setEmpresa(emp);
			
			int marc=ale.nextInt(5);
			if(marc==0) {
				vesA.setMarca(adidas);
			}else if(marc==1) {
				vesA.setMarca(nike);
			}else if(marc==2) {
				vesA.setMarca(tommy);
			}else if(marc==3) {
				vesA.setMarca(marathon);
			}else if(marc==4) {
				vesA.setMarca(reebok);
			}
			
				vestimentaDAO.Insertar(vesA);
			

	        
		}
		
		for (int i = 0; i < 12; i++) {
			
			Usuario usu = new Usuario();
			
			usu.setNombres("usuario"+(char)(65+i));
			usu.setApellidos(" "+(char)(65+i+1));
			usu.setNombreusuario("usu_"+(char)(65+i));
			usu.setEmail("todos@hotm");
			usu.setTelefono("224113"+i);
			usu.setFechanacimiento(new Date((i+1)+"/12/1993"));
			usu.setContrasenia("c");
			usu.setGenero("masculino");
			int aleEsti= ale.nextInt(3);
			if(aleEsti==0) {
				usu.setEstilo("Skater");	
			}else if(aleEsti==1) {
				usu.setEstilo("Rocker");
			}else if(aleEsti==2) {
				usu.setEstilo("Elegante");
			}
			
			usuarioDAO.Guardar(usu);
		}
			
        	int cont=0;
        	
        	try {
        		
        	FileReader archivo = new FileReader("/Users/luistobar/Downloads/PracticaFCM/datatoy3/train.dat");
        	BufferedReader lectura = new BufferedReader(archivo);
			
        	String fila;
        	String[] parametros;
		    
				while ((fila = lectura.readLine()) != null){
				      
	                    parametros = fila.split("::");
	                    
	            		Votos vot = new Votos();
	            		Usuario u= usuarioDAO.Leer(Integer.parseInt(parametros[0]));
	            		vot.setUsuario(u);
	            		Vestimenta v =vestimentaDAO.Leer(Integer.parseInt(parametros[1]));
	            		vot.setVestimenta(v);
	            		vot.setVoto(Integer.parseInt(parametros[2]));
	            		
	                    cont++;		
	                    
	                    votosDAO.Insertar(vot);
				}
				 
				lectura.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
        }		
		
	
	
//		
//		Vestimenta v1 = new Vestimenta();
////		v1.setId(1);
//		v1.setDescripcion("camisa Ran");
//		v1.setColor("azul");
//		v1.setTalla("10");
//		v1.setGenero("masculino");
//		v1.setPrecio(44.1);
//		v1.setTipo("nose");
//		v1.setEstilo("Skater");
//		v1.setImagen("camisa1.jpg");
//
//		v1.setEmpresa(emp);
//		v1.setMarca(adidas);
//		
//		Vestimenta v2 = new Vestimenta();
////		v2.setId(2);
//		v2.setDescripcion("pantalon jean");
//		v2.setColor("azul");
//		v2.setTalla("9");
//		v2.setGenero("masculino");
//		v2.setPrecio(44.1);
//		v2.setTipo("nose");
//		v2.setEstilo("Trendy");
//		v2.setImagen("pantalon.jpg");
//		
//		v2.setEmpresa(emp);
//		v2.setMarca(nike);
//		
//		Vestimenta v3 = new Vestimenta();
////		v3.setId(3);
//		v3.setDescripcion("chompa");
//		v3.setColor("Negro");
//		v3.setTalla("8");
//		v3.setGenero("Masculino");
//		v3.setPrecio(44.1);
//		v3.setTipo("nose");
//		v3.setEstilo("Casual");
//		v3.setImagen("chompa.jpg");
//		
//		v3.setEmpresa(emp);
//		v3.setMarca(tommy);
//		
//		Vestimenta v4 = new Vestimenta();
////		v4.setId(4);
//		v4.setDescripcion("Saco");
//		v4.setColor("Negro");
//		v4.setTalla("11");
//		v4.setGenero("Masculino");
//		v4.setPrecio(44.1);
//		v4.setTipo("nose");
//		v4.setEstilo("StreetWear");
//		v4.setImagen("leva.jpg");
//		
//		v4.setEmpresa(emp);
//		v4.setMarca(marathon);
//		
//		Vestimenta v5 = new Vestimenta();
////		v5.setId(5);
//		v5.setDescripcion("pantaloneta");
//		v5.setColor("Blanco");
//		v5.setTalla("9");
//		v5.setGenero("Masculino");
//		v5.setPrecio(44.1);
//		v5.setTipo("nose");
//		v5.setEstilo("Hispter");
//		v5.setImagen("pantaloneta.jpg");
//		
//		v5.setEmpresa(emp);
//		v5.setMarca(nike);
//		
//		Vestimenta v6 = new Vestimenta();
////		v6.setId(6);
//		v6.setDescripcion("Zapatos");
//		v6.setColor("Amarillo");
//		v6.setTalla("11");
//		v6.setGenero("Masculino");
//		v6.setPrecio(44.1);
//		v6.setTipo("nose");
//		v6.setEstilo("Elegante");
//		v6.setImagen("zapatos.jpg");
//		
//		v6.setEmpresa(emp);
//		v6.setMarca(reebok);
//		
//		Vestimenta v7 = new Vestimenta();
////		v7.setId(7);
//		v7.setDescripcion("Camiseta X");
//		v7.setColor("Negro");
//		v7.setTalla("10");
//		v7.setGenero("Masculino");
//		v7.setPrecio(44.1);
//		v7.setTipo("nose");
//		v7.setEstilo("Rocker");
//		v7.setImagen("camiseta.jpg");
//		
//		v7.setEmpresa(emp);
//		v7.setMarca(adidas);
//		
//		Vestimenta v8 = new Vestimenta();
////		v8.setId(8);
//		v8.setDescripcion("Vermuda");
//		v8.setColor("Gris");
//		v8.setTalla("8");
//		v8.setGenero("Masculino");
//		v8.setPrecio(44.1);
//		v8.setTipo("nose");
//		v8.setEstilo("Casual");
//		v8.setImagen("vermuda.jpg");
//		
//		v8.setEmpresa(emp);
//		v8.setMarca(tommy);
//		
//		Vestimenta v9 = new Vestimenta();
////		v9.setId(9);
//		v9.setDescripcion("camisa Ran");
//		v9.setColor("azul");
//		v9.setTalla("10");
//		v9.setGenero("masculino");
//		v9.setPrecio(44.1);
//		v9.setTipo("nose");
//		v9.setEstilo("Skater");
//		v9.setImagen("camisa1.jpg");
//		
//
//		v9.setEmpresa(emp);
//		v9.setMarca(adidas);
//		
//
//		Vestimenta v10 = new Vestimenta();
////		v10.setId(10);
//		v10.setDescripcion("pantalon jean");
//		v10.setColor("azul");
//		v10.setTalla("9");
//		v10.setGenero("masculino");
//		v10.setPrecio(12.1);
//		v10.setTipo("nose");
//		v10.setEstilo("Trendy");
//		v10.setImagen("pantalon.jpg");
//		
//		v10.setEmpresa(emp);
//		v10.setMarca(nike);
//		
//		Vestimenta v11 = new Vestimenta();
////		v11.setId(11);
//		v11.setDescripcion("chompa");
//		v11.setColor("Negro");
//		v11.setTalla("16");
//		v11.setGenero("Masculino");
//		v11.setPrecio(12.1);
//		v11.setTipo("nose");
//		v11.setEstilo("Casual");
//		v11.setImagen("chompa.jpg");
//		
//		v11.setEmpresa(emp);
//		v11.setMarca(tommy);
//		
//		Vestimenta v12 = new Vestimenta();
////		v12.setId(12);
//		v12.setDescripcion("Saco");
//		v12.setColor("Negro");
//		v12.setTalla("11");
//		v12.setGenero("Masculino");
//		v12.setPrecio(12.1);
//		v12.setTipo("nose");
//		v12.setEstilo("StreetWear");
//		v12.setImagen("leva.jpg");
//		
//		v12.setEmpresa(emp);
//		v12.setMarca(marathon);
//		
//		Vestimenta v13 = new Vestimenta();
////		v13.setId(13);
//		v13.setDescripcion("pantaloneta");
//		v13.setColor("Blanco");
//		v13.setTalla("9");
//		v13.setGenero("Masculino");
//		v13.setPrecio(12.1);
//		v13.setTipo("nose");
//		v13.setEstilo("Hispter");
//		v13.setImagen("pantaloneta.jpg");
//		
//		v13.setEmpresa(emp);
//		v13.setMarca(nike);
//		
//		Vestimenta v14 = new Vestimenta();
////		v14.setId(14);
//		v14.setDescripcion("Zapatos");
//		v14.setColor("Amarillo");
//		v14.setTalla("11");
//		v14.setGenero("Masculino");
//		v14.setPrecio(12.1);
//		v14.setTipo("nose");
//		v14.setEstilo("Elegante");
//		v14.setImagen("zapatos.jpg");
//		
//		v14.setEmpresa(emp);
//		v14.setMarca(reebok);
//		
//		Vestimenta v15 = new Vestimenta();
////		v15.setId(15);
//		v15.setDescripcion("Camiseta X");
//		v15.setColor("Negro");
//		v15.setTalla("10");
//		v15.setGenero("Masculino");
//		v15.setPrecio(2.1);
//		v15.setTipo("nose");
//		v15.setEstilo("Rocker");
//		v15.setImagen("camiseta.jpg");
//		
//		v15.setEmpresa(emp);
//		v15.setMarca(adidas);
//		
//		Vestimenta v16 = new Vestimenta();
////		v16.setId(16);
//		v16.setDescripcion("Vermuda");
//		v16.setColor("Gris");
//		v16.setTalla("16");
//		v16.setGenero("Masculino");
//		v16.setPrecio(11.1);
//		v16.setTipo("nose");
//		v16.setEstilo("Casual");
//		v16.setImagen("vermuda.jpg");
//		
//		v16.setEmpresa(emp);
//		v16.setMarca(tommy);
//		
//		
//
//
//		
//		
//		
//		misVestimentas.add(v1);
//		misVestimentas.add(v2);
//		misVestimentas.add(v3);
//		misVestimentas.add(v4);
//		misVestimentas.add(v5);
//		misVestimentas.add(v6);
//		misVestimentas.add(v7);
//		misVestimentas.add(v8);
//		misVestimentas.add(v9);
//		misVestimentas.add(v10);
//		misVestimentas.add(v11);
//		misVestimentas.add(v12);
//		misVestimentas.add(v13);
//		misVestimentas.add(v14);
//		misVestimentas.add(v15);
//		misVestimentas.add(v16);
		
//		emp.setVestimentas(misVestimentas);
		
//		vestiAdidas.add(v1);
//		vestiAdidas.add(v5);
//		vestiAdidas.add(v8);
//		vestiAdidas.add(v11);
//		vestiAdidas.add(v13);
//		
//		vestiNike.add(v2);
//		vestiNike.add(v9);
//		vestiNike.add(v12);
//		
//		vestiTommy.add(v3);
//		vestiTommy.add(v7);
//		vestiTommy.add(v10);
//		vestiTommy.add(v15);
//		
//		vestiMarathon.add(v4);
//		vestiMarathon.add(v16);
//		
//		vestiReebok.add(v6);
//		vestiReebok.add(v14);
		
//		 
//		adidas.setVestimentas(vestiAdidas);
//		nike.setVestimentas(vestiNike);
//		tommy.setVestimentas(vestiTommy);
//		reebok.setVestimentas(vestiReebok);
//		marathon.setVestimentas(vestiMarathon);
			
//		System.out.println("viene po aquiii vetimentasBoris "+emp.getNombre()+" "); 
//
//		empresaDAO.Insertar(emp);
////      
//      marcaDAO.Guardar(adidas);
//      marcaDAO.Guardar(nike);
//      marcaDAO.Guardar(tommy);
//      marcaDAO.Guardar(reebok);
//      marcaDAO.Guardar(marathon);
      
		
//		for (int i = 0; i < misVestimentas.size(); i++) {
//			
//		
//		try {
//        
//
//			VDAO.Insertar(misVestimentas.get(i));
//
//		}catch (Exception e) {
//		String errorMessage = error.getRootErrorMessage(e);
//		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
//		facesContext.addMessage(null, m);
//		}
//        
//		}
		
//		Usuario u1 = new Usuario();
//		u1.setNombres("pedro");
//		u1.setApellidos("bravo ");
//		u1.setNombreusuario("pedrob");
//		u1.setEmail("todos@hotm");
//		u1.setTelefono("55113");
//		u1.setFechanacimiento(new Date("11/12/1993"));
//		u1.setContrasenia("dfsfd");
//		
//		Usuario u2 = new Usuario();
//		u2.setNombres("xavier");
//		u2.setApellidos("bravo ");
//		u2.setNombreusuario("xavierb");
//		u2.setEmail("todos@hotm");
//		u2.setTelefono("55113");
//		u2.setFechanacimiento(new Date("11/12/1993"));
//		u2.setContrasenia("dfsfd");
//		
//		Usuario u3 = new Usuario();
//		u3.setNombres("boris");
//		u3.setApellidos("cabrera ");
//		u3.setNombreusuario("borisc");
//		u3.setEmail("todos@hotm");
//		u3.setTelefono("55113");
//		u3.setFechanacimiento(new Date("11/12/1993"));
//		u3.setContrasenia("dfsfd");
//		
//		Usuario u4 = new Usuario();
//		u4.setNombres("danilo");
//		u4.setApellidos("bravo ");
//		u4.setNombreusuario("danilob");
//		u4.setEmail("todos@hotm");
//		u4.setTelefono("55113");
//		u4.setFechanacimiento(new Date("11/12/1993"));
//		u4.setContrasenia("dfsfd");
//		
//		Usuario u5 = new Usuario();
//		u5.setNombres("fernando");
//		u5.setApellidos("bravo ");
//		u5.setNombreusuario("fernandob");
//		u5.setEmail("todos@hotm");
//		u5.setTelefono("55113");
//		u5.setFechanacimiento(new Date("11/12/1993"));
//		u5.setContrasenia("dfsfd");
//		
//		Usuario u6 = new Usuario();
//		u6.setNombres("armando");
//		u6.setApellidos("bravo ");
//		u6.setNombreusuario("armandob");
//		u6.setEmail("todos@hotm");
//		u6.setTelefono("55113");
//		u6.setFechanacimiento(new Date("11/12/1993"));
//		u6.setContrasenia("dfsfd");
//		
//		Usuario u7 = new Usuario();
//		u7.setNombres("paulo");
//		u7.setApellidos("bravo ");
//		u7.setNombreusuario("paulob");
//		u7.setEmail("todos@hotm");
//		u7.setTelefono("55113");
//		u7.setFechanacimiento(new Date("11/12/1993"));
//		u7.setContrasenia("dfsfd");
//		
//		Usuario u8 = new Usuario();
//		u8.setNombres("roger");
//		u8.setApellidos("bravo ");
//		u8.setNombreusuario("rogerb");
//		u8.setEmail("todos@hotm");
//		u8.setTelefono("55113");
//		u8.setFechanacimiento(new Date("11/12/1993"));
//		u8.setContrasenia("dfsfd");
//		
//		Usuario u9 = new Usuario();
//		u9.setNombres("byron");
//		u9.setApellidos("bravo ");
//		u9.setNombreusuario("byronb");
//		u9.setEmail("todos@hotm");
//		u9.setTelefono("55113");
//		u9.setFechanacimiento(new Date("11/12/1993"));
//		u9.setContrasenia("dfsfd");
//		
//		Usuario u10 = new Usuario();
//		u10.setNombres("jorge");
//		u10.setApellidos("bravo ");
//		u10.setNombreusuario("jorgeb");
//		u10.setEmail("todos@hotm");
//		u10.setTelefono("55113");
//		u10.setFechanacimiento(new Date("11/12/1993"));
//		u10.setContrasenia("dfsfd");
//		
//		Usuario u11 = new Usuario();
//		u11.setNombres("pablo");
//		u11.setApellidos("bravo ");
//		u11.setNombreusuario("pablob");
//		u11.setEmail("todos@hotm");
//		u11.setTelefono("55113");
//		u11.setFechanacimiento(new Date("11/12/1993"));
//		u11.setContrasenia("dfsfd");
//		
//		Usuario u12 = new Usuario();
//		u12.setNombres("jordi");
//		u12.setApellidos("bravo ");
//		u12.setNombreusuario("jordib");
//		u12.setEmail("todos@hotm");
//		u12.setTelefono("55113");
//		u12.setFechanacimiento(new Date("11/12/1993"));
//		u12.setContrasenia("dfsfd");
//		
//		usuarioDAO.Guardar(u1);
//		usuarioDAO.Guardar(u2);
//		usuarioDAO.Guardar(u3);
//		usuarioDAO.Guardar(u4);
//		usuarioDAO.Guardar(u5);
//		usuarioDAO.Guardar(u6);
//		usuarioDAO.Guardar(u7);
//		usuarioDAO.Guardar(u8);
//		usuarioDAO.Guardar(u9);
//		usuarioDAO.Guardar(u10);
//		usuarioDAO.Guardar(u11);
//		usuarioDAO.Guardar(u12);

//		Votos vo1 = new Votos();
//		Usuario u1= usuarioDAO.Leer(1);
//		vo1.setUsuario(u1);
//		Vestimenta v1 =VDAO.Leer(2);
//		vo1.setVestimenta(v1);
//		vo1.setVoto(4);
//
//		Votos vo2 = new Votos();
//		Usuario u2= usuarioDAO.Leer(1);
//		vo2.setUsuario(u2);
//		Vestimenta v2 =VDAO.Leer(3);
//		vo2.setVestimenta(v2);
//		vo2.setVoto(5);
//		
//		Votos vo3 = new Votos();
//		Usuario u3= usuarioDAO.Leer(1);
//		vo3.setUsuario(u3);
//		Vestimenta v3 =VDAO.Leer(6);
//		vo3.setVestimenta(v3);
//		vo3.setVoto(4);
//		
//		Votos vo4 = new Votos();
//		Usuario u4= usuarioDAO.Leer(1);
//		vo4.setUsuario(u4);
//		Vestimenta v4 =VDAO.Leer(9);
//		vo4.setVestimenta(v4);
//		vo4.setVoto(1);
//		
//		Votos vo5 = new Votos();
//		Usuario u5= usuarioDAO.Leer(1);
//		vo5.setUsuario(u5);
//		Vestimenta v5 =VDAO.Leer(11);
//		vo5.setVestimenta(v5);
//		vo5.setVoto(4);
//		
//		Votos vo6 = new Votos();
//		Usuario u6= usuarioDAO.Leer(2);
//		vo6.setUsuario(u6);
//		Vestimenta v6 =VDAO.Leer(1);
//		vo6.setVestimenta(v6);
//		vo6.setVoto(5);
//		
//		Votos vo7 = new Votos();
//		Usuario u7= usuarioDAO.Leer(2);
//		vo7.setUsuario(u7);
//		Vestimenta v7 =VDAO.Leer(2);
//		vo7.setVestimenta(v7);
//		vo7.setVoto(5);
//		
//		Votos vo8 = new Votos();
//		Usuario u8= usuarioDAO.Leer(2);
//		vo8.setUsuario(u8);
//		Vestimenta v8 =VDAO.Leer(3);
//		vo8.setVestimenta(v8);
//		vo8.setVoto(5);
//		
//		Votos vo9 = new Votos();
//		Usuario u9= usuarioDAO.Leer(2);
//		vo9.setUsuario(u9);
//		Vestimenta v9 =VDAO.Leer(5);
//		vo9.setVestimenta(v9);
//		vo9.setVoto(5);
//		
//		Votos vo10 = new Votos();
//		Usuario u10= usuarioDAO.Leer(2);
//		vo10.setUsuario(u10);
//		Vestimenta v10 =VDAO.Leer(8);
//		vo10.setVestimenta(v10);
//		vo10.setVoto(1);
//		
//		Votos vo11 = new Votos();
//		Usuario u11= usuarioDAO.Leer(2);
//		vo11.setUsuario(u11);
//		Vestimenta v11 =VDAO.Leer(12);
//		vo11.setVestimenta(v11);
//		vo11.setVoto(4);
//		
//		Votos vo12 = new Votos();
//		Usuario u12= usuarioDAO.Leer(3);
//		vo12.setUsuario(u12);
//		Vestimenta v12 =VDAO.Leer(1);
//		vo12.setVestimenta(v12);
//		vo12.setVoto(4);
//		
//		Votos vo13 = new Votos();
//		Usuario u13= usuarioDAO.Leer(3);
//		vo13.setUsuario(u13);
//		Vestimenta v13 =VDAO.Leer(2);
//		vo13.setVestimenta(v13);
//		vo13.setVoto(4);
//		
//		Votos vo14 = new Votos();
//		Usuario u14= usuarioDAO.Leer(3);
//		vo14.setUsuario(u14);
//		Vestimenta v14 =VDAO.Leer(3);
//		vo14.setVestimenta(v14);
//		vo14.setVoto(5);
//		
//		Votos vo15 = new Votos();
//		Usuario u15= usuarioDAO.Leer(4);
//		vo15.setUsuario(u15);
//		Vestimenta v15 =VDAO.Leer(2);
//		vo15.setVestimenta(v15);
//		vo15.setVoto(1);
//		
//		Votos vo16 = new Votos();
//		Usuario u16= usuarioDAO.Leer(4);
//		vo16.setUsuario(u16);
//		Vestimenta v16 =VDAO.Leer(4);
//		vo16.setVestimenta(v16);
//		vo16.setVoto(1);
//		
//		Votos vo17 = new Votos();
//		Usuario u17= usuarioDAO.Leer(4);
//		vo17.setUsuario(u17);
//		Vestimenta v17 =VDAO.Leer(5);
//		vo17.setVestimenta(v17);
//		vo17.setVoto(1);
//		
//		Votos vo18 = new Votos();
//		Usuario u18= usuarioDAO.Leer(4);
//		vo18.setUsuario(u18);
//		Vestimenta v18 =VDAO.Leer(6);
//		vo18.setVestimenta(v18);
//		vo18.setVoto(1);
//		
//		Votos vo19 = new Votos();
//		Usuario u19= usuarioDAO.Leer(4);
//		vo19.setUsuario(u19);
//		Vestimenta v19 =VDAO.Leer(8);
//		vo19.setVestimenta(v19);
//		vo19.setVoto(1);
//		
//		Votos vo20 = new Votos();
//		Usuario u20= usuarioDAO.Leer(4);
//		vo20.setUsuario(u20);
//		Vestimenta v20 =VDAO.Leer(11);
//		vo20.setVestimenta(v20);
//		vo20.setVoto(5);

	}
	
}
