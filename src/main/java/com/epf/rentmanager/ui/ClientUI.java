package com.epf.rentmanager.ui;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.*;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;

public class ClientUI {

	public static void menuClient() throws ServiceException, DaoException {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		ClientService clientService = context.getBean(ClientService.class);

		boolean suivantC = true;
		while (suivantC) {
			IOUtils.print("\nMenu client : choississez une action \n" + "1. Enregistrer un nouveau un client \n"
					+ "2. Lister les clients \n" + "3. Trouver un fichier client \n"
					+ "4. Supprimer un client  \n7. Modifier un client\n" + "5. Retour\n6. Quitter");
			int choixC = IOUtils.readInt("");

			switch (choixC) {
			case 1: // créer nouveau client
				Client newClient;
				try {
					newClient = new Client();
					newClient.setNom(IOUtils.readString("Nom", true));
					newClient.setPrenom(IOUtils.readString("Prenom", true));
					newClient.setEmail(IOUtils.readString("Email", true));
					newClient.setNaissance(IOUtils.readLDate("Date de Naissance", true));
					long idc = clientService.create(newClient);
					System.out.println("Identifiant client : " + idc);
					System.out.println("Client créé");
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2: // Liste des clients
				List<Client> clients = clientService.findAll();
				for (Client client : clients) {
					System.out.println(client);
				}
				break;

			case 3: // Chercher un client par id
				Client client = null;
				long idq = 0;
				System.out.println("Id ?");
				idq = IOUtils.readLong("idq");
				try {
					client = clientService.findById(idq);//
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				System.out.println(client);
				break;

			case 4: // Supprimer un client
				long idc = 0;
				idc = IOUtils.readLong("id");
				System.out.println("l'id du client à effacer : " + idc);
				try {
					clientService.delete(idc);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("Le client a ete suprimé");
				break;

			case 5: // retour
				System.out.println("Retour");
				suivantC = false;
				break;

			case 6: // quitter
				System.out.println("Vous êtes déconnecté");
				System.exit(1);
				break;

			case 7: // modifier un client
				Client clientx = null;
				long idqx = 0;
				System.out.println("Id ?");
				idqx = IOUtils.readLong("idq");
				try {
					clientx = clientService.findById(idqx);
				} catch (ServiceException e) {
					System.out.println(e.getMessage());
				}
				System.out.println(clientx.getNom());
				clientx.setNom(IOUtils.readString("Nom", true));
				System.out.println(clientx.getPrenom());
				clientx.setPrenom(IOUtils.readString("Prenom", true));
				System.out.println(clientx.getEmail());
				clientx.setEmail(IOUtils.readString("Email", true));
				System.out.println(clientx.getNaissance());
				clientx.setNaissance(IOUtils.readLDate("Date de Naissance", true));
				clientService.edit(clientx);
				break;
			}
		}
	}
}
