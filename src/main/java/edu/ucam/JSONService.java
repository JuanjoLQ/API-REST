package edu.ucam;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import edu.ucam.pojos.Factura;
import edu.ucam.pojos.Producto;

@Path("/")
public class JSONService {
	static private Hashtable<String, Producto> productos = new Hashtable<String, Producto>();
	static private Hashtable<String, Factura> facturas = new Hashtable<String, Factura>();
	
	//Listar productos
	@GET
	@Path("/getjson")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendJSON() {
		
		/*
		
		this.productos.put("10", new Producto("10", "Manzana"));
		this.productos.put("11", new Producto("11", "Platano"));
		this.productos.put("12", new Producto("12", "Naranja"));
		this.productos.put("13", new Producto("13", "Castaña"));
		this.productos.put("14", new Producto("14", "Botella"));
		this.productos.put("15", new Producto("15", "Zumo"));
		this.productos.put("16", new Producto("16", "Leche"));
		this.productos.put("17", new Producto("17", "Refresco"));
		
		*/
		
		//Objeto JSON de respuesta
		JSONObject jsonRespuesta = new JSONObject();	
		
		//Objeto JSON para cada asignatura
		JSONObject jsonAsignatura = new JSONObject();
		
		
		//Objeto JSON para cada producto
		
		Enumeration productoEnumeration = this.productos.elements();
		
		while(productoEnumeration.hasMoreElements()){
			JSONObject jsonProducto = new JSONObject();
		    Producto producto = (Producto) productoEnumeration.nextElement();
		    jsonProducto.put("id", producto.getId()); 
		    //System.out.println("ID: " + producto.getId());
		    jsonProducto.put("name", producto.getName()); 
		    jsonRespuesta.append("productos", jsonProducto);
		}
		
		//El JSON generado sería este:
		// {"asignaturas":[{"name":"Desarrollo de Aplica...","id":"DAD2"},{"name":"Programación Web","id":"PW"}]}
		// Tiene un objeto asignaturas que es un array de dos objetos cuyos atributos son 'id' y 'name'
		
		return Response.status(200).entity(jsonRespuesta.toString()).build();
	}
	
	
	//Añadir producto a la BBDD
	@POST	
	@Path("/sendjson")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recieveJSON(InputStream incomingData) {
		
		//Recuperamos el String correspondiente al JSON que nos envía el navegador
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		//Construimos un objeto JSON en base al recibido como cadena 
		JSONObject jsonRecibido = new JSONObject(sb.toString());
		
		//Recuperamos el valor del atributo name del objeto recibido
		String id = jsonRecibido.getJSONObject("producto").getString("id");
		System.out.println(id);
		String name = jsonRecibido.getJSONObject("producto").getString("name");
		System.out.println(name);
		String frase = "";
		//Añadimos datos
		if(!this.productos.containsKey(id)) {
			this.productos.put(id, new Producto(id, name));
			frase = "Producto insertado correctamente";
		}
		else {
			frase = "Producto NO insertado correctamente";
		}
		
		
		//Generamos un objeto JSON como respuesta
		JSONObject jsonRespuesta = new JSONObject();
		jsonRespuesta.append("resultado", frase);
		
		return Response.status(200).entity(jsonRespuesta.toString()).build();
	}
	
	//Eliminar producto a la BBDD
	@DELETE	
	@Path("/deletejson")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteJSON(InputStream incomingData) {
		
		//Recuperamos el String correspondiente al JSON que nos envía el navegador
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		//Construimos un objeto JSON en base al recibido como cadena 
		JSONObject jsonRecibido = new JSONObject(sb.toString());
		
		//Recuperamos el valor del atributo name del objeto recibido
		String idDelete = jsonRecibido.getJSONObject("producto").getString("id");
		System.out.println("Delete--> " + idDelete);
		String frase = "";
		//Añadimos datos
		if(this.productos.containsKey(idDelete)) {
			this.productos.remove(idDelete);
			frase = "Producto eliminado correctamente";
		}
		else {
			frase = "Producto NO eliminado correctamente";
		}
		
		
		//Generamos un objeto JSON como respuesta
		JSONObject jsonRespuesta = new JSONObject();
		jsonRespuesta.append("resultado", frase);
		
		return Response.status(200).entity(jsonRespuesta.toString()).build();
	}
	
