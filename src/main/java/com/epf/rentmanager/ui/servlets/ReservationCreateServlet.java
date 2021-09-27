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

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
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

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
		try {
			request.setAttribute("clients", clientService.findAll());
			request.setAttribute("vehicles", vehicleService.findAll());
		} catch (ServiceException e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
		}
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Reservation reservation = new Reservation();
			reservation.setClient_id(Long.parseLong(request.getParameter("client")));
			reservation.setVoiture_id(Long.parseLong(request.getParameter("car")));
			LocalDate localDated = LocalDate.parse(request.getParameter("debut"), formatter);
			reservation.setDebut(localDated);
			LocalDate localDatef = LocalDate.parse(request.getParameter("fin"), formatter);
			
			reservation.setFin(localDatef);
			reservationService.create(reservation);
			response.sendRedirect("http://localhost:8080/rentmanager/rents");
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