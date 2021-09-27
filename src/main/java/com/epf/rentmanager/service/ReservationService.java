package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.dao.ReservationDao;

@Service
public class ReservationService {
	private ReservationDao reservationDao;
	public static ReservationService instance;

	@Autowired
	private ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	/**
	 * Créé une reservation
	 * 
	 * @param reservation de la classe Reservation
	 * @return l'id (long) de la reservation créée
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public long create(Reservation reservation) throws ServiceException {
		if (isOK(reservation)) {
			if (is30jours(reservation)) {
				try {
					reservationDao.create(reservation);
				} catch (DaoException e) {
					throw new ServiceException("Une erreur a eu lieu lors de la création de la réservation", e);
				}
				return reservation.getId();
			}
		}
		throw new ServiceException("Veuillez corriger les formats");
	}

	/**
	 * Supprime une reservation
	 * 
	 * @param id un long identifiant du reservation
	 * @return l'id de la reservation supprimée
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public long delete(long id) throws ServiceException {
		try {
			reservationDao.delete(id);
			return id;
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppession de la reservation", e);
		}
	}

	/**
	 * Trouve une réservation par son identifiant
	 * 
	 * @param id un long identifiant de la réservation
	 * @return la réservation trouvée
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public Reservation findById(long id) throws ServiceException {
		try {
			Reservation reservation = reservationDao.findById(id);
			if (reservation == null) {
				throw new ServiceException("La réservation n'existe pas");
			}
			return reservation;
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de la réservation", e);
		}
	}

	/**
	 * Trouve les réservations d'un client via son id
	 * 
	 * @param id un long identifiant du client dans la classe réservation
	 * @return la liste des réservations du client
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public List<Reservation> findResaByClientID(long id) throws ServiceException {
		try {
			return reservationDao.findResaByClientId(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération des réservations de l'utilisateur", e);
		}
	}

	/**
	 * Trouve les réservations d'un véhicule via son id
	 * 
	 * @param id un long identifiant du véhicule dans la classe réservation
	 * @return la liste des réservations du véhicule
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public List<Reservation> findResaByVehicleID(long id) throws ServiceException {
		try {
			return reservationDao.findResaByVehicleId(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération des réservations du véhicule", e);
		}
	}

	/**
	 * Donne la liste des reservations
	 * 
	 * @return la liste des reservations (ArrayList)
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public List<Reservation> findAll() throws ServiceException {
		try {
			return reservationDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de la liste des réservations", e);
		}
	}

	/**
	 * Permet de modifier une reservation
	 * 
	 * @param reservation un élément de la classe reservation
	 * @return le nombre de lignes modifiées
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public int edit(Reservation reservation) throws ServiceException {
		if (isOK(reservation)) {
			if (is30jours(reservation)) {
				try {
					int nb_lignes_modifiees = reservationDao.edit(reservation);
					return nb_lignes_modifiees;
				} catch (DaoException e) {
					throw new ServiceException("Une erreur a eu lieu lors de la modification de la reservation", e);
				}
			}
		}
		throw new ServiceException("Veuillez corriger les formats");
	}

	/**
	 * Compte le nombre de clients de chaque voiture
	 * 
	 * @param id L'identifiant de la voiture
	 * @return Le nombre trouvé
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public int NbClientByVehicle(int idc) throws ServiceException {
		try {
			return reservationDao.NbClientByVehicle(idc);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du compte du nombre de clients de la voiture", e);
		}
	}

	/**
	 * Compte le nombre de voitures de chaque client
	 * 
	 * @param id L'identifiant du client
	 * @return Le nombre trouvé
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public int NbVehicleByClient(int idv) throws ServiceException {
		try {
			return reservationDao.NbVehicleByClient(idv);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du compte du nombre de voitures du client", e);
		}
	}

	/**
	 * La liste des identifiants des voitures utilisées par un client
	 * 
	 * @param id L'identifiant du client
	 * @return La liste d'identifiant (ArrayList)
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public List<Integer> vehicleIdByClient(int id) throws ServiceException {
		try {
			return reservationDao.vehicleIdByClient(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de la liste des voitures du client", e);
		}
	}

	/**
	 * La liste des identifiants des clients ayant réservés la voiture
	 * 
	 * @param id L'identifiant de la voiture
	 * @return La liste d'identifiant (ArrayList)
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public List<Integer> clientIdByVehicle(int id) throws ServiceException {
		try {
			return reservationDao.clientIdByVehicle(id);
		} catch (DaoException e) {
			throw new ServiceException(
					"Une erreur a eu lieu lors de la récupération de la liste des clients du vehicule", e);
		}
	}

	/**
	 * Compte le nombre de reservations
	 * 
	 * @return le nombre de reservations (int)
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public int nb_reservation() throws ServiceException {
		try {
			return reservationDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors du compte du nombre de réservations", e);
		}
	}

	
	/**
	 * Vérifie la conformité de la réservation
	 * 
	 * @param reservation une réservation
	 * @return un boolean, true si la réservation valide tous les tests
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public static boolean isOK(Reservation reservation) throws ServiceException {

		if (reservation.getDebut() == null || reservation.getFin() == null ) {
			throw new ServiceException("Veuillez renseigner une date de début et une date de fin");
		}
		Period p = Period.between(reservation.getDebut(), reservation.getFin());
		if (p.getDays() >= 7) {
			throw new ServiceException("Vous ne pouvez pas réserver la même voiture plus de 7 jours d'affilés");
		}
		if (reservation.getDebut().isAfter(reservation.getFin())) {
			throw new ServiceException("La date de fin de peut pas être inférieure à celle de début");
		}
		else
			return true;
	}

	/**
	 * Retourne la liste des réservations associées à un véhicule triées par date
	 * 
	 * @param resa Une réservation dont on veut récupperer l'identifiant du véhicule dont on souhaite afficher les autres réservations associées
	 * @return Une liste qui contient toutes les réservations du véhicule
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public List<Reservation> tri_par_date(Reservation reservation) throws ServiceException {
		try {
			return reservationDao.tri_par_date(reservation);
		} catch (DaoException e) {
			throw new ServiceException(
					"Une erreur a eu lieu lors de la récupération des réservations associées au véhicule"
							+ e.getMessage());
		}
	}
	
	/**
	 * Vérifie la conformité des dates de la réservation : la disponibilité de la voiture pendant les dates sélectionnées 
	 * et la condition pour une réservation de ne pas être réservée plus de 30 jours de suite
	 * 
	 * @param reservation une réservation
	 * @return un boolean, true si la réservation valide tous les tests
	 * @throws ServiceException en cas d'erreur dans la requête
	 */
	public boolean is30jours(Reservation reservationATester) {

		try {
			List<Reservation> reservationMemeV = reservationDao.findResaByVehicleId(reservationATester.getVoiture_id());

			for (Reservation reservationt : reservationMemeV) {
				LocalDate d1 = reservationt.getDebut();
				LocalDate f1 = reservationt.getFin();
				LocalDate d2 = reservationATester.getDebut();
				LocalDate f2 = reservationATester.getFin();
				if (d1.isBefore(d2) && f1.isAfter(d2)) {
					throw new ServiceException("Ces dates ne sont pas disponible");
				}
				if (d1.isBefore(f2) && f1.isAfter(f2)) {
					throw new ServiceException("Ces dates ne sont pas disponible");
				}
				if (d2.isBefore(d1) && f2.isAfter(d1)) {
					throw new ServiceException("Ces dates ne sont pas disponible");
				}
				if (d2.isBefore(f1) && f2.isAfter(f1)) {
					throw new ServiceException("Ces dates ne sont pas disponible");
				}
				if (d1.equals(d2) || f1.equals(d2)) {
					throw new ServiceException("Ces dates ne sont pas disponible");
				}
				if (d1.equals(f2) || f1.equals(f2)) {
					throw new ServiceException("Ces dates ne sont pas disponible");
				}

				List<Reservation> reservations_tries = tri_par_date(reservationATester);
				int nbre_jours = 0;
				int tour = 0;
				LocalDate fin_resa = reservationATester.getFin();
				for (Reservation reservation3 : reservations_tries) {
					if (tour == 0 || Period.between(fin_resa, reservation3.getDebut()).getDays() > 1) {
						nbre_jours = Period.between(reservation3.getDebut(), reservation3.getFin()).getDays() + 1;
					} else {
						nbre_jours += Period.between(reservation3.getDebut(), reservation3.getFin()).getDays() + 1;
					}
					tour = 1;
					fin_resa = reservation3.getFin();
				}
				if (nbre_jours >= 30) {
					throw new ServiceException("Une voiture ne peut pas être reservée 30 jours de suite");
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return true;
	}
}
