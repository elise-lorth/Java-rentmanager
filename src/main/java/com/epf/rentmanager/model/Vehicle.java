package com.epf.rentmanager.model;

public class Vehicle {
	// attributs
	private String constructeur, modele;
	private short nb_places;
	private long identifiant;

	// Constructor
	public Vehicle() {
	}

	@Override // toString
	public String toString() {
		return "Véhicule " + identifiant + ", constructeur : " + constructeur + ", modèle : " + modele
				+ ", nombre de places : " + nb_places;
	}

	// Constructor
	public Vehicle(String constructeur, String modele, short nb_places, long identifiant) {
		super();
		this.constructeur = constructeur;
		this.modele = modele;
		this.nb_places = nb_places;
		this.identifiant = identifiant;
	}

	// Getter & Setter
	public String getConstructeur() {
		return constructeur;
	}

	public void setConstructeur(String constructeur) {
		this.constructeur = constructeur;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public short getNb_places() {
		return nb_places;
	}

	public void setNb_places(short nb_places) {
		this.nb_places = nb_places;
	}

	public long getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(long identifiant) {
		this.identifiant = identifiant;
	}

}
