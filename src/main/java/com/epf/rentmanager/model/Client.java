package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Client {
	// attributs
	private String nom, prenom, email;
	private LocalDate naissance;
	private long identifiant;

	// Constructor
	public Client() {
	}

	public Client(String nom, String prenom, String email, LocalDate naissance, long identifiant) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.naissance = naissance;
		this.identifiant = identifiant;
	}

	// Getter & Setter
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getNaissance() {
		return naissance;
	}

	public void setNaissance(LocalDate naissance) {
		this.naissance = naissance;
	}

	public long getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(long identifiant) {
		this.identifiant = identifiant;
	}

	@Override // toString
	public String toString() {
		return "Client " + identifiant + " : " + nom + " " + prenom + ", email : " + email + ", naissance : "
				+ naissance;
	}

}
