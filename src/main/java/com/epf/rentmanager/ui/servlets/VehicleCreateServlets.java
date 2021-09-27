package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/cars/create")
public class VehicleCreateServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	VehicleService vehicleService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Vehicle vehicle = new Vehicle();
			vehicle.setModele(request.getParameter("modele"));
			vehicle.setConstructeur(request.getParameter("manufacturer"));
			vehicle.setNb_places(Short.parseShort(request.getParameter("seats")));
			vehicleService.create(vehicle);

		} catch (Exception e) {
			request.setAttribute("ErrorMessage", e.getMessage());
			System.out.println("Une erreur est survenue : " + e.getMessage());
			doGet(request, response);
		}
		response.sendRedirect("http://localhost:8080/rentmanager/cars");
	}
}
