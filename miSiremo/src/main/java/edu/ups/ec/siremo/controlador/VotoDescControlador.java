package edu.ups.ec.siremo.controlador;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;

import javax.inject.Inject;

import org.primefaces.event.RateEvent;

import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.dao.VestimentaDao;
import edu.ups.ec.siremo.dao.VotosDao;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.modelo.Vestimenta;
import edu.ups.ec.siremo.modelo.Votos;
import edu.ups.ec.siremo.util.ErrorsController;

/**
 * Esta clase sirve para enlazar la vista del archivo xhtml con el controlador de la vestimenta 
 * @author root
 *
 */
@ManagedBean
@ViewScoped
public class VotoDescControlador {
	

	// Instanciamos la clase que controla los errores con su respectivo inject.
	ErrorsController error = new ErrorsController();
	@Inject
	private FacesContext facesContext;	
	
	//instancia de la entidad de negocio Vestimenta
	
	private Usuario usuario;

	private Vestimenta vestimenta;
	//variables necesarias
	
	private int voto;
	private int idVestimenta;
	private int idUsuario;
	private double rating;
	private DecimalFormat formatoDecimal = new DecimalFormat("#.00");
	
	//instanciamos en objeto de acceso a datos para poder injectar los metodos crud correspondiente al Vestimenta
	@Inject
	private VotosDao votosDao;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private VestimentaDao vestimentaDao;
	


    public void onrate(RateEvent rateEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Votacion", "Tu votacion fué:" + ((Integer) rateEvent.getRating()).intValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	//este metodo se inicia cada vez que se construye la clase
		@PostConstruct
	public void init() {
		vestimenta=new Vestimenta();
		usuario=new Usuario();
		System.out.println("Se iniciooo este bean");
		//loadVestimentas();
	}
		
	public Vestimenta getVestimenta() {
		return vestimenta;
	}

	public void setVestimenta(Vestimenta vestimenta) {
		this.vestimenta = vestimenta;
	}


	
	public int getIdVestimenta() {
		System.out.println("entro gET vesti "+idVestimenta);
		return idVestimenta;
	}

	public void setIdVestimenta(int idVestimenta) {
		
		System.out.println("entro SET vesti "+idVestimenta);
		loadVestimenta(idVestimenta);
		tuVotacionVesti(idVestimenta);
		ratingVestimenta();
		
		this.idVestimenta = idVestimenta;
	}

	public int getIdUsuario() {
		
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		loadUsuario(idUsuario);
		this.idUsuario = idUsuario;
	}

	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	//este metodo nos sirve para cargar la pagina y editar los datos de Vestimentas
	public void loadVestimenta(int id) {
		vestimenta = vestimentaDao.Leer(id);
	}
	
	public void loadUsuario(int id) {
		usuario = usuarioDao.Leer(id);
	}


	public int getVoto() {
		System.out.println("entro a GET voto="+voto);
		return voto;
	}

	public void setVoto(int voto) {
		Votos vot = new Votos();
		vot.setUsuario(usuario);
		vot.setVestimenta(vestimenta);
		vot.setVoto(voto);
		
		votosDao.Guardar(vot);
		
		this.voto = voto;
	}
	public void tuVotacionVesti(int idVestimenta) {
		
		Votos vot = votosDao.tuVotacion(usuario,vestimenta);
		
		if(vot != null) {
			voto = vot.getVoto();
		}else {
			voto=0;
		}
				
	}
	
	public void ratingVestimenta() {
	
		List<Votos> listVotos=votosDao.listadoVotos();
		
		int sumVotacion=0;
		int totalVotos=0;
		
		for (int i = 0; i < listVotos.size(); i++) {
			
			if(listVotos.get(i).getVestimenta().getId()==vestimenta.getId()) {
			System.out.println("listVotoGET="+listVotos.get(i).getId()+" vesID="+vestimenta.getId()+" voto="+listVotos.get(i).getVoto());
			sumVotacion = sumVotacion+listVotos.get(i).getVoto();
			totalVotos++;
		}
	}
	
	if(totalVotos!=0) {
	
		String ra= formatoDecimal.format(Double.parseDouble(""+sumVotacion)/Double.parseDouble(""+totalVotos));
		
		rating= Double.parseDouble(ra.replaceAll(",", "."));
		
	}else {
		rating =0;
	}
	
	System.out.println("El rating de esta vestimenta es"+rating);
	
	}
	
	
}
