package edu.ups.ec.siremo.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import edu.ups.ec.siremo.dao.UsuarioDao;
import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.util.ErrorsController;



/**
 * Esta clase sirve para enlazar la vista del archivo xhtml con el controlador del Test 
 * @author root
 */
@ManagedBean
@RequestScoped
public class TestControlador {

	// Instanciamos la clase que controla los errores con su respectivo inject.
    ErrorsController error = new ErrorsController();
	@Inject
    private FacesContext facesContext;
		
	private String respuesta1="";
	private String respuesta2="";
	private String respuesta3="";
	private String respuesta4="";
	private String respuesta5="";
	private String respuesta6="";
	private String respuesta7="";
	private String respuesta8="";
	private String respuesta9="";
	private String respuesta10="";
	private String respuesta11="";
	private String respuesta12="";
	private String respuesta13="";
	
	private List respuestas;
	private int idUsuario;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	
	@PostConstruct
	public void init() {
		respuestas = new ArrayList();
	}

	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		
		this.idUsuario = idUsuario;
	}
	

	public String getRespuesta1() {
		System.out.println("entro GET res 1 ="+respuesta1);
		return respuesta1;
	}


	public void setRespuesta1(String respuesta1) {
		System.out.println("entro SET res 1 ="+respuesta1);
		if(respuesta1!=null) {
		this.respuesta1 = respuesta1;
		respuestas.add(respuesta1);
		}
	}


	public String getRespuesta2() {
		System.out.println("entro GET res 2");
		return respuesta2;
	}


	public void setRespuesta2(String respuesta2) {
		System.out.println("entro SET res 2");
		if(respuesta2!=null) {
		this.respuesta2 = respuesta2;
		respuestas.add(respuesta2);
		}
	}


	public String getRespuesta3() {
		System.out.println("entro GET res 3 ="+respuesta3);
		return respuesta3;
	}


	public void setRespuesta3(String respuesta3) {
		System.out.println("entro SET res 3 ="+respuesta3);
		if(respuesta3!=null) {
		this.respuesta3 = respuesta3;
		respuestas.add(respuesta3);
		}
	}


	public String getRespuesta4() {
		System.out.println("entro GET res 4 ="+respuesta4);
		return respuesta4;
	}


	public void setRespuesta4(String respuesta4) {
		System.out.println("entro SET res 5 ="+respuesta5);
		if(respuesta4!=null) {
		this.respuesta4 = respuesta4;
		respuestas.add(respuesta4);
		}
	}


	public String getRespuesta5() {
		return respuesta5;
	}


	public void setRespuesta5(String respuesta5) {
		if(respuesta5!=null) {
		this.respuesta5 = respuesta5;
		respuestas.add(respuesta5);
		}
	}


	public String getRespuesta6() {
		return respuesta6;
	}


	public void setRespuesta6(String respuesta6) {
		if(respuesta6!=null) {
		this.respuesta6 = respuesta6;
		respuestas.add(respuesta6);
		}
	}


	public String getRespuesta7() {
		return respuesta7;
	}


	public void setRespuesta7(String respuesta7) {
		if(respuesta7!=null) {
		this.respuesta7 = respuesta7;
		respuestas.add(respuesta7);
		}
	}


	public String getRespuesta8() {
		return respuesta8;
	}


	public void setRespuesta8(String respuesta8) {
		if(respuesta8!=null) {
		this.respuesta8 = respuesta8;
		respuestas.add(respuesta8);
		}
	}


	public String getRespuesta9() {
		return respuesta9;
	}


	public void setRespuesta9(String respuesta9) {
		if(respuesta9!=null) {
		this.respuesta9 = respuesta9;
		respuestas.add(respuesta9);
		}
	}


	public String getRespuesta10() {
		return respuesta10;
	}


	public void setRespuesta10(String respuesta10) {
		if(respuesta10!=null) {
		this.respuesta10 = respuesta10;
		respuestas.add(respuesta10);
		}
	}


	public String getRespuesta11() {
		return respuesta11;
	}


	public void setRespuesta11(String respuesta11) {
		if(respuesta11!=null) {
		this.respuesta11 = respuesta11;
		respuestas.add(respuesta11);
		}
		}


	public String getRespuesta12() {
		return respuesta12;
	}


	public void setRespuesta12(String respuesta12) {
		System.out.println("entro SET res 12 ="+respuesta12);
		if(respuesta12!=null) {
		this.respuesta12 = respuesta12;
		respuestas.add(respuesta12);
		}
	}


	public String getRespuesta13() {
		System.out.println("entro GET res 13 ="+respuesta13);
		return respuesta13;
	}


	public void setRespuesta13(String respuesta13) {
		
		if(respuesta13!=null) {
		System.out.println("entro SET res 13 ="+respuesta13);
		this.respuesta13 = respuesta13;
		respuestas.add(respuesta13);
		}
		
	}

	public void calcularEstilo() {
		
		boolean resValidacion = validarRespuestasContes();
		System.out.println(" Constea todas las preguntas ? ="+resValidacion);
		
		//si contesta todas las preguntas del test envia ha sacar el resultado del estilo 
		if(resValidacion) {
			sacarEstiloProlog();
		
		
			try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    externalContext.redirect("principal_face.xhtml?idUsuario="+idUsuario);
			
			}catch (Exception e) {
			String errorMessage = error.getRootErrorMessage(e);
		    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
		    facesContext.addMessage(null, m);
		    
			}
		}
	    
	}
	
	public boolean validarRespuestasContes() {
		
//		if(!respuesta1.equals("")&&!respuesta2.equals("")&&!respuesta3.equals("")&&!respuesta4.equals("")&&
//				!respuesta5.equals("")&&!respuesta6.equals("")&&!respuesta7.equals("")&&!respuesta8.equals("")&&
//				!respuesta9.equals("")&&!respuesta10.equals("")&&!respuesta11.equals("")&&!respuesta12.equals("")&&
//				!respuesta13.equals("")) {
		if(respuestas.size()==13) {
			return true;
		}else {
			return false;
		}
	}
	
