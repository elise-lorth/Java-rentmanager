package com.epf.rentmanager.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epf.rentmanager.configuration.AppConfiguration;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

public class VehicleUI {

	public static void menuVehicle() throws ServiceException, DaoException {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		VehicleService vehicleService = context.getBean(VehicleService.class);

		boolean suivantV = true;
		while (suivantV) {
			IOUtils.print("\nMenu véhicules: choississez une action \n" + "1. Enregistrer un nouveau véhicule \n"
					+ "2. Lister les véhicules \n" + "3. Trouver un fichier véhicule \n"
					+ "4. Supprimer un véhicule \n7. Modifier un véhicule\n" + "5. Retour\n6. Quitter");
			int choixV = IOUtils.readInt("");

			switch (choixV) {
			case 1: // Créer un nouveau véhicule
				try {
					Vehicle newVehicle = new Vehicle();
					newVehicle.setConstructeur(IOUtils.readString("Constructeur", true));
					newVehicle.setNb_places(IOUtils.readShort("Nombre de places"));
					long idv = vehicleService.create(newVehicle);
					System.out.println("Identifiant voiture : " + idv);
					System.out.println("Voiture créée");
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2: // Lister les véhicules
				List<Vehicle> vehicles = vehicleService.findAll();
				for (Vehicle vehicle : vehicles) {
					System.out.println(vehicle);
				}
				break;

			case 3: // Chercher un véhicule par id 
				Vehicle vehicule = null;
				long idvc = 0;
				System.out.println("Id ?");
				idvc = IOUtils.readLong("idvc");
				try {
					vehicule = vehicleService.findById(idvc);//
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				System.out.println(vehicule);
				break;

			case 4: // Supprimer un véhicule
				long idx = 0;
				idx = IOUtils.readLong("id");
				System.out.println("l'id du vehicule à effacer : " + idx);
				try {
					vehicleService.delete(idx);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("Le vehicule a ete suprimé");
				break;

			case 5: // retour
				System.out.println("Vous êtes déconnecté");
				suivantV = false;
				break;

			case 6: // quitter
				System.out.println("Vous êtes déconnecté");
				System.exit(1);
				break;

			case 7: // Modifier un véhicule
				Vehicle clientxv = null;
				long idqxv = 0;
				System.out.println("Id ?");
				idqxv = IOUtils.readLong("idq");
				try {
					clientxv = vehicleService.findById(idqxv);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				System.out.println(clientxv.getConstructeur());
				clientxv.setConstructeur(IOUtils.readString("Constructeur", true));
				System.out.println(clientxv.getModele());
				clientxv.setModele(IOUtils.readString("Modele", true));
				System.out.println(clientxv.getNb_places());
				clientxv.setNb_places(IOUtils.readShort("Nb_place"));
				vehicleService.edit(clientxv);
				break;
			}
		}
	}
}
