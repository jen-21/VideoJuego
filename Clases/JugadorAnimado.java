package Clases;


import java.util.HashMap;

import Implementacion.Juego;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.shape.Rectangle;

public class JugadorAnimado extends ObjetoJuego{
	private int vidas;
	private HashMap<String, Animacion> animaciones;
	private int xImagen;
	private int yImagen;
	private int anchoImagen;
	private int altoImagen;
	private String animacionActual;
	private int direccion=1;
	
	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

	public JugadorAnimado(int x, int y, String nombreImagen, int velocidad, int vidas, String animacionActual) {
		super(x, y, nombreImagen, velocidad);
		this.vidas = vidas;
		this.animacionActual = animacionActual;
		animaciones = new HashMap<String, Animacion>();
		inicializarAnimaciones();
	}
	
	public int getVidas() {
		return vidas;
	}
	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
	
	public void inicializarAnimaciones() {
		Rectangle coordenadasCorrer[]= {
				new Rectangle(40, 47, 75,67),
				new Rectangle(100,200, 75,67),
				new Rectangle(160,200, 75,67),
				new Rectangle(200,200, 75,67),
				new Rectangle(260,200, 75,67),
				new Rectangle(300,200, 75,77)
		};
		
		Animacion animacionCorrer = new Animacion(0.05,coordenadasCorrer);
		animaciones.put("correr", animacionCorrer);
		
		Rectangle coordenadasDescanso[] = {
				new Rectangle(38, 47, 31,37),
				new Rectangle(67,47, 31,37),
				new Rectangle(96,47, 31,37),
				new Rectangle(125,47, 31,37)
		};
		Animacion animacionDescanso = new Animacion(0.1, coordenadasDescanso);
		animaciones.put("descanso",animacionDescanso);
	}
	
	public void calcularFrame(double t) {
		Rectangle coordenadas = animaciones.get(animacionActual).calcularFrameActual(t);
		this.xImagen = (int)coordenadas.getX();
		this.yImagen = (int)coordenadas.getY();
		this.altoImagen = (int)coordenadas.getWidth();
		this.anchoImagen = (int)coordenadas.getHeight();		
	}
	public Rectangle obtenerRectangulo() {
		return new Rectangle(x, y, (direccion*anchoImagen) - 10, altoImagen);
	}
	
	@Override
	public void pintar(GraphicsContext graficos) {
		graficos.drawImage(Juego.imagenes.get(nombreImagen),xImagen,yImagen,anchoImagen,altoImagen, x + (direccion==-1?anchoImagen:0), y, direccion*anchoImagen, altoImagen);//xImage, yImagen, anchoFragmento, altoFragmento, xPintar, yPintar, anchoPintar, altoPintar
		
	}
	
	
	@Override
	public void mover() {
		if (x>700)
			x=-80;
		
		if (Juego.derecha)
			x+=velocidad;
		
		if (Juego.izquierda)
			x-=velocidad;
	
	}

	public String getAnimacionActual() {
		return animacionActual;
	}

	public void setAnimacionActual(String animacionActual) {
		this.animacionActual = animacionActual;
	}
	
	public void verificarColisionesItem(Item item) {
		if (!item.isCapturado() && this.obtenerRectangulo().getBoundsInLocal().intersects(item.obtenerRectangulo().getBoundsInLocal())) {
			this.vidas += item.getCantidadVidas();
			item.setCapturado(true);
		}
			
	}
}