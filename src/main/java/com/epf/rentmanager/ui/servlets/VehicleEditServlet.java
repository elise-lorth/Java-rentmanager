package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/cars/edit")
public class VehicleEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	VehicleService vehicleService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp");

		int idc = Integer.valueOf(request.getQueryString().substring(3));
		Vehicle vehicle = new Vehicle();

		try {
			vehicle = vehicleService.findById(idc);
		} catch (final Exception e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
		}
		request.setAttribute("vehicle", vehicle);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		Vehicle vehicle = new Vehicle();
		vehicle.setConstructeur(request.getParameter("manufacturer"));
		vehicle.setModele(request.getParameter("modele"));
		vehicle.setNb_places(Short.parseShort(request.getParameter("seats")));
		vehicle.setIdentifiant(Long.parseLong(request.getParameter("id")));
		try {
			vehicleService.edit(vehicle);
			response.sendRedirect("http://localhost:8080/rentmanager/cars");
		} catch (ServiceException e) {
			System.out.println("Une erreur est survenue : " + e.getMessage());
			request.setAttribute("ErrorMessage", e.getMessage());
			doGet(request, response);
		}
	}
}