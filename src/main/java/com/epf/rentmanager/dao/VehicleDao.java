package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
public class VehicleDao {

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES(?,?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLE_QUERY = "SELECT COUNT(*) as count FROM Vehicle";
	private static final String EDIT_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id = ?;";

	/**
	 * Créé un vehicule
	 * 
	 * @param vehicule de la classe Vehicle
	 * @return l'id (long) du vehicule créé
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public long create(Vehicle vehicle) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setShort(3, vehicle.getNb_places());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			ps.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du véhicule", e);
		}
		return id;
	}

	/**
	 * Supprime un vehicule
	 * 
	 * @param id un long identifiant du vehicule
	 * @return l'id du vehicule supprimé
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public long delete(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, id);
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			ps.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule", e);
		}
		return id;
	}

	/**
	 * Trouve un vehicule via son id
	 * 
	 * @param id un long identifiant du vehicule
	 * @return le vehicule trouvé
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public Vehicle findById(long id) throws DaoException {
		Vehicle vehicule = new Vehicle();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				vehicule.setConstructeur(resultSet.getString("constructeur"));
				vehicule.setModele(resultSet.getString("modele"));
				vehicule.setNb_places(resultSet.getShort("nb_places"));
				vehicule.setIdentifiant(id);
			}
			ps.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return vehicule;
	}

	/**
	 * Donne la liste des vehicules
	 * 
	 * @return la liste des vehicules (ArrayList)
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> liste_Vehicules = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);) {
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setIdentifiant(resultSet.getLong("id"));
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setModele(resultSet.getString("modele"));
				vehicle.setNb_places(resultSet.getShort("nb_places"));
				liste_Vehicules.add(vehicle);
			}
			ps.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la génération de la liste des véhicules", e);
		}
		return liste_Vehicules;
	}

	/**
	 * Modifie un vehicule via son id
	 * 
	 * @param vehicle de la classe vehicule
	 * @return le nombre de lignes modifiées
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public int edit(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(EDIT_VEHICLE_QUERY);
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setShort(3, vehicle.getNb_places());
			ps.setLong(4, vehicle.getIdentifiant());
			int nb_lignes_modifiees = ps.executeUpdate();
			ps.close();
			connection.close();

			return nb_lignes_modifiees;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du compte du nombre de véhicules", e);
		}
	}

	/**
	 * Compte le nombre de vehicules
	 * 
	 * @return le nombre de vehicule (int)
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public int count() throws DaoException {
		int nb_voiture = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLE_QUERY);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nb_voiture = rs.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return nb_voiture;
	}

	public static String getCreateVehicleQuery() {
		return CREATE_VEHICLE_QUERY;
	}

	public static String getDeleteVehicleQuery() {
		return DELETE_VEHICLE_QUERY;
	}

	public static String getFindVehicleQuery() {
		return FIND_VEHICLE_QUERY;
	}

	public static String getFindVehiclesQuery() {
		return FIND_VEHICLES_QUERY;
	}

	public static String getCountVehiclesQuery() {
		return COUNT_VEHICLE_QUERY;
	}
}
