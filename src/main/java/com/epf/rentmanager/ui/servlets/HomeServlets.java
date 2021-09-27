package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/home")
public class HomeServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	ReservationService reservationService;
	@Autowired
	ClientService clientService;
	@Autowired
	VehicleService vehicleService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		try {
			request.setAttribute("nb_voiture", vehicleService.nb_voiture());
			request.setAttribute("nb_client", clientService.nb_client());
			request.setAttribute("nb_reservation", reservationService.nb_reservation());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		dispatcher.forward(request, response);
	}
}