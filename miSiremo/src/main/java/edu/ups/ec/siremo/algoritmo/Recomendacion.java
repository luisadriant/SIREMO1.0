package edu.ups.ec.siremo.algoritmo;

public class Recomendacion {

	        public int item;

	        public int prediccion;

	        public Recomendacion(int item, int prediccion) {
	            this.item = item;
	            this.prediccion = prediccion;
	        }

			public int getItem() {
				return item;
			}

			public void setItem(int item) {
				this.item = item;
			}



			public int getPrediccion() {
				return prediccion;
			}

			public void setPrediccion(int prediccion) {
				this.prediccion = prediccion;
			}

			@Override
			public String toString() {
				return "Recomendacion [ item=" + item + ", prediccion=" + prediccion + "]";
			}
	
}
