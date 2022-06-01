package edu.ucam.pojos;

import java.util.Enumeration;
import java.util.Hashtable;

public class Factura {

	private String id = "";
	private Hashtable<String, Producto> productos = new Hashtable<String, Producto>();
	
	public Factura() {
	}
	
	public Factura(String id) {
		super();
		this.id = id;
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
		String frase = "Factura " + this.getId() + ":<br />";
		Enumeration<Producto> eProducto = productos.elements();
		Producto producto = null;
		
		while(eProducto.hasMoreElements()){
			producto = eProducto.nextElement();
			frase += "&nbsp;&nbsp;&nbsp;&nbsp;" + producto.getId() + ": " + producto.getName() + " Stock: " + producto.getStock() + "<br />";
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
	
	public String addProducto(Producto producto) {
		
		if(producto != null) {
			if(this.productos.containsKey(producto.getId())){
				this.productos.get(producto.getId()).addStock();
				return "Se ha agregado stock correctamente al producto en la factura";
			}
			else{
				producto.addStock();
				this.productos.put(producto.getId(), producto);
				return "Se ha insertado correctamente el producto en la factura";
			}
		}
		else {
			return "No se ha insertado correctamente";
		}
		
	}
	
	public String deleteProducto(String id) {
		
		if(this.productos.containsKey(id)){
			this.productos.remove(id);
			return "Se ha eliminado correctamente el producto de la factura";
		}
		else{
			return "No se ha eliminado correctamente";
		}
	}
	
}