	//Actualizar producto de la BBDD
	@PUT
	@Path("/putjson")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateJSON(InputStream incomingData) {
		
		//Recuperamos el String correspondiente al JSON que nos envía el navegador
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		//Construimos un objeto JSON en base al recibido como cadena 
		JSONObject jsonRecibido = new JSONObject(sb.toString());
		
		//Recuperamos el valor del atributo name del objeto recibido
		String idUpdate = jsonRecibido.getJSONObject("producto").getString("id");
		String nameUpdate = jsonRecibido.getJSONObject("producto").getString("name");
		System.out.println("Update--> " + idUpdate);
		String frase = "";
		//Añadimos datos
		if(this.productos.containsKey(idUpdate)) {
			this.productos.replace(idUpdate, new Producto(idUpdate, nameUpdate));
			frase = "Producto modificado correctamente";
		}
		else {
			frase = "Producto NO modificado correctamente";
		}
		
		
		//Generamos un objeto JSON como respuesta
		JSONObject jsonRespuesta = new JSONObject();
		jsonRespuesta.append("resultado", frase);
		
		return Response.status(200).entity(jsonRespuesta.toString()).build();
	}
	
	//Listar facturas
	@GET
	@Path("/getfacturajson")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendFacturaJSON() {
		
		/*
		this.productos.put("10", new Producto("10", "Manzana"));
		this.productos.put("11", new Producto("11", "Platano"));
		this.productos.put("12", new Producto("12", "Naranja"));
		this.productos.put("13", new Producto("13", "Castaña"));
		this.productos.put("14", new Producto("14", "Botella"));
		this.productos.put("15", new Producto("15", "Zumo"));
		this.productos.put("16", new Producto("16", "Leche"));
		this.productos.put("17", new Producto("17", "Refresco"));

		this.facturas.put("1", new Factura("1"));
		this.facturas.put("2", new Factura("2"));
		this.facturas.put("3", new Factura("3"));
		
		Producto producto = new Producto(this.productos.get("12").getId(), this.productos.get("12").getName());
		this.facturas.get("1").addProducto(producto);
		producto = new Producto(this.productos.get("13").getId(), this.productos.get("13").getName());
		this.facturas.get("1").addProducto(producto);
		
		producto = new Producto(this.productos.get("10").getId(), this.productos.get("10").getName());
		this.facturas.get("2").addProducto(producto);
		producto = new Producto(this.productos.get("14").getId(), this.productos.get("14").getName());
		this.facturas.get("2").addProducto(producto);
		producto = new Producto(this.productos.get("14").getId(), this.productos.get("14").getName());
		this.facturas.get("2").addProducto(producto);
		producto = new Producto(this.productos.get("16").getId(), this.productos.get("16").getName());
		this.facturas.get("2").addProducto(producto);
		
		producto = new Producto(this.productos.get("16").getId(), this.productos.get("16").getName());
		this.facturas.get("3").addProducto(producto);
		producto = new Producto(this.productos.get("16").getId(), this.productos.get("16").getName());
		this.facturas.get("3").addProducto(producto);
		producto = new Producto(this.productos.get("16").getId(), this.productos.get("16").getName());
		this.facturas.get("3").addProducto(producto);
		producto = new Producto(this.productos.get("16").getId(), this.productos.get("16").getName());
		this.facturas.get("3").addProducto(producto);
		
		*/
		//Objeto JSON de respuesta
		JSONObject jsonRespuesta = new JSONObject();	
		
		//Objeto JSON para cada asignatura
		JSONObject jsonAsignatura = new JSONObject();
		
		
		//Objeto JSON para cada producto
		
		Enumeration facturaEnumeration = this.facturas.elements();
		
		while(facturaEnumeration.hasMoreElements()){
			JSONObject jsonProducto = new JSONObject();
			Factura factura = (Factura) facturaEnumeration.nextElement();
		    jsonProducto.put("id", factura.getId()); 
		    //System.out.println("ID: " + producto.getId());
		    jsonProducto.put("productos", factura.toStringFactura()); 
		    jsonRespuesta.append("facturas", jsonProducto);
		}
		
		//El JSON generado sería este:
		// {"asignaturas":[{"name":"Desarrollo de Aplica...","id":"DAD2"},{"name":"Programación Web","id":"PW"}]}
		// Tiene un objeto asignaturas que es un array de dos objetos cuyos atributos son 'id' y 'name'
		
		return Response.status(200).entity(jsonRespuesta.toString()).build();
	}
	
	//Añadir factura a la BBDD
	@POST	
	@Path("/sendfacturajson")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recieveFacturaJSON(InputStream incomingData) {
		
		//Recuperamos el String correspondiente al JSON que nos envía el navegador
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		
		//Construimos un objeto JSON en base al recibido como cadena 
		JSONObject jsonRecibido = new JSONObject(sb.toString());
		
		//Recuperamos el valor del atributo name del objeto recibido
		String id = jsonRecibido.getJSONObject("factura").getString("id");
		System.out.println(id);
		String frase = "";
		//Añadimos datos
		if(!this.facturas.containsKey(id)) {
			this.facturas.put(id, new Factura(id));
			frase = "Factura insertada correctamente";
		}
		else {
			frase = "Factura NO insertada correctamente";
		}
		
		
		//Generamos un objeto JSON como respuesta
		JSONObject jsonRespuesta = new JSONObject();
		jsonRespuesta.append("resultado", frase);
		
		return Response.status(200).entity(jsonRespuesta.toString()).build();
	}
	
