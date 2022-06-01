<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>REST Facturas</title>

<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>

<script>
	
	//Listar facturas
	$(document).ready(function(){
	    $("#getJSONButton").click(function(){
	        $.get("rest/getfacturajson",
			      {},
			        function(data,status){
			    	  //alert(data);
			    	  var frase = '';
			    	  jQuery.each(data.facturas, function(i, val) {
			    		  frase += val.productos + '<br />';
			    		  //console.log("ID: " + val.id + "\nName: " + val.name);
			    		  //alert("ID: " + val.id + "\nName: " + val.name);
			    		});
			    		console.log(frase);
			    		document.getElementById("frase").innerHTML = frase;
			        });
	    });
	});
  
  //Crear factura
  $(document).ready(function(){
		$("#createFacturaJSONButton").click(function(){
			var sendInfo = {factura:{id: $('#idFactura').val()}};
			
		    $.ajax({
		           type: "POST",
		           headers: {
		               'Accept': 'application/json',
		               'Content-Type': 'application/json' 
		           },
		           url: "rest/sendfacturajson",
		           dataType: "json",
		           success: function (msg) {
		               if (msg) {
		                   alert(msg.resultado);
		               } else {
		                   alert("Error!");
		               }
		           },
		           
		           data:  JSON.stringify(sendInfo)
			});
		});
	});
	
	//Borrar factura
	$(document).ready(function(){
		$("#deleteFacturaJSONButton").click(function(){
			var sendInfo = {factura:{id: $('#idFactura').val()}};
			
		    $.ajax({
		           type: "DELETE",
		           headers: {
		               'Accept': 'application/json',
		               'Content-Type': 'application/json' 
		           },
		           url: "rest/deletefacturajson",
		           dataType: "json",
		           success: function (msg) {
		               if (msg) {
		                   alert(msg.resultado);
		               } else {
		                   alert("Error!");
		               }
		           },
		           
		           data:  JSON.stringify(sendInfo)
		    });
		});
	});
	
	//Añadir producto en factura
	$(document).ready(function(){
		$("#addProductoFacturaJSONButton").click(function(){
			var sendInfo = {factura:{id: $('#idModFactura').val(), idProducto: $('#idModProducto').val()}};
			
		    $.ajax({
		           type: "POST",
		           headers: {
		               'Accept': 'application/json',
		               'Content-Type': 'application/json' 
		           },
		           url: "rest/sendproductofacturajson",
		           dataType: "json",
		           success: function (msg) {
		               if (msg) {
		                   alert(msg.resultado);
		               } else {
		                   alert("Error!");
		               }
		           },
		           
		           data:  JSON.stringify(sendInfo)
			});
		});
	});
	
	//Borrar producto en factura
	$(document).ready(function(){
		$("#deleteProductoFacturaJSONButton").click(function(){
			var sendInfo = {factura:{id: $('#idModFactura').val(), idProducto: $('#idModProducto').val()}};
			
		    $.ajax({
		           type: "DELETE",
		           headers: {
		               'Accept': 'application/json',
		               'Content-Type': 'application/json' 
		           },
		           url: "rest/deleteproductofacturajson",
		           dataType: "json",
		           success: function (msg) {
		               if (msg) {
		                   alert(msg.resultado);
		               } else {
		                   alert("Error!");
		               }
		           },
		           
		           data:  JSON.stringify(sendInfo)
		    });
		});
	});

</script>

</head>
<body>
<a href="index.html">Ir a Productos</a>
<h1>Facturas</h1>



<button id="getJSONButton">Mostrar Facturas</button>

<br/><br/>

ID factura: <input type="text" id = "idFactura">
<button id="createFacturaJSONButton">Crear Factura</button>
<button id="deleteFacturaJSONButton">Elimina Factura</button>

<br/><br/>

ID factura: <input type="text" id = "idModFactura"><br/>
ID producto: <input type="text" id = "idModProducto"><br/>
<button id="addProductoFacturaJSONButton">Añadir Producto Factura</button>
<button id="deleteProductoFacturaJSONButton">Eliminar Producto Factura</button>

<br/><br/>

<p id="frase"></p>

</body>
</html>