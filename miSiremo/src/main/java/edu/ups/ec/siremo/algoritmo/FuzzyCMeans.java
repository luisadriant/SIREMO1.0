package edu.ups.ec.siremo.algoritmo;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

import edu.ups.ec.siremo.modelo.Usuario;
import edu.ups.ec.siremo.modelo.Vestimenta;
import edu.ups.ec.siremo.modelo.Votos;


public class FuzzyCMeans {

	        private double centroides[][];
	        private int centroidesVecinosporUsuario[][];
	        private double pesoscentroidesVecinosporUsuario[][];

	        private int k = 0;
	        private int usuarios = 0;
	        private int items = 0;

	        private int votosTrain[][];

	        private int iteraciones;
	        
	        private List<Hashtable> ListaVotosUsuarios;
	        private List<Hashtable> ListaPredicciones;
	        private int totalTrain;
	        private double R;
	        private boolean conVacios;
	        private double ERROR;
	        Random aleatorio = new Random();
	        
//	        private int predicciones[][];
	        
	        int l;//num centroides más cercanos a tomar en cuenta para realizar la predicción
	        List<Votos> listVotos;
	        
	        public FuzzyCMeans(List<Votos> listVotos, List<Usuario> listUsuarios, List<Vestimenta> listVestimentas){
	            //Algoritmo de Sistema de Recomendacion
	        	this.listVotos=listVotos;

	        	
	            //1. Entrada de Datos y parámetros

	            //indica si el conjunto de datos tiene vacios o esta completo. Si convacios es false, los huecos se tomaran como ceros.
	            conVacios = false;
	            k=3;
	            iteraciones = 100;
	            ERROR = 0.001;
	            R = 4.0;
	            m = 2.0;
	            minVoto = 1.0;
	            maxVoto = 5.0;
	            
	            System.out.println("el tamaño de los items = "+listVestimentas.size()+" usuario="+listUsuarios.size());

	            creaM();
	            items = listVestimentas.size();
	            usuarios = votosTrain[totalTrain-1][0]+1; //modificar de acuerdo al dataset a utilizar
	            
	            iniciarListas();
	            
	            //2. Clustering de usuarios
	            SoftClustering();

	            //3. Cálculo de Predicciones y medición MAE
	            System.out.println("determinar centroides vecinos");
	                       
	            l=k;
	            determinarCentroidesVecinosFCMeans();
	        }
	        
	        
	        public void creaM(){

	                totalTrain = listVotos.size();
	                
	                votosTrain = new int[totalTrain][3];//total de datos en dataset * 3 campos (usuario, item, voto) 
	                String fila;
	                int cont = 0;

	                System.out.println("total tamano="+listVotos.size());
	                	for (int i = 0; i < listVotos.size(); i++) {
	                		
	                		int usu = listVotos.get(i).getUsuario().getId();
		                    int item = listVotos.get(i).getVestimenta().getId();
	                		int voto = listVotos.get(i).getVoto();
	                    
	                    votosTrain[cont][0] = usu - 1;
	                    votosTrain[cont][1] = item - 1;
	                    votosTrain[cont][2] = voto;
	                    cont++;
	                }
	        }

	        public void iniciarListas() {
	            ListaVotosUsuarios = new ArrayList<Hashtable>();
	            ListaPredicciones = new ArrayList<Hashtable>();
	            
	            Hashtable votosUsuario;
	            Hashtable predicciones;
	            System.out.println("gggggg "+votosTrain[totalTrain-1][0]);
	            for (int u = 0; u <= votosTrain[totalTrain-1][0]; u++) {
	                votosUsuario = new Hashtable();
	                predicciones = new Hashtable();
	                ListaVotosUsuarios.add(votosUsuario);
	                ListaPredicciones.add(predicciones);
	                
	            }

	            if (!conVacios){ //la matriz no tiene vacios o los vacios son tomados como ceros.
	            
	                for (int u = 0; u < usuarios; u++){
	                    for (int i = 0; i < items; i++){
//	                    	System.out.println("por aca va 2 ="+i+" items="+items);
	                        ListaVotosUsuarios.get(u).put(i, 0);
	                        ListaPredicciones.get(u).put(i,0);
	                    }
	                }
	                for (int v = 0; v < totalTrain; v++){
	                    int usuario = votosTrain[v][0];
	                    int item = votosTrain[v] [1];
	                    double voto = votosTrain[v][2];
	                    ListaVotosUsuarios.get(usuario).put(item, voto);
	                }
	                
	            }else{// indica que la matriz tiene vacios, sparse
	            	
	            	for (int u = 0; u < usuarios; u++){
	                    for (int i = 0; i < items; i++){
	                        ListaPredicciones.get(u).put(i,0);
	                    }
	                }
	            	
	                for (int v = 0; v < totalTrain; v++){
	                    int usuario = Integer.parseInt("" + votosTrain[v][0]);
	                    int item = Integer.parseInt("" + votosTrain[v][1]);
	                    double voto = votosTrain[v][2];
	                    ListaVotosUsuarios.get(usuario).put(item, voto);
	                }
	            }
	        }
	        
