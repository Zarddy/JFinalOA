<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
	response.sendRedirect(request.getContextPath()+"/admin/login");
	//request.getRequestDispatcher("/admin/login").forward(request, response);
%>