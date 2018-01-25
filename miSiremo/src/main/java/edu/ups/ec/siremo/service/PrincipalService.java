package edu.ups.ec.siremo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import edu.ups.ec.siremo.algoritmo.Recomendacion;
import edu.ups.ec.siremo.dao.PrediccionesDao;
import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.dao.VestimentaDao;
import edu.ups.ec.siremo.dao.VotosDao;
import edu.ups.ec.siremo.modelo.Empresa;
import edu.ups.ec.siremo.modelo.Marca;
import edu.ups.ec.siremo.modelo.PrediccionesFCM;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.modelo.Vestimenta;
import edu.ups.ec.siremo.modelo.Votos;

@Path("/principal")
public class PrincipalService {

	private List<Vestimenta> vestimentasRecomen;
	private List<Vestimenta> vestimentasEstilo;
	private List<Vestimenta> vestimentasMeGustaron;
	
	private List<Votos> listVotos;
	private Usuario usuario;
	
	
	@Inject
	private VestimentaDao vestimentaDAO;
	
	@Inject
	private PrediccionesDao prediccionesDAO;
	
	@Inject
	private VotosDao votosDAO;
	
	@Inject
	private UsuarioDao usuarioDAO;	
	
	@GET
	@Path("/recomendacion")
	@Produces("application/json")
	public List<Vestimenta> vestimentasReco(@QueryParam("idUsuario") int idUsuario) {
		
		List<PrediccionesFCM> predicciones = prediccionesDAO.listPrediccionesUsua(idUsuario);
//		System.out.println("mmmm "+listaPrediccionesUsuario.size());
		
//		for (int i = 0; i < listaPrediccionesUsuario.size(); i++) {
//			System.out.print("|"+listaPrediccionesUsuario.get(i).getItem()+" "+listaPrediccionesUsuario.get(i).getPrediccion());
//		}
		
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
						ves.setRaiting("1");
					}else if(rat>=1.50 && rat<=2.49) {
						ves.setRaiting("2");
					}else if(rat>=2.50 && rat<=3.49) {
						ves.setRaiting("3");
					}else if(rat>=3.50 && rat<=4.49) {
						ves.setRaiting("4");
					}else if(rat>=4.50) {
						ves.setRaiting("5");
					}
					
					ves.setLikes(totalLikes);
					
					auxVestimentas.add(ves);
				}
		
	    	}
		}
	    
	    
	    if(auxVestimentas.size()==0) {
	    	vestimentasAzar();
	    }else {
	    	vestimentasRecomen = auxVestimentas;
	    }
	    System.out.println("este es el tamano222 ="+vestimentasRecomen.size());
	    
	    for (int i = 0; i < vestimentasRecomen.size(); i++) {
	    	System.out.println("la vesti ="+vestimentasRecomen.get(i).getDescripcion());
				vestimentasRecomen.get(i).getEmpresa().setVestimentas(null);
			}
		    

	    return vestimentasRecomen;
	}
	
	
	private void vestimentasAzar() {
		
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
						ves.setRaiting("1");
					}else if(rat>=1.50 && rat<=2.49) {
						ves.setRaiting("2");
					}else if(rat>=2.50 && rat<=3.49) {
						ves.setRaiting("3");
					}else if(rat>=3.50 && rat<=4.49) {
						ves.setRaiting("4");
					}else if(rat>=4.50) {
						ves.setRaiting("5");
					}
					
					ves.setLikes(totalLikes);
					
					auxVestimentas.add(ves);
				}
	    vestimentasRecomen = auxVestimentas;
		
	    
	}
	
	@GET
	@Path("/recomendacionEstilo")
	@Produces("application/json")
	public List<Vestimenta> vestimentasEstilo(@QueryParam("idUsuario") int idUsuario) {		
		
		vestimentasEstilo = new ArrayList<>();
		System.out.println("este es el id ="+idUsuario);
		
		loadUsuario(idUsuario);
		
		vestimentasRecomen = vestimentasReco(idUsuario);
		
		for (int i = 0; i < vestimentasRecomen.size(); i++){
			Vestimenta ves = vestimentasRecomen.get(i);
			
			if(ves.getEstilo().equals(usuario.getEstilo())){
				vestimentasEstilo.add(ves);
			}
			
		}
	return vestimentasEstilo;	
	}
	
	public void loadUsuario(int idUsuario) {
		
		usuario = usuarioDAO.Leer(idUsuario);
		
	}
	
	@GET
	@Path("/meGustaron")
	@Produces("application/json")
	public List<Vestimenta> vestimentasGustaron(@QueryParam("idUsuario") int idUsuario) {
		

		vestimentasMeGustaron = new ArrayList<>();
		
		loadUsuario(idUsuario);
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
					ves.setRaiting("1");
				}else if(rat>=1.50 && rat<=2.49) {
					ves.setRaiting("2");
				}else if(rat>=2.50 && rat<=3.49) {
					ves.setRaiting("3");
				}else if(rat>=3.50 && rat<=4.49) {
					ves.setRaiting("4");
				}else if(rat>=4.50) {
					ves.setRaiting("5");
				}
				
				ves.setLikes(totalLikes);
				
				vestimentasMeGustaron.add(ves);    
			}
		
		    for (int i = 0; i < vestimentasMeGustaron.size(); i++) {
		    	vestimentasMeGustaron.get(i).getEmpresa().setVestimentas(null);
				}
		    
		return vestimentasMeGustaron;
	}
	
}
