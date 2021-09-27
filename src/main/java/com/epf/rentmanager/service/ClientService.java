package com.epf.rentmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.dao.ClientDao;

@Service
public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;

	@Autowired
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	/**
	 * Créé un client
	 * 
	 * @param client de la classe Client
	 * @return l'id (long) du client créé
	 * @see ClientDao.create
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public long create(Client client) throws ServiceException {

		if (isOK(client)) {
			try {
				client.setNom(client.getNom().toUpperCase());
				clientDao.create(client);
			} catch (DaoException e) {
				throw new ServiceException("Une erreur a eu lieu lors de la création de l'utilisateur", e);
			}
			return client.getIdentifiant();
		}
		throw new ServiceException("Veuillez corriger les formats");
	}

	/**
	 * Trouve un client par son identifiant
	 * 
	 * @param id un long identifiant du client
	 * @return le client trouvé
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public Client findById(long id) throws ServiceException {
		try {
			Client client = clientDao.findById(id);
			if (client == null) {
				throw new ServiceException("L'utilisateur n'existe pas");
			}
			return client;
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de l'utilisateur", e);
		}
	}

	/**
	 * Donne la liste des clients
	 * 
	 * @return la liste des clients (ArrayList)
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de la liste des utilisateurs", e);
		}
	}

	/**
	 * Supprime un client
	 * 
	 * @param id un long identifiant du client
	 * @return l'id du client supprimée
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public long delete(long id) throws ServiceException {
		try {
			clientDao.delete(id);
			return id;
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppression de l'utilisateur", e);
		}
	}

	/**
	 * Modifie un client via son id
	 * 
	 * @param client de la classe Client
	 * @return le nombre de lignes modifiées (int)
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public int edit(Client client) throws ServiceException {
		if (isOK(client)) {
			try {
				client.setNom(client.getNom().toUpperCase());
				int nb_lignes_modifiees = clientDao.edit(client);
				return nb_lignes_modifiees;
			} catch (DaoException e) {
				throw new ServiceException("Une erreur a eu lieu lors de la modification de l'utilisateur", e);
			}
		}
		throw new ServiceException("Veuillez corriger les formats");
	}

	/**
	 * Compte le nombre de clients
	 * 
	 * @return le nombre de client (int)
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public int nb_client() throws ServiceException {
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du compte du nombre d'utilisateurs", e);
		}
	}

	/**
	 * Vérifie le format de l'email inscrit
	 * 
	 * @param email l'email vérifié
	 * @return boolean, false si l'email est null ou non rempli
	 */
	public static boolean isMail(String email) {
		if (email == null) {
			return false;
		}
		return email.matches(".+@.+\\..+");
	}
	
	/**
	 * Vérifie les informations de l'utilisateur
	 * 
	 * @param client Le client testé
	 * @return boolean, false si le client a un mauvais format
	 */
	public static boolean isOK(Client client) throws ServiceException {
		LocalDate today = LocalDate.now();
		Period p = Period.between(client.getNaissance(), today);
		if (client.getNom() == "" || client.getPrenom() == "") {
			throw new ServiceException("L'utilisateur doit avoir un nom et un prénom");
		}

		if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
			throw new ServiceException("L'utilisateur doit avoir un nom et un prénom de plus de 3 caractères");
		}

		if (!isMail(client.getEmail())) {
			throw new ServiceException("Veuillez corriger le format du mail");
		}

		if (p.getYears() < 18) {
			throw new ServiceException("Un mineur ne peut pas conduire");
		} else
			return true;
	}
}
