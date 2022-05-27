package edu.ucam.pojos;

public class Producto {
	
	private String id = "";
	private String name = "";
	
	public Producto() {
		// TODO Auto-generated constructor stub
	}

	public Producto(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
