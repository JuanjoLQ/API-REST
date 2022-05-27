package edu.ucam.pojos;

import java.util.Enumeration;
import java.util.Hashtable;

public class Factura {

	private String id = "";
	private Hashtable<String, Producto> productos = new Hashtable<String, Producto>();
	
	public Factura() {
	}

	public Factura(String id, Hashtable<String, Producto> productos) {
		super();
		this.id = id;
		this.productos = productos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Hashtable<String, Producto> getProductos() {
		return productos;
	}

	public void setProductos(Hashtable<String, Producto> productos) {
		this.productos = productos;
	}
	
	public String toStringFactura() {
		String frase = "Factura " + this.getId() + ":\n";
		Enumeration<Producto> eProducto = productos.elements();
		Producto producto = null;
		
		while(eProducto.hasMoreElements()){
			producto = eProducto.nextElement();
			frase += "\t" + producto.getId() + ": " + producto.getName() + "\n";
		}
		return frase;
	}
	
	
	public Producto getProducto(String idProducto) {
		if(productos.containsKey(idProducto)) {
			return productos.get(idProducto);
		}
		else {
			return null;
		}
	}
	
}