	        public void inicializarMatrizCentroidesconVacios(){ //reinicia a 0 todos los centroides
	        
	            centroides = new double[k][items];
	            for (int j = 0; j < k; j++){
	                for (int i = 0; i < items; i++){
	                    centroides[j][ i] = 0.0;
	                }
	            }
	            //mostrarMatriz(centroides);
	        }
	        // Fuzzy CMeans *********************************************************************************************************************
	        //***********************************************************************************************************************************
	        double[][] Yuk;
	        double m;//controla overlapping en Fuzzy cmeans
	        double JM;
	        double mMin;
	        double mMax;
	        int cMax;
	        int saltoK;
	        double minVoto;
	        double maxVoto;
	        
	        public void SoftClustering(){
	            //a. inicializar
	            iteraciones = 100;
	            int numIntentos = 0;
	            numIntentos++;
	            //b. inicializar fuzzy membership Yij
	            inicializarMatrizCentroidesconVacios();
	            inicializarRandomicamenteYuk();
	            double JMant = Double.MAX_VALUE;

	            //c. actualizar centroides y membership Yij
	            for (int it = 0; it < iteraciones; it++) { //4. Probar Convergencia con numIteraciones o con error 0.001 con JM.
	            
	                if (!conVacios) calcularCentroidesFCM();
	                else calcularCentroidesFCM2();
	                System.out.println("actualizado centroides ");
	                actualizarYuk();
	                System.out.println("actualizado YUK ");
	                JM = calcularJM();
	                //double errorJM = Math.Abs((JM - JMant) / JM);
	                double errorJM = Math.abs((JM - JMant));
	                System.out.println("it;"+(it +1) + ";JM;" + JM + ";errorJM;" + errorJM);
	                if (errorJM < ERROR) break;
	                JMant = JM;
	            }
	        }
	        
	        public void inicializarRandomicamenteYuk(){//La sumatoria de cada vector de usuarios es igual a 1.
	            Yuk = new double[usuarios][k];
	            double sumaYuk;
	            for (int u = 0; u < usuarios; u++){
	                sumaYuk = 0.0;
	                for (int j = 0; j < k; j++){
	                    Yuk[u][j] = aleatorio.nextDouble();
	                    sumaYuk = sumaYuk + Yuk[u][j];
	                }
	                for (int j = 0; j < k; j++) {
	                    Yuk[u][j] = Yuk[u][j] / sumaYuk;//se normaliza para que la sumatoria sea igual a 1.
	                }
	            }
	        }
	        
	        public void calcularCentroidesFCM(){
	            double[][] sumatoriaAuk = new double[k][items];
	            centroides = null;
	            centroides = new double[k][items];
	            for (int i = 0; i < items; i++){
	                for (int u = 0; u < usuarios; u++){
	                    if (!conVacios){
	                        double votoenItem = Double.parseDouble("" + ListaVotosUsuarios.get(u).get(i));
	                        for (int j = 0; j < k; j++){
	                            centroides[j][i] = centroides[j][i] + (Math.pow(Yuk[u][j], m) * votoenItem);
	                            sumatoriaAuk[j][i] = sumatoriaAuk[j][i] + Math.pow(Yuk[u][j], m);
	                        }
	                    }else{
	                        if (ListaVotosUsuarios.get(u).get(i)!= null){
	                            double votoenItem = Double.parseDouble("" + ListaVotosUsuarios.get(u).get(i));
	                            for (int j = 0; j < k; j++){
	                                centroides[j][i] = centroides[j][i] + (Math.pow(Yuk[u][j], m) * votoenItem);
	                                sumatoriaAuk[j][i] = sumatoriaAuk[j][i] + Math.pow(Yuk[u][j], m);
	                            }
	                        }
	                    }
	                }
	            }
	            for (int i = 0; i < items; i++){
	                for (int j = 0; j < k; j++){
	                    centroides[j][i] = centroides[j][i] / sumatoriaAuk[j][i];
	                    if (sumatoriaAuk[j][i] == 0) centroides[j][i] = 0.0;//si ningun usuario aporta entonces queda en 0.
	                }
	            }
	            sumatoriaAuk = null;
	        }
	        
