package edu.ucam;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import edu.ucam.pojos.Producto;

@Path("/")
public class JSONService {
	private Hashtable<String, Producto> productos = new Hashtable<String, Producto>();
	
	@GET
	@Path("/getjson")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendJSON() {
		//Objeto JSON de respuesta
		JSONObject jsonRespuesta = new JSONObject();	
		
		//Objeto JSON para cada asignatura
		JSONObject jsonAsignatura = new JSONObject();
		
		
		//Objeto JSON para cada producto
		JSONObject jsonProducto = new JSONObject();
		
		Enumeration productoEnumeration = this.productos.elements();

		this.productos.put("10", new Producto("10", "Manzana"));
		this.productos.put("11", new Producto("11", "Platano"));


		while(productoEnumeration.hasMoreElements()){
		    Producto producto = (Producto) productoEnumeration.nextElement();
		    jsonProducto.put("id", producto.getId()); 
		    jsonProducto.put("name", producto.getName()); 
		    jsonRespuesta.append("productos", jsonProducto);
		}
		
		
		
		/*
		//Añadimos una asignatura
		jsonAsignatura.put("id", "DAD2");
		jsonAsignatura.put("name", "Desarrollo de Aplica...");
		jsonRespuesta.append("asignaturas", jsonAsignatura);
		
		
		//Añadimos una asignatura
		jsonAsignatura = new JSONObject();
		jsonAsignatura.put("id", "PW");
		jsonAsignatura.put("name", "Programación Web");
		jsonRespuesta.append("asignaturas", jsonAsignatura);
		*/
		
		//El JSON generado sería este:
		// {"asignaturas":[{"name":"Desarrollo de Aplica...","id":"DAD2"},{"name":"Programación Web","id":"PW"}]}
		// Tiene un objeto asignaturas que es un array de dos objetos cuyos atributos son 'id' y 'name'
		
		return Response.status(200).entity(jsonRespuesta.toString()).build();
	}
	
	
	
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
}
