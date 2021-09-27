package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
public class ReservationDao {

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=? ORDER BY vehicle_id;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=? ORDER BY client_id;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String EDIT_RESERVATIONS_QUERY = "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id = ?;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) as count FROM Reservation;";
	private static final String NB_VEHICLE_BY_CLIENT = "SELECT DISTINCT vehicle_id FROM Reservation WHERE client_id=?;";
	private static final String NB_CLIENT_BY_VEHICLE = "SELECT DISTINCT client_id FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_TRIES_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=? ORDER BY fin;";

	/**
	 * Créé une reservation
	 * 
	 * @param reservation de la classe Reservation
	 * @return l'id (long) de la reservation créée
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public long create(Reservation reservation) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, reservation.getClient_id());
			ps.setLong(2, reservation.getVoiture_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création de la réservation", e);
		}
		return id;
	}

	/**
	 * Supprime une reservation
	 * 
	 * @param id un long identifiant du reservation
	 * @return l'id de la reservation supprimée
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public long delete(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, id);
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression de la réservation", e);
		}
		return id;
	}

	/**
	 * Trouve une réservation via son id
	 * 
	 * @param id un long identifiant de la réservation
	 * @return la réservation trouvée
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public Reservation findById(long idr) throws DaoException {
		Reservation reservation = new Reservation();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATION_QUERY);
			ps.setLong(1, idr);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				reservation.setClient_id(resultSet.getLong("client_id"));
				reservation.setVoiture_id(resultSet.getLong("vehicle_id"));
				reservation.setDebut(resultSet.getDate("debut").toLocalDate());
				reservation.setFin(resultSet.getDate("fin").toLocalDate());
				reservation.setId(idr);
			}
			ps.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de la réservation", e);
		}
		return reservation;
	}

	/**
	 * Trouve les réservations d'un client via son id
	 * 
	 * @param clientId un long identifiant du client dans la classe réservation
	 * @return la liste des réservations du client
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> liste_Reservations = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			ps.setLong(1, clientId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getLong("id"));
				reservation.setVoiture_id(resultSet.getLong("vehicle_id"));
				reservation.setDebut(resultSet.getDate("debut").toLocalDate());
				reservation.setFin(resultSet.getDate("fin").toLocalDate());
				liste_Reservations.add(reservation);
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la génération de la liste des réservations du client", e);
		}
		return liste_Reservations;
	}

	/**
	 * Trouve les réservations d'un véhicule via son id
	 * 
	 * @param vehicleId un long identifiant du véhicule dans la classe réservation
	 * @return la liste des réservations du véhicule
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> liste_Reservations = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			ps.setLong(1, vehicleId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getLong("id"));
				reservation.setClient_id(resultSet.getLong("client_id"));
				reservation.setDebut(resultSet.getDate("debut").toLocalDate());
				reservation.setFin(resultSet.getDate("fin").toLocalDate());
				liste_Reservations.add(reservation);
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la génération de la liste des réservations du véhicule", e);
		}
		return liste_Reservations;
	}

	/**
	 * Donne la liste des reservations
	 * 
	 * @return la liste des reservations (ArrayList)
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> liste_Reservations = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setClient_id(resultSet.getLong("client_id"));
				reservation.setVoiture_id(resultSet.getLong("vehicle_id"));
				reservation.setId(resultSet.getLong("id"));
				reservation.setDebut(resultSet.getDate("debut").toLocalDate());
				reservation.setFin(resultSet.getDate("fin").toLocalDate());
				liste_Reservations.add(reservation);
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la génération de la liste des réservations", e);
		}
		return liste_Reservations;
	}

	/**
	 * Permet de modifier une reservation
	 * 
	 * @param reservation un élément de la classe reservation
	 * @return le nombre de lignes modifiées
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public int edit(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(EDIT_RESERVATIONS_QUERY);
			ps.setLong(1, reservation.getClient_id());
			ps.setLong(2, reservation.getVoiture_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.setLong(5, reservation.getId());
			int nb_lignes_modifiees = ps.executeUpdate();
			ps.close();
			connection.close();
			return nb_lignes_modifiees;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la modification de la réservation", e);
		}
	}

	/**
	 * Compte le nombre de reservations
	 * 
	 * @return le nombre de reservations (int)
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public int count() throws DaoException {
		int nb_reservation = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_RESERVATIONS_QUERY);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nb_reservation = rs.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du compte du nombre de réservations", e);
		}
		return nb_reservation;
	}

	/**
	 * Compte le nombre de clients de chaque voiture
	 * 
	 * @param id L'identifiant de la voiture
	 * @return Le nombre trouvé
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public int NbClientByVehicle(int id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(NB_CLIENT_BY_VEHICLE);) {
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			int count = 0;
			while (resultSet.next()) {
				count++;
			}
			return count;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	/**
	 * Compte le nombre de voitures de chaque client
	 * 
	 * @param id L'identifiant du client
	 * @return Le nombre trouvé
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public int NbVehicleByClient(int id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(NB_VEHICLE_BY_CLIENT);) {
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			int count = 0;
			while (resultSet.next()) {
				count++;
			}
			return count;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	/**
	 * La liste des identifiants des voitures utilisées par un client
	 * 
	 * @param id L'identifiant du client
	 * @return La liste d'identifiant (ArrayList)
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public List<Integer> vehicleIdByClient(int id) throws DaoException {
		List<Integer> vehicleListe = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(NB_VEHICLE_BY_CLIENT);) {
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				vehicleListe.add(resultSet.getInt("vehicle_id"));
			}
			return vehicleListe;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	/**
	 * La liste des identifiants des clients ayant réservés la voiture
	 * 
	 * @param id L'identifiant de la voiture
	 * @return La liste d'identifiant (ArrayList)
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public List<Integer> clientIdByVehicle(int id) throws DaoException {
		List<Integer> clientListe = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(NB_CLIENT_BY_VEHICLE);) {
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				clientListe.add(resultSet.getInt("client_id"));
			}
			return clientListe;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	/**
	 * Retourne la liste des réservations associées à un véhicule triée de la plus
	 * ancienne à la plus récente
	 * 
	 * @param resa Une réservation dont on veut récupperer l'identifiant du véhicule
	 *             dont on souhaite afficher les autres réservations associées
	 * @return Une liste qui contient toutes les réservations du véhicule
	 * @throws DaoException en cas d'erreur lors de la connection à la base de
	 *                      données ou dans la requête
	 */

	public List<Reservation> tri_par_date(Reservation resa) throws DaoException {
		List<Reservation> reservations_tries = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_TRIES_QUERY);) {
			preparedStatement.setLong(1, resa.getVoiture_id());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getInt("id") != resa.getId()) {
					Reservation reservation = new Reservation();
					reservation.setId(resultSet.getInt("id"));
					reservation.setClient_id(resultSet.getInt("client_id"));
					reservation.setVoiture_id(resa.getVoiture_id());
					reservation.setDebut(resultSet.getDate("debut").toLocalDate());
					reservation.setFin(resultSet.getDate("fin").toLocalDate());
					reservations_tries.add(reservation);
				}
			}
			boolean resa_ajoutee = false;
			int place = 0;
			for (Reservation reservation : reservations_tries) {
				if (resa.getDebut().isBefore(reservation.getDebut())) {
					reservations_tries.add(place, resa);
					resa_ajoutee = true;
					break;
				}
				place++;
			}
			if (resa_ajoutee == false) {
				reservations_tries.add(resa);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservations_tries;
	}
}
