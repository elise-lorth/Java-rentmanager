package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
	// attributs
	private long id;
	private long client_id;
	private long voiture_id;
	private LocalDate debut;
	private LocalDate fin;

	// constructeur
	public Reservation() {
	}

	// getter&setter
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Reservation(long id, long client_id, long voiture_id, LocalDate debut, LocalDate fin) {
		super();
		this.id = id;
		this.client_id = client_id;
		this.voiture_id = voiture_id;
		this.debut = debut;
		this.fin = fin;
	}

	public long getClient_id() {
		return client_id;
	}

	public void setClient_id(long client_id) {
		this.client_id = client_id;
	}

	public long getVoiture_id() {
		return voiture_id;
	}

	public void setVoiture_id(long voiture_id) {
		this.voiture_id = voiture_id;
	}

	public LocalDate getDebut() {
		return debut;
	}

	public void setDebut(LocalDate debut) {
		this.debut = debut;
	}

	public LocalDate getFin() {
		return fin;
	}

	public void setFin(LocalDate fin) {
		this.fin = fin;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", client_id=" + client_id + ", voiture_id=" + voiture_id + ", debut=" + debut
				+ ", fin=" + fin + "]";
	}

}