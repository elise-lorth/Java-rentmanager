package com.epf.rentmanager.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epf.rentmanager.configuration.AppConfiguration;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

public class ReservationUI {

	public static void menuReservation() throws ServiceException, DaoException {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		ReservationService reservationService = context.getBean(ReservationService.class);

		boolean suivantR = true;
		while (suivantR) {
			IOUtils.print("\nMenu réservations : choississez une action \n"
					+ "1. Enregistrer une nouvelle réservation \n2. Lister les réservations \n"
					+ "3. afficher les réservation d'un client\n4. Afficher les réservations d'un véhicule\n5. Supprimer une réservation \n"
					+ "6. Retour\n7. Quitter");
			int choixR = IOUtils.readInt("");

			switch (choixR) {

			case 1: // créer une réservation
				Reservation newReservation = new Reservation();
				newReservation.setClient_id(IOUtils.readLong("Client Id"));
				newReservation.setVoiture_id(IOUtils.readLong("Vehicule ID"));
				newReservation.setDebut((IOUtils.readLDate("Date de debut", true)));
				newReservation.setFin((IOUtils.readLDate("Date de fin", true)));
				long idy = 0;
				try {
					idy = reservationService.create(newReservation);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				System.out.println("id : " + idy);
				System.out.println("Une réservation a été créé");
				break;

			case 2: // afficher toutes les reservations
				List<Reservation> reservations = null;
				try {
					reservations = reservationService.findAll();
					for (Reservation reservation : reservations) {
						System.out.println(reservation);
					}
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				break;

			case 3: // afficher les reservations d'un client
				List<Reservation> resac = null;
				long idw = 0;
				System.out.println("Quel est l'id du vehicule dont vous voulez voir les reservations ? \n");
				idw = IOUtils.readLong("idw");
				try {
					resac = reservationService.findResaByClientID(idw);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				for (Reservation reservationc : resac) {
					System.out.println(reservationc);
				}
				break;

			case 4: // afficher les réservations d'un véhicule
				List<Reservation> resav = null;
				long idp = 0;
				System.out.println("Quel est l'id du vehicule dont vous voulez voir les reservations ? \n");
				idp = IOUtils.readLong("idp");
				try {
					resav = reservationService.findResaByVehicleID(idp);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				for (Reservation reservationv : resav) {
					System.out.println(reservationv);
				}
				break;

			case 5: // supprimer une réservation
				long idq = 0;
				idq = IOUtils.readLong("id");
				System.out.println("l'id de la reservation à effacer : " + idq);
				try {
					reservationService.delete(idq);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("La reservation a ete suprimee");
				break;

			case 8: // Chercher un client par id (faire des recherches par nom aussi ?)
				Reservation reservation = null;
				long idr = 0;
				System.out.println("Id ?");
				idr = IOUtils.readLong("idr");
				try {
					reservation = reservationService.findById(idr);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				System.out.println(reservation);
				break;

			case 9: // modifier une réservation
				Reservation clientx = null;
				long idqx = 0;
				System.out.println("Id ?");
				idqx = IOUtils.readLong("idq");
				try {
					clientx = reservationService.findById(idqx);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				System.out.println(clientx.getClient_id());
				clientx.setClient_id(IOUtils.readLong("Client"));
				System.out.println(clientx.getVoiture_id());
				clientx.setVoiture_id(IOUtils.readLong("Voiture"));
				System.out.println(clientx.getDebut());
				clientx.setDebut(IOUtils.readLDate("Debut", true));
				System.out.println(clientx.getFin());
				clientx.setFin(IOUtils.readLDate("Fin", true));
				reservationService.edit(clientx);
				break;

			case 6: // retour
				System.out.println("Retour");
				suivantR = false;
				break;

			case 7: // quitter
				System.out.println("Vous êtes déconnecté");
				System.exit(1);
				break;
			}
		}
	}
}