	        public void calcularCentroidesFCM2(){
	            double[][] sumatoriaAuk = new double[k][items];
	            centroides = null;
	            centroides = new double[k][items];
	            //Console.WriteLine("totalTrain: "+ totalTrain);
	            for (int r = 0; r < totalTrain; r++){
	                int u = votosTrain[r][0];
	                int i = votosTrain[r][1];
	                double votoenItem = votosTrain[r][2];
	                for (int j = 0; j < k; j++){
	                    centroides[j][i] = centroides[j][i] + (Math.pow(Yuk[u][j], m) * votoenItem);
	                    sumatoriaAuk[j][i] = sumatoriaAuk[j][i] + Math.pow(Yuk[u][j], m);
	                }
	            }
	            for (int i = 0; i < items; i++){
	                for (int j = 0; j < k; j++){
	                    centroides[j][i] = centroides[j][i] / sumatoriaAuk[j][i];
	                    if (sumatoriaAuk[j][i] == 0) centroides[j][i] = 0.0;
	                }
	            }
	            sumatoriaAuk = null;
	        }
	        double[][] normas;
	        public void actualizarYuk(){
	            normas = new double[usuarios][k];
	            for (int u = 0; u < usuarios; u++){
	                for (int j = 0; j < k; j++){
	                    normas[u][j] = Norma(u, j);
	                }
	            }
	            double p = (2.0 / (m - 1.0));

	            for (int u = 0; u < usuarios; u++){
	                for (int j = 0; j < k; j++){
	                    double sumatoria = 0.0;
	                    for (int c = 0; c < k; c++){
	                        sumatoria = sumatoria + Math.pow((normas[u][j]) / normas[u][c], p);
	                    }
	                    Yuk[u][j] = 1.0 / sumatoria;
	                    if (Double.isNaN(sumatoria) || sumatoria == 0) Yuk[u][j] = (1.0 / k) + aleatorio.nextDouble() / 100.0;
	                }

	            }
	        }
	        
	        public double calcularJM(){
	            double JM = 0.0;
	            for (int u = 0; u < usuarios; u++){
	                for (int j = 0; j < k; j++){
	                    JM = JM + (Math.pow(Yuk[u][j], m) * Math.pow(normas[u][j], 2.0));
	                }
	            }
	            return JM;
	        }
	        
	        public double Norma(int u, int v) {// v es el centroide. Se calcula la razi cuadrada
	        
	            double distancia = 0.0;
	            
	            Set<Integer> keys = ListaVotosUsuarios.get(u).keySet();
	            
	            for(int key : keys){

	            	double voto = Double.parseDouble("" + ListaVotosUsuarios.get(u).get(key));
	                double votoC = centroides[v][key];
	                if (!conVacios){
	                    distancia = distancia + Math.pow(voto - votoC, 2.0);
	                }else{
	                    if (votoC > 0.0)//votosComun++;
	                        distancia = distancia + Math.pow(voto - votoC, 2.0);
	                }
	            }
	            return Math.sqrt(distancia); 
	        }
	        
	        public double Norma2(int u, int v) {// v es el centroide
	        
	            double distancia = 0.0;
	            double votosComun = 0.0;
	            
	            Set<Integer> keys = ListaVotosUsuarios.get(u).keySet();
	            
	            for (int key: keys){
	                double voto = Double.parseDouble("" + ListaVotosUsuarios.get(u).get(key));
	                if (!conVacios){
	                    votosComun++;
	                    distancia = distancia + Math.pow(voto - centroides[v][key], 2.0);
	                }else{
	                    if (centroides[v][key] > 0.0){
	                        votosComun++;
	                        distancia = distancia + Math.pow(voto - centroides[v][key], 2.0);
	                    }
	                }
	            }
	            return distancia;
	        }

