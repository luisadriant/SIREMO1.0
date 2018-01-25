package edu.ups.ec.siremo.controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import edu.ups.ec.siremo.algoritmo.FuzzyCMeans;
import edu.ups.ec.siremo.algoritmo.Recomendacion;
import edu.ups.ec.siremo.dao.EmpresaDao;
import edu.ups.ec.siremo.dao.MarcaDao;
import edu.ups.ec.siremo.dao.PrediccionesDao;
import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.dao.VestimentaDao;
import edu.ups.ec.siremo.dao.VotosDao;
import edu.ups.ec.siremo.modelo.Empresa;
import edu.ups.ec.siremo.modelo.Marca;
import edu.ups.ec.siremo.modelo.Persona;
import edu.ups.ec.siremo.modelo.PrediccionesFCM;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.modelo.Vestimenta;
import edu.ups.ec.siremo.modelo.Votos;
import edu.ups.ec.siremo.util.ErrorsController;

/**
 * Esta clase sirve para enlazar la vista del archivo xhtml con el controlador de la pagi recomendaciones 
 * @author Boris
 *
 */
@ManagedBean
@ViewScoped
public class PrincipalControlador {

	// Instanciamos la clase que controla los errores con su respectivo inject.
	ErrorsController error = new ErrorsController();
	@Inject
	private FacesContext facesContext;
	
	//variables necesarias
	private List<Vestimenta> vestimentasRecomen;
	private List<Vestimenta> vestimentasEstilo;
	private List<Vestimenta> vestimentasMeGustaron;
	
	private int idUsuario;
	private Usuario usuario;
	private List<Votos> listVotos;
	
//	private Vestimenta ves;
	//instanciamos en objeto de acceso a datos para poder injectar los metodos crud correspondiente al Vestimenta
	@Inject
	private VestimentaDao vestimentaDAO;

	@Inject
	private PrediccionesDao prediccionesDAO;
	
	@Inject
	private VotosDao votosDAO;
	
	@Inject
	private UsuarioDao usuarioDao;
	




	
	//este metodo se inicia cada vez que se construye la clase
	@PostConstruct
	public void init() {
		
		vestimentasRecomen = new ArrayList<>();
		vestimentasEstilo = new ArrayList<>();
		vestimentasMeGustaron = new ArrayList<>();
		
		listVotos = new ArrayList<>();
	}

	
	public List<Vestimenta> getVestimentasRecomen() {
		return vestimentasRecomen;
	}

	public void setVestimentasRecomen(List<Vestimenta> vestimentasRecomen) {
		this.vestimentasRecomen = vestimentasRecomen;
	}

	public List<Vestimenta> getVestimentasEstilo() {
		return vestimentasEstilo;
	}

	public void setVestimentasEstilo(List<Vestimenta> vestimentasEstilo) {
		this.vestimentasEstilo = vestimentasEstilo;
	}

	public List<Vestimenta> getVestimentasMeGustaron() {
		return vestimentasMeGustaron;
	}

	public void setVestimentasMeGustaron(List<Vestimenta> vestimentasMeGustaron) {
		this.vestimentasMeGustaron = vestimentasMeGustaron;
	}

	public int getIdUsuario() {
		System.out.println("el id que retorna de GET="+idUsuario);
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		System.out.println("el id que llega al SET recomendacion="+idUsuario);
		loadUsuario(idUsuario);
		
		List<PrediccionesFCM> listaPrediccionesUsuario = prediccionesDAO.listPrediccionesUsua(idUsuario);
		System.out.println("mmmm "+listaPrediccionesUsuario.size());
		
		for (int i = 0; i < listaPrediccionesUsuario.size(); i++) {
			System.out.print("|"+listaPrediccionesUsuario.get(i).getItem()+" "+listaPrediccionesUsuario.get(i).getPrediccion());
		}
		
		
//		int ultimoIDtabla=prediccionesDAO.listadoPredicciones().size();
//		
//		PrediccionesFCM pre1 =prediccionesDAO.Leer(ultimoIDtabla);
//		
//		int predicciones[][]=pre1.getPredicciones();
		
//		System.out.println("El tamaño es= "+predicciones.length+" "+predicciones[0].length+" el ultimoID="+ultimoIDtabla);
		
		if(listaPrediccionesUsuario.size()==0) { //si el usuario es nuevo se le muestra ropa al azar
			vestimentasAzar();
		}else { //si el usuario ya realizo votaciones le recomienda ropa
		recomendacionUsuario(listaPrediccionesUsuario);
		}
		
		vestimentasEstilo(idUsuario);
		vestimentasGustaron(idUsuario);
		
		
		this.idUsuario = idUsuario;
	}
	
