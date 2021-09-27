package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/users/details")
public class ClientDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	ReservationService reservationService;
	@Autowired
	VehicleService vehicleService;
	@Autowired
	ClientService clientService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/details.jsp");

		try {

			long idc = Long.parseLong(request.getParameter("id"));
			int idci = Integer.parseInt(request.getParameter("id"));
			request.setAttribute("client", clientService.findById(idc));

			request.setAttribute("nbresa", reservationService.findResaByClientID(idc).size());
			request.setAttribute("nbvehi", reservationService.NbVehicleByClient(idci));

			request.setAttribute("rents", reservationService.findResaByClientID(idc));

			List<Vehicle> vehicleListe = new ArrayList<>();

			for (int i = 0; i < reservationService.vehicleIdByClient(idci).size(); i++) {
				int a = reservationService.vehicleIdByClient(idci).get(i);
				Vehicle vehicle = vehicleService.findById(a);
				vehicleListe.add(vehicle);
			}
			request.setAttribute("cars", vehicleListe);

		} catch (final Exception e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
		}
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