	//Eliminar factura
		@DELETE	
		@Path("/deletefacturajson")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response deleteFacturaJSON(InputStream incomingData) {
			
			//Recuperamos el String correspondiente al JSON que nos envía el navegador
			StringBuilder sb = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
			} catch (Exception e) {
				System.out.println("Error Parsing: - ");
			}
			
			//Construimos un objeto JSON en base al recibido como cadena 
			JSONObject jsonRecibido = new JSONObject(sb.toString());
			
			//Recuperamos el valor del atributo name del objeto recibido
			String idDelete = jsonRecibido.getJSONObject("factura").getString("id");
			System.out.println("Delete--> " + idDelete);
			String frase = "";
			//Añadimos datos
			if(this.facturas.containsKey(idDelete)) {
				this.facturas.remove(idDelete);
				frase = "Factura eliminada correctamente";
			}
			else {
				frase = "Factura NO eliminada correctamente";
			}
			
			
			//Generamos un objeto JSON como respuesta
			JSONObject jsonRespuesta = new JSONObject();
			jsonRespuesta.append("resultado", frase);
			
			return Response.status(200).entity(jsonRespuesta.toString()).build();
		}
		
		//Añadir producto a factura
		@POST	
		@Path("/sendproductofacturajson")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response addProductoFacturaJSON(InputStream incomingData) {
			
			//Recuperamos el String correspondiente al JSON que nos envía el navegador
			StringBuilder sb = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
			} catch (Exception e) {
				System.out.println("Error Parsing: - ");
			}
			
			//Construimos un objeto JSON en base al recibido como cadena 
			JSONObject jsonRecibido = new JSONObject(sb.toString());
			
			//Recuperamos el valor del atributo name del objeto recibido
			String id = jsonRecibido.getJSONObject("factura").getString("id");
			System.out.println(id);
			String idProducto = jsonRecibido.getJSONObject("factura").getString("idProducto");
			System.out.println(idProducto);
			
			String frase = "";
			//Añadimos datos
			if(this.facturas.containsKey(id) && this.productos.containsKey(idProducto)) {
				Producto producto = new Producto(this.productos.get(idProducto).getId(), this.productos.get(idProducto).getName());
				this.facturas.get(id).addProducto(producto);
				frase = "Factura actualizada correctamente";
			}
			else {
				frase = "Factura NO actualizada correctamente";
			}
			
			
			//Generamos un objeto JSON como respuesta
			JSONObject jsonRespuesta = new JSONObject();
			jsonRespuesta.append("resultado", frase);
			
			return Response.status(200).entity(jsonRespuesta.toString()).build();
		}
		
		
		//Eliminar producto de factura
				@DELETE	
				@Path("/deleteproductofacturajson")
				@Consumes(MediaType.APPLICATION_JSON)
				public Response deleteProductoFacturaJSON(InputStream incomingData) {
					
					//Recuperamos el String correspondiente al JSON que nos envía el navegador
					StringBuilder sb = new StringBuilder();
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
						String line = null;
						while ((line = in.readLine()) != null) {
							sb.append(line);
						}
					} catch (Exception e) {
						System.out.println("Error Parsing: - ");
					}
					
					//Construimos un objeto JSON en base al recibido como cadena 
					JSONObject jsonRecibido = new JSONObject(sb.toString());
					
					//Recuperamos el valor del atributo name del objeto recibido
					String idDelete = jsonRecibido.getJSONObject("factura").getString("id");
					System.out.println("Delete--> " + idDelete);
					String idProducto = jsonRecibido.getJSONObject("factura").getString("idProducto");
					System.out.println("Delete--> " + idProducto);
					
					String frase = "";
					//Añadimos datos
					if(this.facturas.containsKey(idDelete) && this.facturas.get(idDelete).getProductos().containsKey(idProducto)) {
						this.facturas.get(idDelete).getProductos().remove(idProducto);
						frase = "Factura actualizada correctamente";
					}
					else {
						frase = "Factura NO actualizada correctamente";
					}
					
					
					//Generamos un objeto JSON como respuesta
					JSONObject jsonRespuesta = new JSONObject();
					jsonRespuesta.append("resultado", frase);
					
					return Response.status(200).entity(jsonRespuesta.toString()).build();
				}
	
}