	        public int predecirVotoFCM(int u, int i){
	            double prediccion = 0.0;
	            double sumatoriaPesos = 0.0;
	            
	            for (int v = 0; v < l; v++){ 
	                double votoVecino = 0.0;
	                int vecino = centroidesVecinosporUsuario[u][v];
	                
	                votoVecino = centroides[vecino][i];
	                if(votoVecino!=0) {
	                	sumatoriaPesos = sumatoriaPesos + pesoscentroidesVecinosporUsuario[u][v];
	                }
	                prediccion = prediccion + (votoVecino * pesoscentroidesVecinosporUsuario[u][v]);
	            }
	            prediccion = prediccion / sumatoriaPesos;  //media ponderada
	            if (sumatoriaPesos == 0.0) { prediccion = ((minVoto + maxVoto) / 2.0); }
	            if (prediccion == 0.0) { prediccion = ((minVoto + maxVoto) / 2.0); }//dejar como indiferente en el caso de no tener info para predecir.

	            double pui = 0.0;
	            pui = (prediccion - 1.0) / R;
	            System.out.println(" prediccion="+prediccion+" pui="+pui);
	            
	            if (pui < 0.2){
	                prediccion = 1;
	            }else if (pui >= 0.2 && pui < 0.4){ 
	                prediccion = 2;
	            }else if (pui >= 0.4 && pui < 0.6){
	                prediccion = 3;
	            }else if (pui >= 0.6 && pui < 0.8){
	                prediccion = 4;
	            }else if (pui >= 0.8){
	                prediccion = 5;
	            }
	            
	            return (int)prediccion;
	        }
	        

	        
	        public void determinarCentroidesVecinosFCMeans(){
	            centroidesVecinosporUsuario = new int[usuarios][l];
	            pesoscentroidesVecinosporUsuario = new double[usuarios][l];
	            
	            for (int u = 0; u < usuarios; u++) {
					ArrayList vecinos= new ArrayList<>();
					
					
					for (int c = 0; c < k; c++) {
						double sim = Norma(u,c); 
						vecinos.add(new PartPesos(c,sim));
						
//						System.out.println("el veci ="+vecinos.get(c).toString());
					}
					
					//ordena de menor a mayor
		        	Collections.sort(vecinos, new Comparator<PartPesos>() {
						@Override
						public int compare(PartPesos parte1, PartPesos parte2) {			
							int res= new Double(parte1.getPartProb()).compareTo(new Double(parte2.getPartProb()));
							return res;
						}
					});
					
					for (int c = 0; c < vecinos.size(); c++) {
						
//						System.out.println("el veci 222 ="+vecinos.get(c).toString());
					}
					
					int numVecino = 0;
					
					List<PartPesos> temp = vecinos.subList(0, l);
					
					
					for(PartPesos vecino : temp){
						
	                    centroidesVecinosporUsuario[u][numVecino] = vecino.PartId;
	                    pesoscentroidesVecinosporUsuario[u][numVecino] = vecino.PartProb;
	                    numVecino++;
//	                    System.out.println("El conel vt="+numVecino+" "+vecino.PartId);
	                
					}
	            }
	        }
	        
	        public List<Hashtable> prediccionesUsuarios() {
	        	
	        	determinarCentroidesVecinosFCMeans();
	        	
//	        	predicciones = new int[usuarios][items];
	            
//	        	System.out.println(votosTrain.length);
	        	for (int usu = 0; usu < ListaPredicciones.size(); usu++) {
					
				
	            for (int item = 0; item < ListaPredicciones.get(usu).size(); item++){
	            	
	            	double votoUsu=0.0;
	            	
	            	if(ListaVotosUsuarios.get(usu).get(item) !=null) {
	                votoUsu = Double.parseDouble(""+ListaVotosUsuarios.get(usu).get(item));
	            	}
      
	                if(votoUsu!=0) {
	                	System.out.println("no hace prediccion xq el usuario ("+(usu)+") "
	                			+ "con item ("+(item)+") ya voto ("+votoUsu+")");
	                	
		                
	                }else{
	                
	                	int prediccion = predecirVotoFCM(usu, item);
	                	
//	                	predicciones[usu][item]=prediccion; //.add(new Recomendacion(item,prediccion));
	                	
	                	ListaPredicciones.get(usu).put(item, prediccion);
	                	
	                	
	                	System.out.println("HIZO PREDICCION ("+prediccion+") del usuario ("+usu+")"
	                			+ " con item ("+(item)+")");
	                }
	            }
	            
	            for (int i = 0; i < ListaPredicciones.size(); i++) {
					for (int j = 0; j < ListaPredicciones.get(i).size(); j++) {
						System.out.print("|"+ListaPredicciones.get(i).get(j));	
					}System.out.println();
	            	
	            	
				}
	            
	            //obtiene los numeros mayores de la lista en base al voto de la prediicon
//	        	Collections.sort(predicciones, new Comparator<Recomendacion>() {
//					@Override
//					public int compare(Recomendacion pre1, Recomendacion pre2) {			
//						
//						 
//						
//						return new Integer(pre2.getPrediccion()).compareTo(new Integer(pre1.getPrediccion()));
//					}
//				});
	        	
	        	}
	        	
	        	
	            return ListaPredicciones;
	        }
}