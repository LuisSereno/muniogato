<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">  

<%
ArrayList<String[]> imagenesMenu = (ArrayList <String[]>) request.getAttribute("imagenesMenu");
for (String[] menu: imagenesMenu){
%>
	<h1 class="textoActividades"><%=menu[0]%></h1>
	<div style="display: inline-block;margin-left:20%;">
		<img src="<%=menu[1]%>" class="imagenesAnadidas" />
	</div>
<%}%>
