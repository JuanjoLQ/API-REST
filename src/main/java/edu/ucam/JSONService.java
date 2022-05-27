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
	
	
	@GET
	@Path("/getjson")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendJSON() {
		/*
		if (this.productos.size() < 3) {
			this.productos.put("10", new Producto("10", "Manzana"));
			this.productos.put("11", new Producto("11", "Platano"));	
		}
		else{
			this.productos.put("12", new Producto("12", "Naranja"));
			this.productos.put("13", new Producto("13", "Casta�a"));
		}
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
		
		
		
		/*
		//A�adimos una asignatura
		jsonAsignatura.put("id", "DAD2");
		jsonAsignatura.put("name", "Desarrollo de Aplica...");
		jsonRespuesta.append("asignaturas", jsonAsignatura);
		
		
		//A�adimos una asignatura
		jsonAsignatura = new JSONObject();
		jsonAsignatura.put("id", "PW");
		jsonAsignatura.put("name", "Programaci�n Web");
		jsonRespuesta.append("asignaturas", jsonAsignatura);
		*/
		
		//El JSON generado ser�a este:
		// {"asignaturas":[{"name":"Desarrollo de Aplica...","id":"DAD2"},{"name":"Programaci�n Web","id":"PW"}]}
		// Tiene un objeto asignaturas que es un array de dos objetos cuyos atributos son 'id' y 'name'
		
		return Response.status(200).entity(jsonRespuesta.toString()).build();
	}
	
	
	
	@POST	
	@Path("/sendjson")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recieveJSON(InputStream incomingData) {
		
		//Recuperamos el String correspondiente al JSON que nos env�a el navegador
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
		//A�adimos datos
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
	
	
	@DELETE	
	@Path("/deletejson")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteJSON(InputStream incomingData) {
		
		//Recuperamos el String correspondiente al JSON que nos env�a el navegador
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
		//A�adimos datos
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
	
	@PUT
	@Path("/putjson")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateJSON(InputStream incomingData) {
		
		//Recuperamos el String correspondiente al JSON que nos env�a el navegador
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
		//A�adimos datos
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
	
	
	
	
	
}
