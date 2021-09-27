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
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
public class ClientDao {

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENT_QUERY = "SELECT COUNT(*) as count FROM Client";
	private static final String EDIT_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id = ?;";

	/**
	 * Créé un client
	 * 
	 * @param client de la classe Client
	 * @return l'id (long) du client créée
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public long create(Client client) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
			}
			ps.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client", e);
		}
		return id;
	}

	/**
	 * Supprime un client
	 * 
	 * @param id un long identifiant du client
	 * @return l'id du client supprimée
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public long delete(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, id);
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client", e);
		}
		return id;
	}

	/**
	 * Trouve un client via son id
	 * 
	 * @param id un long identifiant du client
	 * @return le client trouvé
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public Client findById(long id) throws DaoException {
		Client client = new Client();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY);
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance").toLocalDate());
				client.setIdentifiant(id);
			}
			ps.close();
			resultSet.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client", e);
		}
		return client;
	}

	/**
	 * Donne la liste des clients
	 * 
	 * @return la liste des clients (ArrayList)
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public List<Client> findAll() throws DaoException {
		List<Client> liste_Clients = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);) {
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Client client = new Client();
				client.setIdentifiant(resultSet.getLong("id"));
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance").toLocalDate());
				liste_Clients.add(client);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la génération de la liste des clients", e);
		}
		return liste_Clients;
	}

	/**
	 * Compte le nombre de clients
	 * 
	 * @return le nombre de client (int)
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public int count() throws DaoException {
		int nb_client = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_CLIENT_QUERY);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nb_client = rs.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du compte du nombre de clients", e);
		}
		return nb_client;
	}

	/**
	 * Modifie un client via son id
	 * 
	 * @param client de la classe Client
	 * @return le nombre de lignes modifiées (int)
	 * @throws DaoException en cas d'erreur lors de la connexion à la base de donnée
	 *                      ou dans la requête
	 */
	public int edit(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(EDIT_CLIENT_QUERY);
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.setLong(5, client.getIdentifiant());
			int nb_lignes_modifiees = ps.executeUpdate();
			ps.close();
			connection.close();
			return nb_lignes_modifiees;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la modification du client", e);
		}
	}

	public static String getCreateClientQuery() {
		return CREATE_CLIENT_QUERY;
	}

	public static String getDeleteClientQuery() {
		return DELETE_CLIENT_QUERY;
	}

	public static String getFindClientQuery() {
		return FIND_CLIENT_QUERY;
	}

	public static String getFindClientsQuery() {
		return FIND_CLIENTS_QUERY;
	}

}
