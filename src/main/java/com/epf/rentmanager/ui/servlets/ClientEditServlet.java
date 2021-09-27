package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/users/edit")
public class ClientEditServlet extends HttpServlet {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
		int idc = Integer.valueOf(request.getQueryString().substring(3));
		Client client = new Client();
		try {
			client = clientService.findById(idc);
		} catch (final Exception e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
		}
		request.setAttribute("client", client);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		Client client = new Client();
		client.setNom(request.getParameter("last_name"));
		client.setPrenom(request.getParameter("first_name"));
		client.setEmail(request.getParameter("email"));
		LocalDate localDate = LocalDate.parse(request.getParameter("naissance"));
		client.setNaissance(localDate);
		client.setIdentifiant(Long.parseLong(request.getParameter("id")));

		try {
			clientService.edit(client);
			response.sendRedirect("http://localhost:8080/rentmanager/users");
		} catch (DateTimeParseException e) {
			request.setAttribute("ErrorMessage", "Format de date invalide");
			doGet(request, response);
		} catch (ServiceException e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
			request.setAttribute("ErrorMessage", e.getMessage());
			doGet(request, response);
		}
	}
}