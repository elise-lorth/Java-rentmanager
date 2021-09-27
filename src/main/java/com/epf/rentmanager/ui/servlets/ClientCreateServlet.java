package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	ClientService clientService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/create.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Client client = new Client();
			client.setNom(request.getParameter("last_name"));
			client.setPrenom(request.getParameter("first_name"));
			client.setEmail(request.getParameter("email"));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(request.getParameter("naissance"), formatter);
			client.setNaissance(localDate);
			clientService.create(client);
			response.sendRedirect("http://localhost:8080/rentmanager/users");

		} catch (DateTimeParseException e) {
			request.setAttribute("ErrorMessage", "Format de date invalide");
			doGet(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("ErrorMessage", e.getMessage());
			doGet(request, response);
		}
	}
}
