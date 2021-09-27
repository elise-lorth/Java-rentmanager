package com.epf.rentmanager.ui;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.utils.IOUtils;

public class MainUI {
	
	public static void main(String[] args) throws ServiceException, DaoException {
				
		boolean suivant = true;
		while (suivant) {
			IOUtils.print(
					"Quel service voulez-vous ?\n1. Gérer les client\n2. Gérer les véhicules\n3. Gérer les réservations\n4.Quitter");
			int choix = IOUtils.readInt("");

			switch (choix) {
			case 1:
				ClientUI.menuClient();
				break;

			case 2:
				VehicleUI.menuVehicle();
				break;

			case 3:
				ReservationUI.menuReservation();
				break;

			case 4:
				System.out.println("Vous êtes déconnecté");
				suivant = false;
				break;

			}
		}
	}

}
