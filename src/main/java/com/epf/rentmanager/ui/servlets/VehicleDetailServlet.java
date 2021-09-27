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

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/cars/details")
public class VehicleDetailServlet extends HttpServlet {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp");
		try {
			long idc = Long.parseLong(request.getParameter("id"));
			int idci = Integer.parseInt(request.getParameter("id"));
			request.setAttribute("vehicle", vehicleService.findById(idc));

			request.setAttribute("nbresa", reservationService.findResaByVehicleID(idc).size());
			request.setAttribute("nbcli", reservationService.NbClientByVehicle(idci));

			request.setAttribute("rents", reservationService.findResaByVehicleID(idc));

			List<Client> clientListe = new ArrayList<>();

			for (int i = 0; i < reservationService.clientIdByVehicle(idci).size(); i++) {
				int a = reservationService.clientIdByVehicle(idci).get(i);
				Client client = clientService.findById(a);
				clientListe.add(client);
			}
			request.setAttribute("clients", clientListe);

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