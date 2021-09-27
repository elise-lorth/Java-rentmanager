package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ClientService clientService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private ReservationService reservationService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/details.jsp");

		try {
			int idresa = Integer.parseInt(request.getParameter("id"));
			Reservation reservation = reservationService.findById(idresa);
			request.setAttribute("reservation", reservation);
			request.setAttribute("vehicle", vehicleService.findById(reservation.getVoiture_id()));
			request.setAttribute("client", clientService.findById(reservation.getClient_id()));
		} catch (final Exception e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
		}
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