	private void vestimentasEstilo(int idUsuario) {
		
		vestimentasEstilo = new ArrayList<>();
		
		for (int i = 0; i < vestimentasRecomen.size(); i++) {
			Vestimenta ves = vestimentasRecomen.get(i);
			
			if(ves.getEstilo().equals(usuario.getEstilo())) {
				vestimentasEstilo.add(ves);
			}
			
		}
		
				
	}
	private void vestimentasGustaron(int idUsuario) {
		
		
		vestimentasMeGustaron = new ArrayList<>();
		
		List<Votos> votos= votosDAO.meGustaron(usuario);
		List<Votos> listVotos= votosDAO.listadoVotos();

		
		//el bucle va de mayor a menor para sacar en primer lugar la ultima preda que le gusto al usuario.
			for (int i = votos.size()-1; i >=0 ; i--) {
				Vestimenta ves = votos.get(i).getVestimenta();
						
				int sumVotacion=0;
				int totalVotos=0;
				int totalLikes=0;
				for (int k = 0; k < listVotos.size(); k++) {
					
					
					if(listVotos.get(k).getVestimenta().getId()==ves.getId()) {
						System.out.println("listVotoGET="+listVotos.get(k).getId()+" vesID="+ves.getId()+" voto="+listVotos.get(k).getVoto());
						sumVotacion = sumVotacion+listVotos.get(k).getVoto();
						totalVotos++;
						if(listVotos.get(k).getVoto()==5) {
							totalLikes++;
						}
					}
				}
				
				double rat=0.00;
				
				if(totalVotos!=0) {
				rat=Double.parseDouble(""+sumVotacion)/Double.parseDouble(""+totalVotos);
				
				}
				
				System.out.println("EL ratingggg "+rat);
				
				if(rat<=1.49) {
					ves.setRaiting("images/star1.png");
				}else if(rat>=1.50 && rat<=2.49) {
					ves.setRaiting("images/star2.png");
				}else if(rat>=2.50 && rat<=3.49) {
					ves.setRaiting("images/star3.png");
				}else if(rat>=3.50 && rat<=4.49) {
					ves.setRaiting("images/star4.png");
				}else if(rat>=4.50) {
					ves.setRaiting("images/star5.png");
				}
				
				ves.setLikes(totalLikes);
				
				vestimentasMeGustaron.add(ves);    
			}
	}
	
	private void loadUsuario(int id) {
		usuario = usuarioDao.Leer(id);
	}
	