public void sacarEstiloProlog() {
		
//		Query q = new Query("consult('basedeconocimiento.pl')");
//		if(q.hasSolution()) {
//			System.out.println("Cargada");
//		}else {
//			System.out.println("Error, no se pudo cargar la base.");
//		}
		
		Usuario usuario = usuarioDao.Leer(idUsuario);
	
		System.out.println("Easeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee "+usuario.getNombres());
		
		String aux="0,0,0";
		int rebelde=0;
		int vanidoso=0;
		int pulcro=0;
		for (int cont=0; cont< 13; cont++) {
			
			if(respuestas.get(cont).equals("1")) {
				rebelde++;
			}else if(respuestas.get(cont).equals("2")) {
				vanidoso++;
			}if(respuestas.get(cont).equals("3")) {
				pulcro++;
			} 
		}
		
		if(rebelde>vanidoso && vanidoso>pulcro) {
			usuario.setEstilo("Rocker");
		}else if (rebelde>pulcro && pulcro > vanidoso) {
			usuario.setEstilo("Skater");
		}else if (pulcro>rebelde && rebelde>vanidoso) {
			usuario.setEstilo("Preppy");
		}else if (pulcro>vanidoso && vanidoso>rebelde) {
			usuario.setEstilo("Casual");
		}else if (vanidoso>rebelde && rebelde>pulcro) {
			usuario.setEstilo("Elegante");
		}else if (vanidoso>pulcro && pulcro>rebelde) {
			usuario.setEstilo("Trendy");
		}else if (rebelde==vanidoso && rebelde>pulcro) {
			usuario.setEstilo("Skater");
		}else if (rebelde==vanidoso && rebelde<pulcro) {
			usuario.setEstilo("Sin Estilo");
		}else if (rebelde==pulcro && rebelde>vanidoso) {
			usuario.setEstilo("Sin Estilo");
		}else if (rebelde==pulcro && rebelde<vanidoso) {
			usuario.setEstilo("Rocker");
		}
		
		usuarioDao.Actualizar(usuario);
		
		
}
	
}
