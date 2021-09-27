package com.epf.rentmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;

	@Autowired
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	/**
	 * Créé un vehicule
	 * 
	 * @param vehicule de la classe Vehicle
	 * @return l'id (long) du vehicule créé
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public long create(Vehicle vehicle) throws ServiceException {
		if (isOK(vehicle)) {
			try {
				vehicleDao.create(vehicle);
			} catch (DaoException e) {
				throw new ServiceException("Une erreur a eu lieu lors de la création du véhicule : ", e);
			}
			return vehicle.getIdentifiant();
		}
		throw new ServiceException("Veuillez corriger les formats");
	}

	/**
	 * Modifie un vehicule via son id
	 * 
	 * @param vehicle de la classe vehicule
	 * @return le nombre de lignes modifiées
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public int edit(Vehicle vehicle) throws ServiceException {
		if (isOK(vehicle)) {

			try {
				int nb_lignes_modifiees = vehicleDao.edit(vehicle);

				return nb_lignes_modifiees;
			} catch (DaoException e) {
				throw new ServiceException("Une erreur a eu lieu lors de la modification du véhicule : ", e);
			}
		}
		throw new ServiceException("Veuillez corriger les formats");
	}

	/**
	 * Trouve un vehicule via son id
	 * 
	 * @param id un long identifiant du vehicule
	 * @return le vehicule trouvé
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public Vehicle findById(long id) throws ServiceException {
		try {
			Vehicle vehicle = vehicleDao.findById(id);
			if (vehicle == null) {
				throw new ServiceException("Le véhicule n'existe pas");
			}
			return vehicle;
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du véhicule : ", e);
		}
	}

	/**
	 * Donne la liste des vehicules
	 * 
	 * @return la liste des vehicules (ArrayList)
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de la liste des véhicules", e);
		}
	}

	/**
	 * Supprime un vehicule
	 * 
	 * @param id un long identifiant du vehicule
	 * @return l'id du vehicule supprimé
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public long delete(long id) throws ServiceException {
		try {
			vehicleDao.delete(id);
			return id;
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppression du véhicule", e);
		}
	}

	/**
	 * Compte le nombre de vehicules
	 * 
	 * @return le nombre de vehicule (int)
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public int nb_voiture() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du compte du nombre de véhicules", e);
		}
	}

	/**
	 * Vérifie les informations de la voiture
	 * 
	 * @param vehicle Le véhicule testé
	 * @return boolean, false si le format est mauvais
	 */
	public static boolean isOK(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructeur() == "" || vehicle.getModele() == "") {
			throw new ServiceException("La voiture doit avoir un constructeur");
		} else if (vehicle.getNb_places() < 2 || vehicle.getNb_places() > 9) {
			throw new ServiceException("La voiture doit avoir au moins 2 places et maximum 9 places");
		} else
			return true;
	}
}