	private void vestimentasAzar() {
		vestimentasRecomen = vestimentaDAO.listadovestimentas();
		
		List<Vestimenta> auxVestimentas=new ArrayList();
		
		listVotos = votosDAO.listadoVotos();
		
	    for (int i = 0; i < vestimentasRecomen.size(); i++) {

					Vestimenta ves = vestimentasRecomen.get(i);
					//realizamos el calculo del rating y le asiganmos una imagen
					int sumVotacion=0;
					int totalVotos=0;
					int totalLikes=0;
					for (int k = 0; k < listVotos.size(); k++) {
						
						
						if(listVotos.get(k).getVestimenta().getId()==ves.getId()) {
							System.out.println("listVotoGET="+listVotos.get(k).getId()+" vesID="+ves.getId()+" voto="+listVotos.get(k).getVoto());
							sumVotacion = sumVotacion+listVotos.get(k).getVoto();
							totalVotos++;
							if(listVotos.get(k).getVoto()==5) {
								totalLikes++;
							}
						}
					}
					
					double rat=0.00;
					
					if(totalVotos!=0) {
					rat=Double.parseDouble(""+sumVotacion)/Double.parseDouble(""+totalVotos);
					
					}
					
					System.out.println("EL ratingggg "+rat);
					
					if(rat<=1.49) {
						ves.setRaiting("images/star1.png");
					}else if(rat>=1.50 && rat<=2.49) {
						ves.setRaiting("images/star2.png");
					}else if(rat>=2.50 && rat<=3.49) {
						ves.setRaiting("images/star3.png");
					}else if(rat>=3.50 && rat<=4.49) {
						ves.setRaiting("images/star4.png");
					}else if(rat>=4.50) {
						ves.setRaiting("images/star5.png");
					}
					
					ves.setLikes(totalLikes);
					
					auxVestimentas.add(ves);
				}
	    vestimentasRecomen = auxVestimentas;
		
	}
	

	
	private void recomendacionUsuario(List<PrediccionesFCM> predicciones) {
		
		List<Recomendacion> listPredicciones = new ArrayList();

		System.out.println("entro a metodo "+predicciones.size());
	    for (int item = 0; item < predicciones.size(); item++){
	    	
//	    	if(predicciones[numUsuario][item] !=0) {
//	    		int prediccion=predicciones[numUsuario][item];
	    		Recomendacion rec=new Recomendacion(predicciones.get(item).getItem(), predicciones.get(item).getPrediccion());
	    		
	    		listPredicciones.add(rec);
//	    	}

	    }
	    //obtiene los numeros mayores de la lista en base al voto de la prediicon
		Collections.sort(listPredicciones, new Comparator<Recomendacion>() {
			@Override
			public int compare(Recomendacion pre1, Recomendacion pre2) {			
				return new Integer(pre2.getPrediccion()).compareTo(new Integer(pre1.getPrediccion()));
			}
		});
	    
		vestimentasRecomen = vestimentaDAO.listadovestimentas();
		List<Vestimenta> auxVestimentas=new ArrayList();
		
		listVotos = votosDAO.listadoVotos();
		
		System.out.println("elll taaa "+listPredicciones.size()+" total vest "+vestimentasRecomen.size());
		
	    for (int i = 0; i < listPredicciones.size(); i++) {
	    	
	    	Recomendacion rec = listPredicciones.get(i);
	    	
	    	
	    	for (int j = 0; j < vestimentasRecomen.size(); j++) {
				
				Vestimenta ves= vestimentasRecomen.get(j);
				
//			System.out.println("h = "+ves.getId()+" k = "+(rec.getItem()));
				if(ves.getId()==(rec.getItem())){
					
					//realizamos el calculo del rating y le asiganmos una imagen
//					int sumVotacion=0;
//					int totalVotos=0;
					int totalLikes=0;
					for (int k = 0; k < listVotos.size(); k++) {
						
						
						if(listVotos.get(k).getVestimenta().getId()==ves.getId()) {
							System.out.println("listVotoGET="+listVotos.get(k).getId()+" vesID="+ves.getId()+" voto="+listVotos.get(k).getVoto());
//							sumVotacion = sumVotacion+listVotos.get(k).getVoto();
//							totalVotos++;
							if(listVotos.get(k).getVoto()==5) {
								totalLikes++;
							}
						}
					}
//					double rat=0.00;
					
//					if(totalVotos!=0) {
//					rat=Double.parseDouble(""+sumVotacion)/Double.parseDouble(""+totalVotos);
					
//					}
					
					int rat = listPredicciones.get(i).getPrediccion();
					System.out.println("EL ratingggg "+rat);
					
					if(rat==1) {
						ves.setRaiting("images/star1.png");
					}else if(rat==2) {
						ves.setRaiting("images/star2.png");
					}else if(rat==3) {
						ves.setRaiting("images/star3.png");
					}else if(rat==4) {
						ves.setRaiting("images/star4.png");
					}else if(rat==5) {
						ves.setRaiting("images/star5.png");
					}
					
					ves.setLikes(totalLikes);
					
					auxVestimentas.add(ves);
				}
		
	    	}
		}
	    vestimentasRecomen = auxVestimentas;
		
		
//		for (int i = 0; i < auxVestimentas.size(); i++) {
//			System.out.println("la vesti recom ="+vestimentasRecomen.get(i).getDescripcion());
//		}

	}
	
}
