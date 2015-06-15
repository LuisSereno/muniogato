<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">  

<div class="tamanoImagenes">
	<h1 style="margin-left:40%;">RESTAURANTE</h1>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/1.jpg" width="200" height="150" />
	</div>
	
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/2.jpg" width="200" height="150" />
	</div>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/3.jpg" width="200" height="150" />
	</div>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/4.jpg" width="200" height="150" />
	</div>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/13.jpg" width="200" height="150" />
	</div>
		<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/14.jpg" width="200" height="150" />
	</div>
		<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/20.jpg" width="200" height="150" />
	</div>
		<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/21.jpg" width="200" height="150" />
	</div>
		<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/22.jpg" width="200" height="150" />
	</div>
		
	<h1 style="margin-left:45%">HOTEL</h1>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/5.jpg" width="200" height="150" />
	</div>
	
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/6.jpg" width="200" height="150" />
	</div>
	
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/7.jpg" width="200" height="150" />
	</div>
	
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/8.jpg" width="200" height="150" />
	</div>
	
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/9.jpg" width="200" height="150" />
	</div>
	
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/10.jpg" width="200" height="150" />
	</div>
	
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/11.jpg" width="200" height="150" />
	</div>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/12.jpg" width="200" height="150" />
	</div>
	
	<h1 style="margin-left:45%">EVENTOS</h1>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/15.jpg" width="200" height="150" />
	</div>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/16.jpg" width="200" height="150" />
	</div>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/17.jpg" width="200" height="150" />
	</div>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/18.jpg" width="200" height="150" />
	</div>
	<div style="display: inline-block;margin-left:5%;">
		<img  src="imagenes/restauranteHotel/19.jpg"  width="200" height="150" />
	</div>
</div>


<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	
<script>

	$("img").on("click",function(){
		console.log($(this).height());
		if ($(this).hasClass("imgfull")){
			$(this).removeClass("imgfull");
		}else{
			$(this).addClass("imgfull");
		}

	});



</script>