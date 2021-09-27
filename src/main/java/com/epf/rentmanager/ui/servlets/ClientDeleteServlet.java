package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	ReservationService reservationService;
	@Autowired
	ClientService clientService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			long idc = Long.parseLong(request.getParameter("id"));
			clientService.delete(idc);
			response.sendRedirect("http://localhost:8080/rentmanager/users");

		} catch (final Exception e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
